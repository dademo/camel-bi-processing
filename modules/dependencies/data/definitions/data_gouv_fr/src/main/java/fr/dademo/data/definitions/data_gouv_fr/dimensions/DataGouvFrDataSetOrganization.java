package fr.dademo.data.definitions.data_gouv_fr.dimensions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataGouvFrDataSetOrganization {

    @Nullable
    private String id;

    @Nullable
    @JsonProperty("class")
    private String clazz;

    @Nullable
    private String acronym;

    @Nullable
    private List<DataGouvFrDataSetBadge> badges;

    @Data
    public static class DataGouvFrDataSetBadge {

        private String kind;
    }
}
