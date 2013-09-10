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
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Entity that represents geographical locations
 */
@Entity
@Table(name = "GEONAMES")
@NamedQueries({
    @NamedQuery(name = "Geonames.findAll", query = "SELECT g FROM Geonames g"),
    @NamedQuery(name = "Geonames.findByCountry", query = "SELECT g FROM Geonames g WHERE g.country = :country"),
    @NamedQuery(name = "Geonames.findByAlpha2", query = "SELECT g FROM Geonames g WHERE g.alpha2 = :alpha2"),
    @NamedQuery(name = "Geonames.findByAlpha3", query = "SELECT g FROM Geonames g WHERE g.alpha3 = :alpha3"),
    @NamedQuery(name = "Geonames.findByNumcode", query = "SELECT g FROM Geonames g WHERE g.numcode = :numcode"),
    @NamedQuery(name = "Geonames.findByLatitude", query = "SELECT g FROM Geonames g WHERE g.latitude = :latitude"),
    @NamedQuery(name = "Geonames.findByLongitude", query = "SELECT g FROM Geonames g WHERE g.longitude = :longitude")})
public class Geonames implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "COUNTRY")
    private String country;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "ALPHA2")
    private String alpha2;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "ALPHA3")
    private String alpha3;

    @Basic(optional = false)
    @NotNull
    @Column(name = "NUMCODE")
    private int numcode;

    @Basic(optional = false)
    @NotNull
    @Column(name = "LATITUDE")
    private double latitude;

    @Basic(optional = false)
    @NotNull
    @Column(name = "LONGITUDE")
    private double longitude;

    /**
     * Default public constructor needed for JPA
     */
    public Geonames() {
    }

    public Geonames(String country) {
        this.country = country;
    }

    public Geonames(String country, String alpha2, String alpha3, int numcode, double latitude, double longitude) {
        this.country = country;
        this.alpha2 = alpha2;
        this.alpha3 = alpha3;
        this.numcode = numcode;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAlpha2() {
        return alpha2;
    }

    public void setAlpha2(String alpha2) {
        this.alpha2 = alpha2;
    }

    public String getAlpha3() {
        return alpha3;
    }

    public void setAlpha3(String alpha3) {
        this.alpha3 = alpha3;
    }

    public int getNumcode() {
        return numcode;
    }

    public void setNumcode(int numcode) {
        this.numcode = numcode;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (country != null ? country.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Geonames)) {
            return false;
        }
        Geonames other = (Geonames) object;
        if ((this.country == null && other.country != null) || (this.country != null && !this.country.equals(other.country))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.adoptopenjdk.javacountdown.entity.Geonames[ country=" + country + " ]";
    }
    
}
