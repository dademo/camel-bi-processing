/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.association_waldec.datamodel;

import org.jooq.impl.CustomRecord;

import javax.annotation.Nonnull;

/**
 * @author dademo
 */
public class AssociationWaldecRecord extends CustomRecord<AssociationWaldecRecord> {

    private static final long serialVersionUID = -5979839326641508119L;

    public AssociationWaldecRecord(@Nonnull AssociationWaldecTable table) {
        super(table);
    }
}

