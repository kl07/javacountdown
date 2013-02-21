/**
 * Copyright [2013] Markus Eisele
 *
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
package net.eisele.javacountdown;

import com.google.gson.Gson;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.eisele.javacountdown.util.JDBCHelper;

/**
 * The main Data provider for the JAX-RS services
 *
 * @author eiselem
 */
public class DataProvider {

    private static final Logger log = Logger.getLogger(DataProvider.class.getName());

    public DataProvider() {
    }

    /**
     * Get a list of all countries with data to display on the map
     *
     * @return
     */
    public String getCountries() {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        StringBuilder builder = new StringBuilder();

        try {
            connection = JDBCHelper.getConnection();
            statement = connection.prepareStatement(JDBCHelper.ALL_NUMBERS);
            rs = statement.executeQuery();

            /**
             *
             * TODO: Separate Java Versions. Atm this simply treats all the
             * same.
             *
             */
            HashMap<String, Integer> all = new HashMap<String, Integer>();

            while (rs.next()) {
                String country = rs.getString(1);
                int count = rs.getInt(2);
                // remove unresolved locations
                if (!country.equalsIgnoreCase("unresolved")) {
                    all.put(country, count);
                }
            }
            JDBCHelper.close(connection, statement, null);

            int total = 0; // get 100% base
            for (Integer value : all.values()) {
                total += value;
            }

            log.log(Level.FINE, "Total: {0}", total);

            Set<String> keyset = all.keySet();
            Gson gson = new Gson();


            // Down to percentages
            for (String key : keyset) {
                int temp2 = all.get(key) * 100;
                float percentage = temp2 / total;
                int prettypercentage = (int) percentage;
                all.put(key, prettypercentage);

                log.log(Level.FINE, "Key + Temp: {0} {1}", new Object[]{key, prettypercentage});
            }
            // make json
            String json = gson.toJson(all);
            builder.append(json);

        } catch (Exception ex) {
            log.log(Level.SEVERE, null, ex);
            JDBCHelper.close(connection, statement, null);
        }
        log.log(Level.FINE, "<< BUILDER {0}", builder.toString());
        return builder.toString();
    }

    /**
     * This is where parts of the magic already happens. This does a select on
     * the geonames table and does some searching for the nearest country entry
     * with the lat/lng. It should return a ISO 3166 alpha-2 code. Refer to
     * blog.stavi.sh/country-list-iso-3166-codes-latitude-longitud for the data
     * behind it.
     *
     * @param String lat
     * @param String lng
     * @return ISO 3166 alpha 2 code
     */
    private String getCountryFromLatLong(String lat, String lng) {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        String country = "unresolved";
        try {

            connection = JDBCHelper.getConnection();
            statement = connection.prepareStatement(JDBCHelper.COUNTRY_FROM_LL);
            statement.setFloat(1, Float.parseFloat(lat));
            statement.setFloat(2, Float.parseFloat(lng));



            log.log(Level.INFO, "Are we lucky? {0} long {1}", new Object[]{lat, lng});
            rs = statement.executeQuery();

            if (rs.next()) {
                log.log(Level.INFO, "we have a result");

                country = rs.getString(1);
            }

            JDBCHelper.close(connection, statement, null);

        } catch (Exception ex) {
            log.log(Level.SEVERE, null, ex);
            JDBCHelper.close(connection, statement, null);
        }


        return country;
    }

    /**
     * TODO: Add validation and some error-checks.
     *
     * @param Visit visit
     */
    public void persistVisit(Visit visit) {

        log.log(Level.INFO, "persist visit called: {0}", visit);
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            String country = getCountryFromLatLong(visit.getLat(), visit.getLng());
            log.log(Level.INFO, "country resolved {0}", country);
            connection = JDBCHelper.getConnection();
            statement = connection.prepareStatement(JDBCHelper.VISIT_STMT);
            statement.setString(1, country);
            statement.setDate(2, new Date(System.currentTimeMillis()));
            statement.setString(3, visit.getVersion());
            statement.setFloat(4, Float.parseFloat(visit.getLat()));
            statement.setFloat(5, Float.parseFloat(visit.getLng()));
            boolean execute = statement.execute();
            JDBCHelper.close(connection, statement, null);
            log.log(Level.INFO, "persisted {0}", execute);
        } catch (Exception ex) {
            log.log(Level.SEVERE, null, ex);
            JDBCHelper.close(connection, statement, null);
        }


    }
}
