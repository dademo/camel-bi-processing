/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.tools.liquibase.filter;

import liquibase.changelog.IncludeAllFilter;

import java.util.Objects;

/**
 * @author dademo
 */
public class NonRootFileFilter implements IncludeAllFilter {

    @Override
    public boolean include(String changeLogPath) {
        return Objects.nonNull(changeLogPath) && !changeLogPath.endsWith(".root.xml");
    }
}
