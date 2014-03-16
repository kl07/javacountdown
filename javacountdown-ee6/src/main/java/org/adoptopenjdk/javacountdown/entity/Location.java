package org.adoptopenjdk.javacountdown.entity;



import com.google.code.morphia.annotations.Embedded;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;


@Embedded
public class Location implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String type;
	private List<Float> coordinates = new ArrayList<>();
	
	
	public Location() {
	}

	public Location(String type) {
		this.type = type;
	}

	public Location(String type, List<Float> coordinates) {
		this.type = type;
		this.coordinates = coordinates;
	}
		
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type=type;
	}
	
	
	public List<Float> getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(List<Float> coordinates) {
		this.coordinates=coordinates;
	}
	
	
	public void addCoordinates(String lon, String lat){		
		coordinates.add(Float.valueOf(lon));
		coordinates.add(Float.valueOf(lat));
	}
	
    public String toString() {
        return "Location(type: " + type + ", coordinates: " + coordinates + ")";
    }
    
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (coordinates != null ? coordinates.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Location)) {
			return false;
		}
		Location other = (Location) object;
		if ((this.coordinates == null && other.coordinates != null)
				|| (this.coordinates != null && !this.coordinates.equals(other.coordinates))) {
			return false;
		}
		return true;
	}
	
}
