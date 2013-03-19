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
package org.adoptopenjdk.javacountdown.boundary;

import org.adoptopenjdk.javacountdown.boundary.VersionResource;
import com.jayway.restassured.http.ContentType;
import java.io.File;
import java.net.URL;
import org.adoptopenjdk.javacountdown.control.DataProvider;
import org.adoptopenjdk.javacountdown.entity.Visit;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OperateOnDeployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.persistence.ApplyScriptBefore;
import org.jboss.arquillian.persistence.Cleanup;
import org.jboss.arquillian.persistence.CreateSchema;
import org.jboss.arquillian.persistence.TestExecutionPhase;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import javax.ws.rs.core.Response.Status;

/**
 * Testing the REST interface methods
 */
@RunWith(Arquillian.class)
@CreateSchema({"derby/create-ddl.sql"})
@Cleanup(phase = TestExecutionPhase.NONE)
public class VersionResourceTest {

    private static final Logger logger = LoggerFactory.getLogger(VersionResourceTest.class);
    private static final String RESOURCE_PREFIX = "rest";
    private static final String REST_ENDPOINT = "version";

    /**
     * Creating the ShrinkWrap deployment for Arquillian. This only contains the
     * backend!
     *
     * @return
     */
    @Deployment(name = "rest")
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addPackage(VersionResource.class.getPackage())
                .addPackage(DataProvider.class.getPackage())
                .addPackage(Visit.class.getPackage())
                .addAsWebInfResource("test-persistence.xml", "classes/META-INF/persistence.xml")
                .addAsWebInfResource(new File("src/main/webapp", "WEB-INF/web.xml"))
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    /**
     * Simple Test used as a workaround because Arquillian doesn't support
     * Before/AfterClass in core yet. It simply triggers the CreateSchema class annotation.
     * @throws Exception
     */
    @SuppressWarnings("static-method")
    @Test
    @InSequence(1)
    public void createSchema() throws Exception {
        logger.info("createSchema");
    }

    /**
     * We need to clean up afterwards to make the test repeatable. Derby does
     * not provide 'drop if exists'
     */
    @SuppressWarnings("static-method")
    @Test
    @InSequence(5)
    @ApplyScriptBefore("derby/drop.sql")
    public void cleanSchema() {
        logger.info("cleanSchema");
    }

    /**
     * Testing a well formed log submission POST /version
     *
     * @param deploymentUrl
     * @throws Exception
     */
    @SuppressWarnings("static-method")
    @Test
    @InSequence(2)
    @RunAsClient
    public void testLog(@ArquillianResource @OperateOnDeployment("rest") URL deploymentUrl) throws Exception {
        logger.info("testLog");
        String url = deploymentUrl.toString() + RESOURCE_PREFIX + "/" + REST_ENDPOINT;
        String input =
                "{\"version\":\"1.7.0.15\",\"lat\":48.2287258,\"lng\":11.6854924}";
        // should return Response.noContent() which is a 204
        given().body(input).contentType(ContentType.JSON).expect().statusCode(Status.NO_CONTENT.getStatusCode()).log().ifError().when().post(url);

    }

    /**
     * Testing a malformed log submission POST /version
     *
     * @param deploymentUrl
     * @throws Exception
     */
    @SuppressWarnings("static-method")
    @Test
    @InSequence(3)
    @RunAsClient
    public void testWrongJson(@ArquillianResource @OperateOnDeployment("rest") URL deploymentUrl) throws Exception {
        logger.info("testWrongJson");
        String url = deploymentUrl.toString() + RESOURCE_PREFIX + "/" + REST_ENDPOINT;
        String maleFormedJson =
                "{\"version\":\"1.7.0.15\",\"lat\":48.2287258,";
        // should return a 400 bad request
        given().body(maleFormedJson).contentType(ContentType.JSON).expect().statusCode(Status.BAD_REQUEST.getStatusCode()).log().ifError().when().post(url);

    }

    /**
     * Testing the returned map data GET /version
     *
     * @param deploymentUrl
     * @throws Exception
     */
    @SuppressWarnings("static-method")
    @Test
    @InSequence(4)
    @RunAsClient
    public void testGetData(@ArquillianResource @OperateOnDeployment("rest") URL deploymentUrl) throws Exception {
        logger.info("testGetData");
        String url = deploymentUrl.toString() + RESOURCE_PREFIX + "/" + REST_ENDPOINT;
        given().contentType(ContentType.JSON).expect().body("BB", equalTo(Integer.valueOf(100))).statusCode(Status.OK.getStatusCode()).log().ifError().when().get(url);
    }
}