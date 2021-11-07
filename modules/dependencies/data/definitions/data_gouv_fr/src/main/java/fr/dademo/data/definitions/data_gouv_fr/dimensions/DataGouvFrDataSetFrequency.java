package fr.dademo.data.definitions.data_gouv_fr.dimensions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
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
}
