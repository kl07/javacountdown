package org.adoptopenjdk.javacountdown;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

/**
 * Used for one-off importing the CSV based GeoData into MongoDB
 * 
 * TODO: Could change s.o.println to proper logging
 */
public class ImportGeoData {

    private static final int PORT = 27017;
    private static final String HOST = "localhost";

    private static final String COLLECTION_NAME = "geoposition";
    private static final String DATABASE_NAME = "jcountdown";

    private static final String CSV_FILE_LOCATION = "geoposition.csv";

    public static void main(String... args) {
        execute();
    }

    /**
     * This method performs the import into the Mongo database.
     */
    public static void execute() {

        String line = "";
        String delimiter = ",";

        DBCollection collection = getDBCollection();

        Path geoData = Paths.get(CSV_FILE_LOCATION);

        try (BufferedReader bufferedReader = Files.newReader(geoData.toFile(), Charsets.UTF_8)) {
            while ((line = bufferedReader.readLine()) != null) {
                String[] country = line.split(delimiter);
                BasicDBObject document = JSONUtility.constructJSONDocument(country);
                collection.insert(document);
            }

            System.out.println("Number of documents inserted: " + collection.count());

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Done");
    }

    /**
     * @return a connection to the Mongo database
     */
    private static DBCollection getDBCollection() {
        DBCollection collection = null;
        try {
            DB db = getDBConnection();
            collection = db.getCollection(COLLECTION_NAME);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return collection;
    }

    /**
     * @return DB Connection
     * @throws UnknownHostException
     */
    private static DB getDBConnection() throws UnknownHostException {
        MongoClient mongoClient;
        mongoClient = new MongoClient(HOST, PORT);
        DB db = mongoClient.getDB(DATABASE_NAME);
        return db;
    }

}
