package org.adoptopenjdk.javacountdown.control;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.DatastoreImpl;
import com.google.code.morphia.Key;
import com.google.code.morphia.dao.BasicDAO;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.adoptopenjdk.javacountdown.entity.Visit;
import org.adoptopenjdk.javacountdown.entity.GeoPosition;



@ApplicationScoped
public class MorphiaDOAFactory {
	
	
	@Inject
	private DatastoreImpl datastore;
	
	
	@Produces @VisitQ
	public BasicDAO<Visit, Key<Visit>> createVisitDAO(){	
		return new VisitDAO(Visit.class, datastore);
			
	}
	
	@Produces @GeoPositionQ
	public BasicDAO<GeoPosition, Key<GeoPosition>> createGeoPositionDOA(){		
		return new GeoPositionDAO(GeoPosition.class, this.datastore);	
	}

}
