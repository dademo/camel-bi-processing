/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.naf.datamodel;

import org.jooq.impl.CustomRecord;

import javax.annotation.Nonnull;

/**
 * @author dademo
 */
@SuppressWarnings("java:S110")
public class NafDefinitionRecord extends CustomRecord<NafDefinitionRecord> {

    private static final long serialVersionUID = -3661300836662672014L;

    public NafDefinitionRecord(@Nonnull NafDefinitionTable table) {
        super(table);
    }
}
