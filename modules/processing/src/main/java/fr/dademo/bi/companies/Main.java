package fr.dademo.bi.companies;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;
import org.apache.camel.quarkus.main.CamelMainApplication;

@QuarkusMain
public class Main {

    public static void main(String... args) {
        //Quarkus.run(MyApp.class, args);
        Quarkus.run(CamelMainApplication.class, args);
    }

//    public static class MyApp implements QuarkusApplication {
//
//        // Tasks to run
//        @Inject
//        private APETask apeTask;
//
//        @Override
//        public int run(String... args) throws Exception {
//            //apeTask.run();
//            return 0;
//        }
//    }
}
