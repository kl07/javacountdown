/*
 * Copyright 2013 Adopt OpenJDK.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.adoptopenjdk.javacountdown.control;

import com.google.code.morphia.DatastoreImpl;
import com.google.code.morphia.Morphia;
import com.mongodb.MongoClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import java.net.UnknownHostException;

/**
 * Produces Morphia datastore objects used by the DAO to persist data in
 * MongoDB.
 * 
 * @author Alex Theedom
 */
@Startup
@Singleton
@ApplicationScoped
public class MorphiaDatastore {

    private static final Logger logger = LoggerFactory.getLogger(MorphiaDatastore.class);

    private static final String DATABASE_NAME = "jcountdown";
    private static final String HOST = "localhost";
    private static final int PORT = 27017;

    @Produces
    public static DatastoreImpl getDatastore() {

        MongoClient mongoClient = null;
        DatastoreImpl datastore = null;

        try {
            mongoClient = new MongoClient(HOST, PORT);

            Morphia morphia = new Morphia();

            datastore = (DatastoreImpl) morphia.createDatastore(mongoClient, DATABASE_NAME);
            logger.debug("Created Mongo Datastore");

        } catch (UnknownHostException e) {
            logger.error("Can not resolve DB host, message: {}", e.getMessage());
        }

        return datastore;
    }

}
