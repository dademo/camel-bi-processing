/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.job.persistence_service.consumer;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author dademo
 */
@SuppressWarnings("deprecation")
public interface SupervisionBinding {

    String INPUT = "supervision";

    @Input(SupervisionBinding.INPUT)
    SubscribableChannel inputChannel();
}
