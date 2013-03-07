/*
 * Copyright 2013 Adopt OpenJDK.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.adoptopenjdk.javacountdown.control;

import java.io.Serializable;

/**
 * A class to support type-safety with non-entity queries
 */
public class CountryHolder implements Serializable {

    private static final long serialVersionUID = 1L;
    private String country;
    private Integer cnt;

    public CountryHolder() {
    }

    /**
     * Constructor for use with DataProvider.getCountryFromLatLong
     * @param country
     */
    public CountryHolder(String country) {
        this.country = country;
        this.cnt = 0;
    }

    /**
     * Constructor for use with DataProvider.getCountries
     * @param country
     * @param cnt
     */
    public CountryHolder(String country, Long cnt) {
        this.country = country;

        if (cnt > (long) Integer.MAX_VALUE) {
            // x is too big to convert
            this.cnt = 0;
        } else {
            this.cnt = cnt.intValue();
        }
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }
}
