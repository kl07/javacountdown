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

import com.google.code.morphia.Key;
import org.adoptopenjdk.javacountdown.control.DataAccessObject.Type;
import org.adoptopenjdk.javacountdown.entity.AdoptionReportCountry;
import org.adoptopenjdk.javacountdown.entity.Visit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Asynchronous;
import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;
import javax.inject.Inject;

/**
 * Observes events fired by the VisitDAO
 * 
 * @author Alex Theedom
 */
@Asynchronous
public class VisitDataEventObserver {

    private static final Logger logger = LoggerFactory.getLogger(VisitDataEventObserver.class);

    @Inject
    @DataAccessObject(Type.REPORT)
    AdoptionReportDAO adoptionReportDAO;

    /**
     * If the Visit object has been persisted successfully we can update the
     * adoption report data.
     * 
     * @param visit
     */
    public void onSuccess(@Observes(during = TransactionPhase.AFTER_SUCCESS) Visit visit) {

        logger.debug("Observed Visit event for {}", visit);

        AdoptionReportCountry adoptionReportCountry = adoptionReportDAO.getCountryTotals(visit.getCountry());
        if (adoptionReportCountry == null) {
            adoptionReportCountry = new AdoptionReportCountry(visit);
        }
        adoptionReportCountry.updateTotals(visit);
        Key<AdoptionReportCountry> key = adoptionReportDAO.save(adoptionReportCountry);

        logger.debug("Updated adoption, persisted key {}", key);
    }

    /**
     * If there is a failure in persisting the Visit object we log it and don't
     * update the adoption report data.
     * 
     * @param visit
     */
    public void onFailure(@Observes(during = TransactionPhase.AFTER_FAILURE) Visit visit) {
        logger.error("Observed failed visit event for {}", visit);
    }

}
