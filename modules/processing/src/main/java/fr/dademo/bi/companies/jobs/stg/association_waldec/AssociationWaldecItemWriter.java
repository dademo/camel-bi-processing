/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.association_waldec;

import fr.dademo.bi.companies.jobs.stg.association_waldec.datamodel.AssociationWaldec;
import org.springframework.batch.item.ItemWriter;

/**
 * @author dademo
 */
public interface AssociationWaldecItemWriter extends ItemWriter<AssociationWaldec> {
}
