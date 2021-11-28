/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.beans;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author dademo
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BeanValues {

    // DataSourcesFactory
    public static final String BATCH_DATASOURCE_NAME = "batch";
    public static final String STG_DATASOURCE_NAME = "stg";
}
