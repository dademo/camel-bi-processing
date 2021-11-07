package fr.dademo.bi.companies.repositories.file.validators.exception;

import javax.validation.constraints.NotNull;

public class UnwantedBeanException extends RuntimeException {

    private static final long serialVersionUID = -5117694036924611860L;

    public UnwantedBeanException(@NotNull Class<?> clazz) {
        super(String.format("Unexpected bean of class `%s` received", clazz.getCanonicalName()));
    }

    public UnwantedBeanException(@NotNull String beanName) {
        super(String.format("Unexpected bean named `%s` received", beanName));
    }
}
