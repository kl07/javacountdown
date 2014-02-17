package org.adoptopenjdk.javacountdown.control;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.inject.Alternative;

import org.adoptopenjdk.javacountdown.entity.GeoPosition;
import org.adoptopenjdk.javacountdown.entity.Visit;
import org.bson.types.ObjectId;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.DatastoreImpl;
import com.google.code.morphia.Key;
import com.google.code.morphia.dao.BasicDAO;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;


public class GeoPositionDAO extends BasicDAO<GeoPosition, Key<GeoPosition>> {
	
	private static final Logger logger = Logger.getLogger(GeoPositionDAO.class.getName());
	
	private DatastoreImpl datastore;
	
	//public GeoPositionDAO(){}
	
	public GeoPositionDAO(Class<GeoPosition> entityClass, DatastoreImpl datastore) {
		super(entityClass, datastore);
//		this.datastore = (DatastoreImpl)datastore;
//		this.datastore.getMapper().addMappedClass(entityClass);
	}


	public GeoPosition getGeoPosition(double latitude, double longitude){
		
		logger.log(Level.FINE, "Lets find country code");
		
		GeoPosition geoPosition = new GeoPosition();	
		
		List<Double> coordinates = new ArrayList<>();
		coordinates.add(longitude);
		coordinates.add(latitude);
		
		BasicDBObject query = 	new BasicDBObject("location.coordinates", 
								new BasicDBObject("$near", 
								new BasicDBObject("$geometry",
								new BasicDBObject("type", "Point").append("coordinates", coordinates))));
		
		DBCursor cursor =  getCollection().find(query).limit(1);

		if (cursor.hasNext()) {
			
			DBObject dbObject = cursor.next();			
			String countryCode 	= (String) dbObject.get("country");
			ObjectId id 		= (ObjectId) dbObject.get("_id");
			geoPosition.setCountry(countryCode);
			geoPosition.setId(id);
		} 
		
		return geoPosition;
		
	}

}
