/*
 * Copyright [2013] Adopt OpenJDK Programme
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.adoptopenjdk.javacountdown.control;

import org.adoptopenjdk.javacountdown.entity.Visit;
import com.google.gson.Gson;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;

/**
 * The main Data provider for the JAX-RS services
 */
@Stateless
public class DataProvider {

    // Only EclipseLink > 2.5
    //private final static String GET_COUNTRIES = "SELECT new org.adoptopenjdk.javacountdown.control.CountryHolder(v.country,((COUNT(v) / ( SELECT COUNT(v) FROM Visit v)) * 100 )) AS percentage FROM Visit v WHERE v.country <> 'unresolved' AND v.vMinor = :version GROUP BY v.country";
    private final static String GET_COUNTRIES = "SELECT new org.adoptopenjdk.javacountdown.control.CountryHolder(v.country, COUNT(v.country)) cnt FROM Visit v WHERE v.country <> 'unresolved' AND v.vMinor = :version GROUP BY v.country ORDER BY COUNT(v.country)";
    private final static String GET_COUNTRY_FROM_GEO_DATA = "SELECT new org.adoptopenjdk.javacountdown.control.CountryHolder(G.alpha2) FROM Geonames AS G ORDER BY ABS((ABS(G.latitude-:lat))+(ABS(G.longitude-:lng))) ASC";
    private static final Logger logger = Logger.getLogger(DataProvider.class.getName());

    @PersistenceContext
    EntityManager entityManager;

    /**
     * Get a list of all countries with data to display on the map, this is
     * returned directly as a String as that's the expected format
     *
     * @return List of countries as a String
     */
    public String getCountries() {

        // Criteria Query approach
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        
        Expression<Long> ex = cb.diff(cb.count(cq.from(Visit.class)),cb.prod(cb.count(cq.from(Visit.class)), cb.parameter(Long.class)));

//CriteriaQuery query = criteriaBuilder.createQuery();
        TypedQuery<CountryHolder> query = entityManager.createQuery(GET_COUNTRIES, CountryHolder.class);
        query.setParameter("version", Integer.valueOf(7));
        List<CountryHolder> results = query.getResultList();

        HashMap<String, Integer> all = new HashMap<>();

        logger.log(Level.FINE, "Total: {0}", results.size());

        for (CountryHolder holder : results) {
            logger.log(Level.FINE, "country + percentage: {0} {1}", new Object[]{holder.getCountry(), holder.getCount()});
            all.put(holder.getCountry(), holder.getCount());
        }

        // If we don't have results we simply put an empty element to prevent 204 on the client
        if (all.isEmpty()) {
            all.put("", Integer.valueOf(0));
        }

        Gson gson = new Gson();
        String json = gson.toJson(all);

        if (logger.isLoggable(Level.FINE)) {

            logger.log(Level.FINE, "<< BUILDER {0}", json);
        }

        return json;
    }

    /**
     * This is where parts of the magic already happens. This does a select on
     * the geonames table and does some searching for the nearest country entry
     * with the latitude/longitude. It should return a ISO 3166 alpha-2 code.
     * Refer to blog.stavi.sh/country-list-iso-3166-codes-latitude-longitude for
     * the data behind it.
     *
     * @param String latitude
     * @param String longitude
     * @return ISO 3166 alpha 2 code
     */
    private String getCountryFromLatLong(double latitude, double longitude) {
        String country = "unresolved";

        TypedQuery<CountryHolder> query = entityManager.createQuery(GET_COUNTRY_FROM_GEO_DATA, CountryHolder.class);
        query.setParameter("lat", new Double(latitude));
        query.setParameter("lng", new Double(longitude));
        query.setMaxResults(3);
        List<CountryHolder> results = query.getResultList();

        logger.log(Level.FINE, "Are we lucky? lat {0} long {1}", new Object[]{latitude, longitude});

        if (results.size() > 0) {
            logger.log(Level.FINE, "we have a result");
            CountryHolder result = results.get(0);
            country = result.getCountry();
        }
        logger.log(Level.FINE, "Country: {0}", country);
        return country;
    }

    /**
     * Persisting a Visit entity This only gets called when the visit could be
     * parsed by GSON. No further checks necessary here.
     *
     * @param visit
     */
    @Asynchronous
    public void persistVisit(Visit visit) {
        String country = getCountryFromLatLong(visit.getLatitude(), visit.getLongitude());
        setVersion(visit);
        visit.setCountry(country);
        visit.setTime(new Date(System.currentTimeMillis()));
        entityManager.persist(visit);

        logger.log(Level.FINE, "persisted {0}", visit);
    }

    /**
     * Parsing the version string to it's numbers. If this fails we still have
     * the String version field in the database ...
     *
     * @param visit
     * @return The Visit, probably instrumented with version data
     */
    private static Visit setVersion(Visit visit) {
        try {
            String delims = "[.]+";
            String[] tokens = visit.getVersion().split(delims);
            visit.setvMajor(Integer.parseInt(tokens[0]));
            visit.setvMinor(Integer.parseInt(tokens[1]));
            visit.setvPatch(Integer.parseInt(tokens[2]));
            visit.setvBuild(Integer.parseInt(tokens[3]));
        } catch (NumberFormatException | NullPointerException e) {
            logger.fine("Failed to parse version, but that's OK, "
                    + "we still have the string variant stored in the data store.");
        }
        return visit;
    }
}
