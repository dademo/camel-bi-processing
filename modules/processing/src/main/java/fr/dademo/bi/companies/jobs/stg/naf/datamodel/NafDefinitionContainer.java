package fr.dademo.bi.companies.jobs.stg.naf.datamodel;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
