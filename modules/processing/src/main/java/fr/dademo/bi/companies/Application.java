package fr.dademo.bi.companies;

import fr.dademo.bi.companies.services.AppJobLauncher;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration;

@SpringBootApplication(exclude = {
        R2dbcAutoConfiguration.class,
        DataSourceAutoConfiguration.class,
})
@EnableBatchProcessing
public class Application implements CommandLineRunner {

    @Autowired
    private AppJobLauncher appJobLauncher;

    public static void main(String[] args) {
        System.exit(SpringApplication.exit(SpringApplication.run(Application.class, args)));
    }

    @Override
    public void run(String... args) {
        appJobLauncher.runAll();
    }
}