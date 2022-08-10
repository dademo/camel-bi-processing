/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.exceptions;

public class MissingBeanException extends RuntimeException {

    private static final long serialVersionUID = -6599374744806531718L;

    public MissingBeanException(Class<?> beanClass) {
        super(String.format("Missing bean of class %s", beanClass.getName()));
    }

    public MissingBeanException(Class<?> beanClass, String beanName) {
        super(String.format("Missing bean of class %s named %d", beanClass.getName(), beanName));
    }
}
