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

import com.google.code.morphia.Key;
import com.google.code.morphia.dao.BasicDAO;
import com.google.gson.Gson;
import org.adoptopenjdk.javacountdown.control.DataAccessObject.Type;
import org.adoptopenjdk.javacountdown.entity.AdoptionReportCountry;
import org.adoptopenjdk.javacountdown.entity.GeoPosition;
import org.adoptopenjdk.javacountdown.entity.VersionInfo;
import org.adoptopenjdk.javacountdown.entity.Visit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.util.Date;
import java.util.Map;

/**
 * The main Data provider for the JAX-RS services
 */
@Stateless
public class DataProvider {

    private static final String EMPTY_STRING = "";
    private static final Logger logger = LoggerFactory.getLogger(DataProvider.class);

    @Inject
    @DataAccessObject(Type.VISIT)
    BasicDAO<Visit, Key<Visit>> visitDAO;

    @Inject
    @DataAccessObject(Type.GEOPOSITION)
    BasicDAO<GeoPosition, Key<GeoPosition>> geoPositionDAO;

    @Inject
    @DataAccessObject(Type.REPORT)
    BasicDAO<AdoptionReportCountry, Key<AdoptionReportCountry>> adoptionReportDAO;

    @Inject
    Event<Visit> visitEvent;

    /**
     * This retrieves the country code based on the given latitude/longitude.
     * It should return a ISO 3166 alpha-2 code.
     * Refer to http://www.maxmind.com/en/worldcities for the data behind it.
     *
     * @param latitude  The latitude
     * @param longitude The longitude
     * @return GeoPosition
     */
    private GeoPosition getGeoPositionFromLatLong(double latitude, double longitude) {

        GeoPosition geoPosition = ((GeoPositionDAO) geoPositionDAO).getGeoPosition(latitude, longitude);

        if (!EMPTY_STRING.equals(geoPosition.getCountry())) {
            logger.debug("Country code {} found for lat/lng: {},{}", geoPosition.getCountry(), latitude, longitude);
        } else {
            logger.error("No country code found for lat/lng: {},{}.", latitude, longitude);
        }

        return geoPosition;
    }

    /**
     * Persists a Visit entity. This only gets called when the visit could be
     * parsed by GSON. No further checks necessary here.
     *
     * @param visit The visit to persist
     */
    @Asynchronous
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void persistVisit(VisitTransfer visitTransfer) {

        GeoPosition geoPosition = getGeoPositionFromLatLong(visitTransfer.getLatitude(), visitTransfer.getLongitude());
        VersionInfo versionInfo = constructVersionInfo(visitTransfer);

        Visit visit = new Visit();
        visit.setVersion(versionInfo.getMinorVersion());
        visit.setVersionInfo(versionInfo);
        visit.setCountry(geoPosition.getCountry());
        visit.setGeoPosition(geoPosition);
        visit.setBrowser(visitTransfer.getBrowser());
        visit.setOs(visitTransfer.getOs());
        visit.setTime(new Date(System.currentTimeMillis()));

        Key<Visit> key = null;
        try {
            key = visitDAO.save(visit);
            logger.debug("Visit persisted with key {}", key);
        } catch (Exception e) {
            logger.error("Could not persist Visit {}, message: {}", visit, e.getMessage());
        } finally {
            visitEvent.fire(visit);
        }

    }

    /**
     * Gets a list of all countries with data to display on the map, this is
     * returned directly as a String as that's the expected format
     *
     * @return List of countries and percentage adoption as a String
     */
    public String getJdkAdoptionReport() {

        Map<String, Integer> jdkAdoptionCountry = ((AdoptionReportDAO) adoptionReportDAO).getJdkAdoption();
        Gson gson = new Gson();
        String json = gson.toJson(jdkAdoptionCountry);

        logger.debug("Retrieved JDK adoption as JSON: {} ", json);

        return json;
    }

    /**
     * Parsing the version string to it's numbers.
     *
     * @param visitTransfer The visit
     * @return VersionInfo
     */
    private static VersionInfo constructVersionInfo(VisitTransfer visitTransfer) {
        VersionInfo versionInfo = new VersionInfo();
        if (visitTransfer.getVersion() != null) {
            try {

                String delims = "[.]+";
                String[] tokens = visitTransfer.getVersion().split(delims);
                versionInfo.setMajorVersion(Integer.parseInt(tokens[0]));
                versionInfo.setMinorVersion(Integer.parseInt(tokens[1]));
                versionInfo.setPatchVersion(Integer.parseInt(tokens[2]));
                versionInfo.setBuildVersion(Integer.parseInt(tokens[3]));
            } catch (NumberFormatException | NullPointerException e) {
                logger.warn("Failed to parse version {} ", visitTransfer.getVersion());
            }
        }
        return versionInfo;
    }

}
