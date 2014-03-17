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
package org.adoptopenjdk.javacountdown.control;

import java.io.Serializable;
import java.util.Date;


/**
 * This class is a used to transfer data received via REST from the
 * visitor to the DAO. We use this DTO to avoid
 * the need to format data in the correct JSON format on the client side.
 * 
 * @author Alex Theedom
 * 
 */
public class VisitTransfer implements Serializable{
    
    private static final long serialVersionUID = 5128072717995441603L;
    private String version;
    private double latitude;
    private double longitude;
    private String browserName;
    private String browserVersion;
    private String os;

 
    public VisitTransfer() {}

    public void setVersion(String version) {
        this.version = version;
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

    public String getBrowserName() {
        return browserName;
    }

    public void setBrowserName(String browserName) {
        this.browserName = browserName;
    }
    
    public String getBrowserVersion() {
        return browserVersion;
    }

    public void setBrowserVersion(String browserVersion) {
        this.browserVersion = browserVersion;
    }
    
    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((browserName == null) ? 0 : browserName.hashCode());
        result = prime * result + ((browserVersion == null) ? 0 : browserVersion.hashCode());
        long temp;
        temp = Double.doubleToLongBits(latitude);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((os == null) ? 0 : os.hashCode());
        result = prime * result + ((version == null) ? 0 : version.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        VisitTransfer other = (VisitTransfer) obj;
        if (browserName == null) {
            if (other.browserName != null)
                return false;
        } else if (!browserName.equals(other.browserName))
            return false;
        if (browserVersion == null) {
            if (other.browserVersion != null)
                return false;
        } else if (!browserVersion.equals(other.browserVersion))
            return false;
        if (Double.doubleToLongBits(latitude) != Double.doubleToLongBits(other.latitude))
            return false;
        if (Double.doubleToLongBits(longitude) != Double.doubleToLongBits(other.longitude))
            return false;
        if (os == null) {
            if (other.os != null)
                return false;
        } else if (!os.equals(other.os))
            return false;
        if (version == null) {
            if (other.version != null)
                return false;
        } else if (!version.equals(other.version))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "VisitTransfer [version=" + version + ", latitude=" + latitude + ", longitude=" + longitude + ", browserName=" + browserName
                + ", browserVersion=" + browserVersion + ", os=" + os + "]";
    }

    



}
