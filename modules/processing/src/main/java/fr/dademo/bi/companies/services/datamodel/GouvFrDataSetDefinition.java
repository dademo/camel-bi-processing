package fr.dademo.bi.companies.services.datamodel;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.annotation.Nullable;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GouvFrDataSetDefinition {

    @Nullable
    @JsonAlias("description")
    private String description;

    @JsonAlias("title")
    private String title;

    @JsonAlias("slug")
    private String slug;

    @JsonAlias("uri")
    private String uri;

    @JsonAlias("page")
    private String page;

    @JsonAlias("resources")
    private List<GouvFrDataSetResourceDefinition> resources;
}
