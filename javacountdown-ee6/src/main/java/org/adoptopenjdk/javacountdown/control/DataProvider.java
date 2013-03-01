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
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * The main Data provider for the JAX-RS services
 */
@Named
public class DataProvider {

    private final static String GET_COUNTRIES = "SELECT visit.country, COUNT(visit.country) FROM Visit AS visit GROUP BY visit.country ORDER BY 'count'";
    private final static String GET_COUNTRY_FROM_GEO_DATA = "SELECT G.alpha2 FROM Geonames AS G ORDER BY ABS((ABS(G.latitude-:lat))+(ABS(G.longitude-:lng))) ASC";
    private static final Logger logger = Logger.getLogger(DataProvider.class.getName());
    @PersistenceContext(unitName = "javacountdownPU")
    EntityManager entityManager;

    /**
     * Get a list of all countries with data to display on the map
     *
     * TODO Can we return this as a domain object as opposed to a String?
     *
     * @return List of countries as a String
     */
    public String getCountries() {
        StringBuilder builder = new StringBuilder();

        Query query = entityManager.createQuery(GET_COUNTRIES);
        List<Object[]> results = query.getResultList();

        // TODO: Separate Java Versions. ATM this simply treats all the same.
        HashMap<String, Integer> all = new HashMap<>();

        for (Object[] result : results) {
            String country = (String) result[0];
            int count = ((Number) result[1]).intValue();
            // Remove unresolved locations
            if (!country.equalsIgnoreCase("unresolved")) {
                all.put(country, count);
            }
        }

        int total = 0; // get 100% base
        for (Integer value : all.values()) {
            total += value;
        }

        logger.log(Level.INFO, "Total: {0}", total);

        Set<String> keyset = all.keySet();
        Gson gson = new Gson();

        // Down to percentages
        for (String key : keyset) {
            int temp2 = all.get(key) * 100;
            float percentage = temp2 / total;
            int prettypercentage = (int) percentage;
            all.put(key, prettypercentage);
            logger.log(Level.INFO, "Key + Temp: {0} {1}", new Object[]{key, prettypercentage});
        }

        // if we don't have results we simply put an empty element to prevent 204 on the client
        if (all.isEmpty()) {
            all.put("", 0);
        }

        // Make json
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

        Query query = entityManager.createQuery(GET_COUNTRY_FROM_GEO_DATA);
        query.setParameter("lat", latitude);
        query.setParameter("lng", longitude);
        query.setMaxResults(3);
        List<Object[]> results = query.getResultList();

        logger.log(Level.INFO, "Are we lucky? lat {0} long {1}", new Object[]{latitude, longitude});

        if (results.size() > 0) {
            logger.log(Level.INFO, "we have a result");
            Object result = results.get(0);
            country = (String) result;
        }

        logger.log(Level.INFO, "Country: {0}", country);

        return country;
    }

    /**
     * TODO: Add validation and some error-checks.
     *
     * @param Visit visit
     */
    public void persistVisit(Visit visit) {

        logger.log(Level.INFO, "persist visit called: {0}", visit);

        String country = getCountryFromLatLong(visit.getLat(), visit.getLng());

        visit.setCountry(country);
        entityManager.persist(visit);

        logger.log(Level.INFO, "persisted {0}", visit);
    }
}
