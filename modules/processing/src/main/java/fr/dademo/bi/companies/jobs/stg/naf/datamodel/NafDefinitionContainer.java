/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.naf.datamodel;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author dademo
 */
@Getter
@Setter
@NoArgsConstructor
public class NafDefinitionContainer {

    @JsonAlias("datasetid")
    private String dataSetId;

    @JsonAlias("recordid")
    private String recordId;

    @JsonAlias("fields")
    private NafDefinition fields;

    @JsonAlias("record_timestamp")
    private String recordTimestamp;
}
