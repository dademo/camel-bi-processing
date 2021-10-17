package fr.dademo.bi.companies.services.datamodel;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataSetResourceChecksumDefinition {

    @JsonAlias("type")
    private String type;

    @JsonAlias("value")
    private String value;
}
