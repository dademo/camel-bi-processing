package fr.dademo.bi.companies.process.stg.naf.entities;


import com.fasterxml.jackson.annotation.JsonAlias;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@RegisterForReflection
@Entity
@Table(name = "fact_naf_codes")
@Getter
@Setter
@NoArgsConstructor
public class NafDefinition {

    @NotNull
    @NotBlank
    @Id
    @Column(name = "naf_code", nullable = false, unique = true, length = 5)
    @JsonAlias("code_naf")
    private String nafCode;

    @Column(name = "title", length = Integer.MAX_VALUE)
    @JsonAlias("intitule_naf")
    private String title;

    @Column(name = "title_65", length = 65)
    @JsonAlias("intitule_naf_65")
    private String title65;

    @Column(name = "title_40", length = 40)
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
