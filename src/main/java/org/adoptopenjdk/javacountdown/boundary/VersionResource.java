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
package org.adoptopenjdk.javacountdown.boundary;

import org.adoptopenjdk.javacountdown.control.DataProvider;
import org.adoptopenjdk.javacountdown.control.ResultCache;
import org.adoptopenjdk.javacountdown.control.VisitTransfer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * REST Web Service for the javacountdown website.
 */
@Path("version")
@Stateless
public class VersionResource {

    private static final Logger logger = LoggerFactory.getLogger(VersionResource.class);

    @Inject
    private DataProvider dataProvider;

    @EJB
    ResultCache cache;

    /**
     * Retrieves visitor information from web client in JSON format.
     * 
     * @param visit
     *            The client visit information
     * @return A HTTP 202 Accepted response
     */
    @POST
    @Consumes("application/json")
    public Response sendVisit(String visit) {

        VisitTransfer visitTransfer = null;
        Gson gson = new Gson();
        try {
            visitTransfer = gson.fromJson(visit, VisitTransfer.class);
        } catch (JsonSyntaxException e) {
            logger.warn("Could not deserialize client input, message: {}", e.getMessage());
            throw new WebApplicationException(e, Response.status(Response.Status.BAD_REQUEST).build());
        }
        dataProvider.persistVisit(visitTransfer);
        return Response.noContent().build();
    }

    /**
     * Returns the JDK adoption data.
     * 
     * @return A map containing the JDK adoption for the countries
     */
    @GET
    @Produces("application/json")
    public Response getJdkAdoption() {
        return Response.ok(cache.getCountryData()).build();
    }
}