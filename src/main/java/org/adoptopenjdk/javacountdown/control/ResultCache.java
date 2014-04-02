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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.ejb.Startup;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.ScheduleExpression;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;


/**
 * Caches the data use to generate the world map of JDK adoption.
 * 
 * @author Markus Eisele <markus at eisele.net>
 * @author Alex Theedom
 */
@Startup
@Singleton
public class ResultCache {

    private static final Logger logger = LoggerFactory.getLogger(ResultCache.class);

    String json = "";

    @Resource
    TimerService timerService;
    Timer timer;
    
    @Inject
    private DataProvider dataProvider;

    @PostConstruct
    public void setTimer() {
        timer = timerService.createCalendarTimer(new ScheduleExpression().hour("00"));
    }
    
    // TODO Investigate use of timer here.
    @Timeout
    public void timeout(Timer timer) {
        json = dataProvider.getJdkAdoptionReport();
        logger.debug("Rebuilt JDK adoption cache");
    }
    
    public String getCountryData() {
        if (json.isEmpty()) {
            json = dataProvider.getJdkAdoptionReport();
        }
        return json;
    }
}
