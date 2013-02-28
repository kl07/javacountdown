/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
import org.jboss.arquillian.persistence.CreateSchema;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author eiselem
 */
@RunWith(Arquillian.class)
public class VersionResourceTest {

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
        WebResource webResource = client
                .resource(url);

        System.out.println("deploymentUrl: " + url);

        String input = "{\"version\":\"1.7.0.15\",\"lat\":48.2287258,\"lng\":11.6854924}";

        ClientResponse response = webResource.type("application/json")
                .post(ClientResponse.class, input);

        Assert.assertEquals(200, response.getStatus());
        System.out.println("POST /version HTTP/1.1\n\n" + response.getEntity(String.class));
    }

    @Test
    public void testGetData() throws Exception {


        Client client = Client.create();
        String url = deploymentUrl.toString() + RESOURCE_PREFIX + "/version";
        WebResource webResource = client
                .resource(url);

        System.out.println("deploymentUrl: " + url);

        ClientResponse response = webResource.accept("application/json")
                .get(ClientResponse.class);

        Assert.assertEquals(200, response.getStatus());
        System.out.println("GET /version HTTP/1.1\n\n" + response.getEntity(String.class));

    }
}