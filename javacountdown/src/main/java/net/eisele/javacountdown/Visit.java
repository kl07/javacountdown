/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.eisele.javacountdown;

/**
 *
 * @author eiselem
 */
public class Visit {
    // version=1.7.0.13&lat=48.228768699999996&lng=11.6854984

    private String version;
    private String lat;
    private String lng;

    public Visit() {
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "Visit{" + "version=" + version + ", lat=" + lat + ", lng=" + lng + '}';
    }
}
