/*
 * Copyright [2013] Adopt OpenJDK Programme
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.adoptopenjdk.javacountdown.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Visitor class, represents an end user hitting a website with their Java
 * applet enabled event.
 */
@Entity
public class Visit implements Serializable {

    private static final long serialVersionUID = 1L;
    private String version;
    private int vMajor;
    private int vMinor;
    private int vPatch;
    private int vBuild;
    private double latitude;
    private double longitude;
    private String country;
    @Temporal(TemporalType.DATE)
    @Column(name = "entered")
    private Date time;
    @Id
    @SequenceGenerator(name = "Visit_SEQ", allocationSize = 5, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Visit_SEQ")
    private Long id;

    /**
     * Default public constructor for JPA
     */
    public Visit() {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Visit other = (Visit) obj;
        return this.id == other.id || (this.id != null && this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "Visit{" + "version=" + version + ", vMajor=" + vMajor + ", vMinor=" + vMinor + ", vPatch=" + vPatch + ", vBuild=" + vBuild + ", latitude=" + latitude + ", longitude=" + longitude + ", country=" + country + ", time=" + time + ", id=" + id + '}';
    }

}
