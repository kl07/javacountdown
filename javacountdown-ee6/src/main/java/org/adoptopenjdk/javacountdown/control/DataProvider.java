/**
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
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * The main Data provider for the JAX-RS services
 */
public class DataProvider {

    private final static String GET_COUNTRIES = "SELECT new org.adoptopenjdk.javacountdown.control.CountryHolder(v.country, COUNT(v.country)) cnt FROM Visit v WHERE v.country <> 'unresolved' AND v.vMinor = :version GROUP BY v.country ORDER BY COUNT(v.country)";
    private final static String GET_COUNTRY_FROM_GEO_DATA = "SELECT new org.adoptopenjdk.javacountdown.control.CountryHolder(G.alpha2) FROM Geonames AS G ORDER BY ABS((ABS(G.latitude-:lat))+(ABS(G.longitude-:lng))) ASC";
    private static final Logger logger = Logger.getLogger(DataProvider.class.getName());
    @PersistenceContext
    EntityManager em;

    /**
     * Get a list of all countries with data to display on the map, this is
     * returned directly as a String as that's the expected format
     *
     * @return List of countries as a String
     */
    public String getCountries() {
        StringBuilder builder = new StringBuilder();

        TypedQuery<CountryHolder> query = em.createQuery(GET_COUNTRIES, CountryHolder.class);
        query.setParameter("version", 7);
        List<CountryHolder> results = query.getResultList();

        HashMap<String, Integer> all = new HashMap<>();

        for (CountryHolder holder : results) {
            String country = holder.getCountry();
            int count = holder.getCount().intValue();
            all.put(country, Integer.valueOf(count));
        }


        int total = 0; // get 100% base
        for (Integer value : all.values()) {
            total += value.intValue();
        }

        logger.log(Level.INFO, "Total: {0}", Integer.valueOf(total));

        Set<String> keyset = all.keySet();
        Gson gson = new Gson();

        // Down to percentages
        for (String key : keyset) {
            int temp = all.get(key).intValue() * 100;
            float percentage = temp / (float) total;
            int prettypercentage = (int) percentage;
            all.put(key, Integer.valueOf(prettypercentage));
            logger.log(Level.INFO, "Key + Temp: {0} {1}", new Object[]{key, Integer.valueOf(prettypercentage)});
        }

        // if we don't have results we simply put an empty element to prevent 204 on the client
        if (all.isEmpty()) {
            all.put("", Integer.valueOf(0));
        }

        // Make JSON
        String json = gson.toJson(all);
        builder.append(json);

        logger.log(Level.INFO, "<< BUILDER {0}", builder.toString());

        return builder.toString();
    }

    /**
     * This is where parts of the magic already happens. This does a select on
     * the geonames table and does some searching for the nearest country entry
     * with the lat/lng. It should return a ISO 3166 alpha-2 code. Refer to
     * blog.stavi.sh/country-list-iso-3166-codes-latitude-longitude for the data
     * behind it.
     *
     * @param String latitude
     * @param String longitude
     * @return ISO 3166 alpha 2 code
     */
    private String getCountryFromLatLong(double latitude, double longitude) {
        String country = "unresolved";

        TypedQuery<CountryHolder> query = em.createQuery(GET_COUNTRY_FROM_GEO_DATA, CountryHolder.class);
        query.setParameter("lat", new Double(latitude));
        query.setParameter("lng", new Double(longitude));
        query.setMaxResults(3);
        List<CountryHolder> results = query.getResultList();

        logger.log(Level.INFO, "Are we lucky? lat {0} long {1}", new Object[]{new Double(latitude), new Double(longitude)});

        if (results.size() > 0) {
            logger.log(Level.INFO, "we have a result");
            CountryHolder result = results.get(0);
            country = result.getCountry();
        }

        logger.log(Level.INFO, "Country: {0}", country);

        return country;
    }

    /**
     * Persisting a Visit entity This only gets called when the visit could be
     * parsed by gson. No further checks necessary here.
     *
     * @param visit
     */
    public void persistVisit(Visit visit) {
        logger.log(Level.INFO, "persist visit called: {0}", visit);

        String country = getCountryFromLatLong(visit.getLat(), visit.getLng());
        visit = setVersion(visit);
        visit.setCountry(country);
        visit.setTime(new Date(System.currentTimeMillis()));
        em.persist(visit);

        logger.log(Level.INFO, "persisted {0}", visit);
    }

    /**
     * Parsing the version string to it's numbers. If this fails we still have
     * the String version field in the database ...
     *
     * @param visit
     */
    private Visit setVersion(Visit visit) {
        try {
            String delims = "[.]+";
            String[] tokens = visit.getVersion().split(delims);
            visit.setvMajor(Integer.parseInt(tokens[0]));
            visit.setvMinor(Integer.parseInt(tokens[1]));
            visit.setvPatch(Integer.parseInt(tokens[2]));
            visit.setvBuild(Integer.parseInt(tokens[3]));
        } catch (Exception e) {
            // nothing to do.... we still have the "String" version
        }
        return visit;
    }
}
