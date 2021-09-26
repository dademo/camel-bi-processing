package fr.dademo.bi.companies.tools;

import java.util.List;

public class CastTools {

    @SuppressWarnings("unchecked")
    public static <T> List<T> insecureListCast(List<?> values) {
        return (List<T>) values;
    }
}
