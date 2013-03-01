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
package org.adoptopenjdk.javacountdown.boundry;

import org.adoptopenjdk.javacountdown.entity.Visit;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

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
 * REST Web Service for the javacountdown website
 */
@Path("version")
@Stateless
public class VersionResource {

    private static final Logger logger = Logger.getLogger(VersionResource.class.getName());

    @Context
    private UriInfo context;

    @Inject
    private DataProvider dataProvider;

    /**
     * Retrieves visitor information from web client in JSON format
     *
     * @param content
     */
    @POST
    @Consumes("application/json")
    public void log(String content) {
        logger.log(Level.INFO, "Client input: {0}", content);

        Visit visit = null;
        Gson gson = new Gson();
        try {
            visit = gson.fromJson(content, Visit.class);
        } catch (JsonSyntaxException e) {
            logger.log(Level.SEVERE, "Deserialization went wrong", e);
        }
        dataProvider.persistVisit(visit);
        logger.log(Level.INFO, content);
    }

    /**
     * Gets Data for the map
     *
     * @return json object with data for the map.
     */
    @GET
    @Produces("application/json")
    public String getData() {

        String data = dataProvider.getCountries();
        logger.log(Level.INFO, data);
        return data;

    }
}
