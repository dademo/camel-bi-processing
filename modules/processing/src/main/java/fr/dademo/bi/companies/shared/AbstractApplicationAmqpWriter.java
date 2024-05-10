/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.shared;

import jakarta.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;

public abstract class AbstractApplicationAmqpWriter extends BaseJobWriter {

    @Getter(AccessLevel.PROTECTED)
    private final AmqpTemplate amqpTemplate;

    private String cachedExchangeName;

    protected AbstractApplicationAmqpWriter(@Nonnull AmqpTemplate amqpTemplate) {

        this.amqpTemplate = amqpTemplate;
        cachedExchangeName = null;
    }

    @Nonnull
    protected abstract String getExchangeName();

    protected void sendMessage(Message associationMessage) {
        amqpTemplate.send(getFinalExchangeName(), "*", associationMessage);
    }

    private String getFinalExchangeName() {

        if (cachedExchangeName == null) {
            cachedExchangeName = getExchangeName();
        }
        return cachedExchangeName;
    }
}
