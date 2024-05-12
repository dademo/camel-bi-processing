/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.data.helpers.data_gouv_fr.helpers;

import fr.dademo.data.definitions.data_gouv_fr.dimensions.DataGouvFrDataSetResource;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author dademo
 */
@SuppressWarnings("unused")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DataGouvFrFilterHelpers {

    public static Predicate<DataGouvFrDataSetResource> fieldStartingWith(Function<? super DataGouvFrDataSetResource, String> getter, String prefix) {
        return dataGouvFrDataSetResource -> getter.apply(dataGouvFrDataSetResource).startsWith(prefix);
    }

    public static Predicate<DataGouvFrDataSetResource> fieldEndingWith(Function<? super DataGouvFrDataSetResource, String> getter, String suffix) {
        return dataGouvFrDataSetResource -> getter.apply(dataGouvFrDataSetResource).endsWith(suffix);
    }

    public static Predicate<DataGouvFrDataSetResource> fieldContaining(Function<? super DataGouvFrDataSetResource, String> getter, String s) {
        return dataGouvFrDataSetResource -> getter.apply(dataGouvFrDataSetResource).contains(s);
    }

    public static Predicate<DataGouvFrDataSetResource> fieldMatching(Function<? super DataGouvFrDataSetResource, String> getter, String regex) {
        return dataGouvFrDataSetResource -> getter.apply(dataGouvFrDataSetResource).matches(regex);
    }

    public static Predicate<DataGouvFrDataSetResource> fieldEquals(Function<? super DataGouvFrDataSetResource, String> getter, String value) {
        return dataGouvFrDataSetResource -> getter.apply(dataGouvFrDataSetResource).equals(value);
    }
}
