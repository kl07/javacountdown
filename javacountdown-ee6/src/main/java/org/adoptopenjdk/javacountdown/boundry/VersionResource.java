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
package org.adoptopenjdk.javacountdown.boundry;

import org.adoptopenjdk.javacountdown.entity.Visit;
import com.google.gson.Gson;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import org.adoptopenjdk.javacountdown.control.DataProvider;

/**
 * REST Web Service for the javacountdown
 *
 * @author eiselem
 */
@Path("version")
@Stateless
public class VersionResource {

    private static final Logger l = Logger.getLogger(VersionResource.class.getName());
    @Context
    private UriInfo context;
    @Inject
    private DataProvider dp;

    /**
     * Creates a new instance of VersionResource
     */
    public VersionResource() {
    }

    /**
     * Retrieves visit information from webclient in JSON format
     *
     * @param content
     */
    @POST
    @Consumes("application/json")
    public void log(String content) {
        l.log(Level.INFO, content);

        Visit visit = null;
        try {
            Gson gson = new Gson();
            visit = gson.fromJson(content, Visit.class);
        } catch (Exception e) {
            l.log(Level.SEVERE, "Deserialization went wrong", e);
        }
        dp.persistVisit(visit);
        l.log(Level.INFO, content);

    }

    /**
     * Gets Data for the map
     *
     * @return json object with data for the map.
     */
    @GET
    @Produces("application/json")
    public String getData() {

        String data = dp.getCountries();
        l.log(Level.INFO, data);
        return data;

    }
}
