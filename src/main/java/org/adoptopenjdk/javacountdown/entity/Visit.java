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

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.PostLoad;
import com.google.code.morphia.annotations.PrePersist;
import com.google.code.morphia.annotations.Reference;
import com.google.code.morphia.annotations.Transient;

import org.bson.types.ObjectId;
import org.joda.time.DateTime;

import javax.enterprise.context.RequestScoped;

import java.io.Serializable;
import java.util.Date;

/**
 * Visit class, represents an end user hitting a website with their Java applet
 * enabled event.
 * 
 * @author Alex Theedom
 */
@RequestScoped
@Entity(value = "visitors", noClassnameStored = true)
public class Visit implements Serializable {

    private static final long serialVersionUID = -5580843065068184730L;

    @Id
    private ObjectId id;
    private int version;
    private VersionInfo versionInfo;
    private String country;

    @Reference
    private GeoPosition geoPosition;
    private BrowserInfo browserInfo;
    private String os;

    @Transient
    private DateTime time; // Yoda time
    private Date date; // Java time. We persist this.

    @PrePersist
    public void dateTimeToDate() {
        setDate(getTime().toDate());
    }

    @PostLoad
    public void dateToDateTime() {
        setTime(new DateTime(getDate()));
    }

    public Visit() {
        setTime(new DateTime());
    }

    public boolean isVersion(int versionToCheckAgainst) {
        return this.version == versionToCheckAgainst;
    }

    public GeoPosition getGeoPosition() {
        return geoPosition;
    }

    public void setGeoPosition(GeoPosition geoPosition) {
        this.geoPosition = geoPosition;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public DateTime getTime() {
        return time;
    }

    public void setTime(DateTime dateTime) {
        this.time = dateTime;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public VersionInfo getVersionInfo() {
        return versionInfo;
    }

    public void setVersionInfo(VersionInfo versionInfo) {
        this.versionInfo = versionInfo;
    }

    public BrowserInfo getBrowserInfo() {
        return browserInfo;
    }

    public void setBrowserInfo(BrowserInfo browserInfo) {
        this.browserInfo = browserInfo;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
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
        return "Visit [id=" + id + ", version=" + version + ", versionInfo=" + versionInfo + ", country=" + country
                + ", geoPosition=" + geoPosition + ", browser=" + browserInfo + ", os=" + os + ", time=" + time + "]";
    }

    public Date getDate() {
        return (Date)date.clone();
    }

    public void setDate(Date date) {
        this.date = (Date)date.clone();
    }

}
