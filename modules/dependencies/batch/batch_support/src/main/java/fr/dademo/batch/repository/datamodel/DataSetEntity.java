/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.repository.datamodel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.util.Strings;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.LocalDateTime;
import java.util.Objects;

@Builder
@Getter
@Setter
@AllArgsConstructor
@DataSetEntity.DataSetParentConstraint
public class DataSetEntity {

    @Nullable
    private String id;

    @Nonnull
    private String name;

    @Nullable
    private String parent;

    @Nullable
    private String source;

    @Nullable
    private String sourceSub;

    @Nonnull
    private DataSetState state;

    @Nonnull
    private LocalDateTime timestamp;


    public enum DataSetState {
        READY,
        FAILED,
        RUNNING
    }

    @SuppressWarnings("unused")
    @Constraint(validatedBy = DataSetParentConstraintValidator.class)
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface DataSetParentConstraint {

        String message() default "{error.parent}";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};
    }

    public static class DataSetParentConstraintValidator implements ConstraintValidator<DataSetParentConstraint, DataSetEntity> {

        @Override
        public void initialize(DataSetParentConstraint constraintAnnotation) {
            ConstraintValidator.super.initialize(constraintAnnotation);
        }

        @SuppressWarnings("all")
        @Override
        public boolean isValid(DataSetEntity value, ConstraintValidatorContext context) {

            if (!(value instanceof DataSetEntity)) {
                throw new IllegalArgumentException(String.format(
                    "@%s only applies to objects of type %s",
                    DataSetParentConstraint.class.getName(),
                    DataSetEntity.class.getName()
                ));
            }

            return Objects.nonNull(value.getParent()) ||
                (Strings.isNotBlank(value.getSource()) && Strings.isNotBlank(value.getSourceSub()));
        }
    }
}
