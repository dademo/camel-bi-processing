/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.naf.datamodel;


import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * @author dademo
 */
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
