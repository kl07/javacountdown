package org.adoptopenjdk.javacountdown.control;

import java.io.Serializable;
import java.util.Date;

import javax.enterprise.context.RequestScoped;



public class VisitTransfer implements  Serializable{
	
	private static final long serialVersionUID = 1L;
    private String version;
    private int vMajor;
    private int vMinor;
    private int vPatch;
    private int vBuild;
    private double latitude;
    private double longitude;
    private String country;
    private String browser;
    private String os;
    private Date time;

 

    /**
     * Default public constructor for JPA
     */
    public VisitTransfer() {
    }

    public String getVersion() {
        return this.version;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * Return a clone of the time to follow thread-safe programming practices
     *
     * @return a clone of the time
     */
    public Date getTime() {
        return (Date) time.clone();
    }

    /**
     * Set a clone of the time to follow thread-safe programming practices
     *
     * @param time
     */
    public void setTime(Date time) {
        this.time = (Date) time.clone();
    }


    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getvMajor() {
        return vMajor;
    }

    public void setvMajor(int vMajor) {
        this.vMajor = vMajor;
    }

    public int getvMinor() {
        return vMinor;
    }

    public void setvMinor(int vMinor) {
        this.vMinor = vMinor;
    }

    public int getvPatch() {
        return vPatch;
    }

    public void setvPatch(int vPatch) {
        this.vPatch = vPatch;
    }

    public int getvBuild() {
        return vBuild;
    }

    public void setvBuild(int vBuild) {
        this.vBuild = vBuild;
    }

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

//    @Override
//    public int hashCode() {
//        int hash = 3;
//        hash = 79 * hash + (this.id != null ? this.id.hashCode() : 0);
//        return hash;
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (obj == null) {
//            return false;
//        }
//        if (getClass() != obj.getClass()) {
//            return false;
//        }
//        final Visit other = (Visit) obj;
//        return this.id == other.id || (this.id != null && this.id.equals(other.id));
//    }
//
//    @Override
//    public String toString() {
//        return "Visit{" + "version=" + version + ", vMajor=" + vMajor + ", vMinor=" + vMinor + ", vPatch=" + vPatch + ", vBuild=" + vBuild + ", latitude=" + latitude + ", longitude=" + longitude + ", country=" + country + ", time=" + time + ", id=" + id + '}';
//    }

}
