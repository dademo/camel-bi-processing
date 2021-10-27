package fr.dademo.bi.companies.services.datamodel;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.annotation.Nullable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GouvFrDataSetResourceDefinition {

    @Nullable
    @JsonAlias("title")
    private String title;

    @Nullable
    @JsonAlias("type")
    private String type;

    @Nullable
    @JsonAlias("url")
    private String url;

    @Nullable
    @JsonAlias("description")
    private String description;

    @Nullable
    @JsonAlias("filesize")
    private Long fileSize;

    @Nullable
    @JsonAlias("filetype")
    private String fileType;

    @Nullable
    @JsonAlias("format")
    private String format;

    @Nullable
    @JsonAlias("mime")
    private String mime;

    @Nullable
    @JsonAlias("checksum")
    private GouvFrDataSetResourceChecksumDefinition checksum;

    @JsonAlias("created_at")
    private String createdAt;

    @JsonAlias("published")
    private String published;
}
