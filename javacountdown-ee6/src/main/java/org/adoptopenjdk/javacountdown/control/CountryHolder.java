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

    private static final long serialVersionUID = -3259682699225919112L;
    private String country;
    private Integer count;

    public CountryHolder(String country) {
        this.country = country;
        this.count = Integer.valueOf(0);
    }

    public CountryHolder(String country, Long count) {
        this.country = country;

        if (count.longValue() > Integer.MAX_VALUE) {
            this.count = Integer.valueOf(0);
        } else {
            this.count = Integer.valueOf(count.intValue());
        }
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
