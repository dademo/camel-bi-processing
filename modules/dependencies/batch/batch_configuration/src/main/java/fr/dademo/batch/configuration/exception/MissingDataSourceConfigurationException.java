/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.configuration.exception;

import java.util.function.Supplier;

/**
 * @author dademo
 */
public class MissingDataSourceConfigurationException extends RuntimeException {

    private static final long serialVersionUID = -3977011550884885808L;

    public MissingDataSourceConfigurationException(String dataSourceTypeDescription, String dataSourceName) {
        super(String.format("Missing `%s` datasource configuration for `%s`", dataSourceTypeDescription, dataSourceName));
    }

    public static Supplier<MissingDataSourceConfigurationException> ofDataSource(String dataSourceTypeDescription, String dataSourceName) {
        return () -> new MissingDataSourceConfigurationException(dataSourceTypeDescription, dataSourceName);
    }
}
