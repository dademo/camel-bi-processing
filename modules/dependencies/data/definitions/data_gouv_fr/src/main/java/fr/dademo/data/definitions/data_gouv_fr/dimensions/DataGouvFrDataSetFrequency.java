/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.data.definitions.data_gouv_fr.dimensions;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

/**
 * @author dademo
 */
@AllArgsConstructor
public enum DataGouvFrDataSetFrequency {
    UNKNOWN("unknown"),
    PUNCTUAL("punctual"),
    CONTINUOUS("continuous"),
    HOURLY("hourly"),
    FOUR_TIMES_A_DAY("fourTimesADay"),
    THREE_TIMES_A_DAY("threeTimesADay"),
    SEMI_DAILY("semiDaily"),
    DAILY("daily"),
    FOUR_TIMES_A_WEEK("fourTimesAWeek"),
    THREE_TIMES_A_WEEK("threeTimesAWeek"),
    SEMI_WEEKLY("semiWeekly"),
    WEEKLY("weekly"),
    MONTHLY("monthly"),
    BIMONTHLY("bimonthly"),
    QUARTERLY("quarterly"),
    THREE_TIMES_A_YEAR("threeTimesAYear"),
    SEMI_ANNUAL("semiAnnual"),
    ANNUAL("annual"),
    BIENNIAL("biennial"),
    TRIENNIAL("triennial"),
    QUINQUENNIAL("quinquennial"),
    IRREGULAR("irregular");

    private final String value;

    @JsonValue
    public String getValue() {
        return value;
    }
}
