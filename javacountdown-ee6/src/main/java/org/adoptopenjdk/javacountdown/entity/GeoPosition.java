package org.adoptopenjdk.javacountdown.entity;


import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;

import com.google.code.morphia.annotations.Embedded;

@RequestScoped
@Entity(value="geoposition", noClassnameStored=true)
public class GeoPosition implements Serializable{
	private static final long serialVersionUID = 1L;
	
    @Id
    private ObjectId id;
	private String country;
	private String city;
	@Embedded
	private Location location;
	
	
	public GeoPosition() {}

	
	public ObjectId getId(){
		return id;
	}
	
	public void setId(ObjectId id){
		this.id = id;
	}

	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	
	
	
	
	
//    public String toString() {
//        return "Location(type: " + type + ", coordinates: " + coordinates + ")";
//    }
//    
//	@Override
//	public int hashCode() {
//		int hash = 0;
//		hash += (coordinates != null ? coordinates.hashCode() : 0);
//		return hash;
//	}
//
//	@Override
//	public boolean equals(Object object) {
//		if (!(object instanceof Location)) {
//			return false;
//		}
//		Location other = (Location) object;
//		if ((this.coordinates == null && other.coordinates != null)
//				|| (this.coordinates != null && !this.coordinates.equals(other.coordinates))) {
//			return false;
//		}
//		return true;
//	}
	
}
