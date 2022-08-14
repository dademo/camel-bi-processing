/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.association.datamodel;

import org.jooq.impl.CustomRecord;

import javax.annotation.Nonnull;

/**
 * @author dademo
 */
public class AssociationRecord extends CustomRecord<AssociationRecord> {

    private static final long serialVersionUID = 3007869680725243588L;

    public AssociationRecord(@Nonnull AssociationTable table) {
        super(table);
    }
}

