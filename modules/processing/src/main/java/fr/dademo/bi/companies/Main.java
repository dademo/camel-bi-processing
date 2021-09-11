package fr.dademo.bi.companies;

import fr.dademo.bi.companies.components.camel.HttpComponent;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import org.apache.camel.CamelContext;
import org.apache.camel.quarkus.core.CamelRuntime;

import javax.inject.Inject;

@QuarkusMain
public class Main {

    public static void main(String... args) {
        Quarkus.run(MyApp.class, args);
    }

    public static class MyApp implements QuarkusApplication {

        // Tasks to run
        @Inject
        private CamelContext camelContext;

        @Inject
        private CamelRuntime camelRuntime;

        @Override
        public int run(String... args) {

            // https://developers.redhat.com/articles/2021/05/17/integrating-systems-apache-camel-and-quarkus-red-hat-openshift
            camelContext.addComponent(HttpComponent.COMPONENT_NAME, new HttpComponent(camelContext));

            camelRuntime.start(args);
            return camelRuntime.waitForExit();
        }
    }
}
