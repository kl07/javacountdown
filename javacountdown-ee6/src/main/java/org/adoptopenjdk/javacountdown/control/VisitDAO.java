package org.adoptopenjdk.javacountdown.control;


import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import javax.enterprise.inject.Alternative;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.DatastoreImpl;
import com.google.code.morphia.Key;
import com.google.code.morphia.dao.BasicDAO;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.QueryResults;
import com.google.code.morphia.query.UpdateOperations;
import com.google.code.morphia.query.UpdateResults;
import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.WriteConcern;
import com.mongodb.WriteResult;

import org.adoptopenjdk.javacountdown.entity.GeoPosition;
import org.adoptopenjdk.javacountdown.entity.Visit;
import org.bson.types.ObjectId;

 
public class VisitDAO extends BasicDAO<Visit, Key<Visit>> {
//MorphiaDAOAbstract<Visit, Key<Visit>>{

	private DatastoreImpl datastore;
	
	//public VisitDAO(){}
	

	public VisitDAO(Class<Visit> entityClass, DatastoreImpl datastore) {
		super(entityClass, datastore);
//		this.datastore = (DatastoreImpl)datastore;
//		this.datastore.getMapper().addMappedClass(entityClass);
	}
	
	
	public String getCountries(){
		/*
			db.visitor_test.aggregate(
				{$project: 	{country: 1, version: 1}},
				{$group:	{_id: "$country", total : { $sum: 1 }}}	
			)
		 */
		
		DBObject fields = new BasicDBObject("country", 1);		
		fields.put("version", 1);
		DBObject project = new BasicDBObject("$project", fields );
		
		DBObject groupFields = new BasicDBObject( "_id", "$country");	
		groupFields.put("total", new BasicDBObject( "$sum", 1));
				
		DBObject group = new BasicDBObject("$group", groupFields);
				
		AggregationOutput output = getCollection().aggregate( project, group );
		
		
		CommandResult cr = output.getCommandResult();
		Map map = cr.toMap();
		
		Iterable<DBObject> i = output.results();
		
		String results = output.toString();
		
		
		
		datastore.createQuery(entityClazz).countAll();
		
		
		return results;
	}
	
	
	


/**
 * Gets the GeoPosition of the visitor 
 */

	public GeoPosition getGeoPosition(double latitude, double longitude) {
		// TODO Auto-generated method stub
		return null;
	}






	



	
	
}
