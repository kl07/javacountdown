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
package net.eisele.javacountdown.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * A very tiny little helper
 *
 * @author eiselem
 */
public class JDBCHelper {

    private static final Logger log = Logger.getLogger(JDBCHelper.class.getName());
    // The insert into visits statement
    /**
     *
     */
    public final static String VISIT_STMT = "INSERT INTO VISIT (ID,COUNTRY, DATETIME_FIELD, JAVAVERSION, LATITUDE, LONGITUDE) VALUES (VISIT_SEQ.nextval,?,?,?,?,?)";
    // the country for lat/lng statement
    /**
     *
     */
    public final static String COUNTRY_FROM_LL = "select ALPHA2 from ( SELECT * FROM geonames W ORDER BY ABS(ABS(W.LATITUDE -?) + ABS(W.LONGITUDE -?)) ASC ) where ROWNUM <= 3";
    // the complete country list statement
    /**
     *
     */
    public final static String ALL_NUMBERS = "select country, count(country) from  visit group by country order by 'count'";

    /**
     * Return a connection
     *
     * @return a connection
     */
    public static Connection getConnection() {

        Connection connection = null;
        try {
            Context context = new InitialContext();
            DataSource dataSource = (javax.sql.DataSource) context.lookup("database");


            connection = dataSource.getConnection();


        } catch (Exception ex) {
            log.log(Level.SEVERE, "Error getting connection", ex);
        }
        return connection;
    }

    /**
     * Close connection, statement or resultset
     *
     * @param conn
     * @param stmt
     * @param rs
     */
    public static void close(Connection conn, PreparedStatement stmt,
            ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception ex) {
            }
        }

        if (stmt != null) {
            try {
                stmt.close();
            } catch (Exception ex) {
            }
        }

        if (conn != null) {
            try {
                conn.close();
            } catch (Exception ex) {
            }
        }
    }
}
