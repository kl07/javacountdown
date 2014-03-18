package org.adoptopenjdk.javacountdown;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBObject;

public class JSONUtility {

    private static final int LATITUDE = 5;
    private static final int LONGITUDE = 6;

    /**
     * Constructs a JSON document based on a String Array.
     * 
     * @param data
     * @return JSON document
     */
    public static BasicDBObject constructJSONDocument(String[] data) {

        List<Float> coordinates = new ArrayList<>();
        coordinates.add(Float.valueOf(data[LONGITUDE]));
        coordinates.add(Float.valueOf(data[LATITUDE]));

        BasicDBObject embeddedGeoJSON = new BasicDBObject("type", "Point").append("coordinates", coordinates);

        BasicDBObject document = new BasicDBObject("country", data[0]).append("city", data[2]).append("location",
                embeddedGeoJSON);

        return document;
    }

}
