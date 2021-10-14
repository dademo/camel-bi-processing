package fr.dademo.bi.companies.jobs.stg.naf.datamodel;


import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class NafDefinition {

    @NotNull
    @NotBlank
    @JsonAlias("code_naf")
    private String nafCode;

    @JsonAlias("intitule_naf")
    private String title;

    @JsonAlias("intitule_naf_65")
    private String title65;

    @JsonAlias("intitule_naf_40")
    private String title40;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NafDefinition that = (NafDefinition) o;
        return nafCode.equals(that.nafCode) && Objects.equals(title, that.title) && Objects.equals(title65, that.title65) && Objects.equals(title40, that.title40);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nafCode, title, title65, title40);
    }
}
