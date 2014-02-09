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

import com.google.code.morphia.Key;
import com.google.code.morphia.dao.BasicDAO;
import com.google.gson.Gson;

import org.adoptopenjdk.javacountdown.entity.JdkAdoptionCountry;
import org.adoptopenjdk.javacountdown.entity.VersionInfo;
import org.adoptopenjdk.javacountdown.entity.GeoPosition;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.inject.Inject;

/**
 * The main Data provider for the JAX-RS services
 */
@Stateless
public class DataProvider {

    private static final String EMPTY_STRING = "";
    private static final Logger logger = Logger.getLogger(DataProvider.class.getName());
         
	@Inject @VisitQ
	BasicDAO<Visit, Key<Visit>> visitDAO;
     
    @Inject @GeoPositionQ
    BasicDAO<GeoPosition, Key<GeoPosition>> geoPositionDAO;

    @Inject @JdkAdoptionQ
    BasicDAO<JdkAdoptionCountry, Key<JdkAdoptionCountry>> jdkAdoptionDAO;

       
 
    /**
     * This retrieves the country code based on the given latitude/longitude. 
     * It should return a ISO 3166 alpha-2 code.
     * Refer to http://www.maxmind.com/en/worldcities for the data behind it.
     *
     * @param String latitude
     * @param String longitude
     * @return ISO 3166 alpha 2 code
     */  
    private GeoPosition getGeoPositionFromLatLong(double latitude, double longitude) {
    	
//    	longitude = 51.511214;
//    	latitude  = -0.119824;
//    	
//    	latitude =  -3.7177154999999997;
//    	longitude = 40.4701242;
//    	
//    	latitude  = 51.513878;
//    	longitude =-0.161945;
   	
    	GeoPosition geoPosition = ((GeoPositionDAO)geoPositionDAO).getGeoPosition(latitude, longitude);

        logger.log(Level.FINE, "Are we lucky? lat {0} long {1}", new Object[]{latitude, longitude});

        if (!EMPTY_STRING.equals(geoPosition.getCountry())) {
            logger.log(Level.FINE, "Country: {0}", geoPosition.getCountry());
        } else {
            logger.log(Level.FINE, "No country code found.");
        }

        return geoPosition;
    }
    
    
    
    /**
     * Persisting a Visit entity This only gets called when the visit could be
     * parsed by GSON. No further checks necessary here.
     *
     * @param visit
     */
    @Asynchronous
    public void persistVisit(VisitTransfer visitTransfer) {
    	Visit visit = new Visit();
    	GeoPosition geoPosition = getGeoPositionFromLatLong(visitTransfer.getLatitude(), visitTransfer.getLongitude());
    	VersionInfo versionInfo = constructVersioninfo(visitTransfer);
    	
    	visit.setVersion(versionInfo.getvMajor());
    	visit.setVersionInfo(versionInfo); 
         	
        visit.setCountry(geoPosition.getCountry());       
        visit.setGeoPosition(geoPosition); 
 
        visit.setBrowser(visitTransfer.getBrowser());
        visit.setOs(visitTransfer.getOs());
                       
        visit.setTime(new Date(System.currentTimeMillis()));
        
        Key<Visit> dbRef = visitDAO.save(visit);
        
        logger.log(Level.FINE, "persisted {0}", visit);
        logger.log(Level.FINE, "persisted dbRef {0}", dbRef);
        
        
        // Update the JDK adoption world map data     
        
        JdkAdoptionCountry jdkAdoptionCountryTotals = ((JdkAdoptionDAO) jdkAdoptionDAO).getCountryTotals(geoPosition.getCountry());
        if(jdkAdoptionCountryTotals == null){
        	jdkAdoptionCountryTotals = new JdkAdoptionCountry(visit);
        }
    	jdkAdoptionCountryTotals.updateTotals(visit);
        jdkAdoptionDAO.save(jdkAdoptionCountryTotals);              
    }

    
    
    /**
     * Get a list of all countries with data to display on the map, this is
     * returned directly as a String as that's the expected format
     *
     * @return List of countries and percentage adoption as a String
     */
	public String getJdkAdoption() {
		
		Map<String, Integer> jdkAdoptionCountry = ((JdkAdoptionDAO) jdkAdoptionDAO).getJdkAdoption();
		  
        Gson gson = new Gson();
        String json = gson.toJson(jdkAdoptionCountry);
		System.out.println(json);
		return json;
	}  
    
	
	
    /**
     * Parsing the version string to it's numbers. If this fails we still have
     * the String version field in the database ...
     *
     * @param visit
     * @return The Visit, probably instrumented with version data
     */
    private static VersionInfo constructVersioninfo(VisitTransfer visit) {
    	VersionInfo versionInfo = new VersionInfo();
    	if (visit.getVersion() != null){
	        try {
	        	
	            String delims = "[.]+";
	            String[] tokens = visit.getVersion().split(delims);
	            versionInfo.setvMajor(Integer.parseInt(tokens[0]));
	            versionInfo.setvMinor(Integer.parseInt(tokens[1]));
	            versionInfo.setvPatch(Integer.parseInt(tokens[2]));
	            versionInfo.setvBuild(Integer.parseInt(tokens[3]));
	        } catch (NumberFormatException | NullPointerException e) {
	            logger.fine("Failed to parse version, but that's OK, "
	                    + "we still have the string variant stored in the data store.");
	        }
    	}
        return versionInfo;
    }



}
