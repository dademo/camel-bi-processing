/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.controllers.hal;

import java.util.Date;

/**
 * @author dademo
 */
public abstract class AbstractDataBackendStatisticsRepresentationModelAssembler<T> implements AppStatisticsEntityRepresentationModelAssembler<T> {

    protected abstract Long getParentId();

    protected abstract Date getFrom();

    protected abstract Date getTo();
}
