/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.beans.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

/**
 * @author dademo
 */
@SuppressWarnings("unused")
@Slf4j
@Configuration
public class ApplicationEvents {

    @EventListener(ApplicationStartedEvent.class)
    public void onApplicationStarted() {
        log.debug("Application started");
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationStopped() {
        log.debug("Application ready");
    }
}
