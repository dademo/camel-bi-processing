package fr.dademo.bi.companies.jobs.stg.naf.entities;

import com.fasterxml.jackson.annotation.JsonAlias;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@RegisterForReflection
@NoArgsConstructor
@Getter
@Setter
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
