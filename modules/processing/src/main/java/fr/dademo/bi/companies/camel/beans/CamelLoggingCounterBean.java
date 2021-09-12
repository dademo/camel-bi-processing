package fr.dademo.bi.companies.camel.beans;

import io.quarkus.runtime.annotations.RegisterForReflection;

import javax.annotation.Nonnull;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import static fr.dademo.bi.companies.camel.beans.CamelLoggingCounterBean.BEAN_NAME;

@ApplicationScoped
@Named(BEAN_NAME)
@RegisterForReflection
public class CamelLoggingCounterBean {

    public static final String BEAN_NAME = "camelLoggingCounterBean";

    private final Map<String, AtomicLong> loggingCounter;

    public CamelLoggingCounterBean() {
        loggingCounter = new HashMap<>();
    }

    public long get(@Nonnull String key) {

        return loggingCounter
                .getOrDefault(key, new AtomicLong(0))
                .longValue();
    }

    public long next(@Nonnull String key,
                     long add) {

        var longValue = loggingCounter.getOrDefault(key, new AtomicLong(0));
        var result = longValue.addAndGet(add);
        loggingCounter.putIfAbsent(key, longValue);
        return result;
    }
}
