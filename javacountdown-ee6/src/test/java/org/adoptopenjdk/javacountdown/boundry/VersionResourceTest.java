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

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.io.File;
import java.net.URL;
import org.adoptopenjdk.javacountdown.control.DataProvider;
import org.adoptopenjdk.javacountdown.entity.Visit;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO Explanation fo what use cases this covers.
 */
@RunWith(Arquillian.class)
public class VersionResourceTest {

    private static final Logger logger = LoggerFactory.getLogger(VersionResourceTest.class);
    
    private static final String RESOURCE_PREFIX = "rest";

    @ArquillianResource
    URL deploymentUrl;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addPackage(VersionResource.class.getPackage())
                .addPackage(DataProvider.class.getPackage())
                .addPackage(Visit.class.getPackage())
                .addAsWebInfResource("test-persistence.xml", "classes/META-INF/persistence.xml")
                .addAsWebInfResource(new File("src/main/webapp", "WEB-INF/web.xml"))
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    public void testLog() throws Exception {

        Client client = Client.create();
        String url = deploymentUrl.toString() + RESOURCE_PREFIX + "/version";
        WebResource webResource = client.resource(url);

        logger.info("deploymentUrl: " + url);

        String input = "{\"version\":\"1.7.0.15\",\"lat\":48.2287258,\"lng\":11.6854924}";

        webResource.type("application/json");
        ClientResponse response = webResource.type("application/json")
                                             .post(ClientResponse.class, input);

        Assert.assertEquals(200, response.getStatus());
        logger.info("POST /version HTTP/1.1\n\n" + response.getEntity(String.class));
    }

    @Test
    public void testGetData() throws Exception {

        Client client = Client.create();
        String url = deploymentUrl.toString() + RESOURCE_PREFIX + "/version";
        WebResource webResource = client.resource(url);

        logger.info("deploymentUrl: " + url);

        ClientResponse response = webResource.accept("application/json")
                .get(ClientResponse.class);

        // TODO Martijn's local dev environment gets a 204 - NO CONTENT
        Assert.assertEquals(200, response.getStatus());
        logger.info("GET /version HTTP/1.1\n\n" + response.getEntity(String.class));
    }
}