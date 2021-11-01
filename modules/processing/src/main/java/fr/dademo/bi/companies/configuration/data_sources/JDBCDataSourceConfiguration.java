package fr.dademo.bi.companies.configuration.data_sources;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.Duration;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JDBCDataSourceConfiguration {

    @NotBlank
    private String url;

    @Nullable
    private String username;

    @Nullable
    private String password;

    @Nullable
    private String driverClassName;

    @Min(0)
    private int minimumIdle = 0;

    @Min(1)
    private int maximumPoolSize = 10;

    @Min(1)
    private long connectionTimeoutMS = Duration.ofSeconds(30).toMillis();
}
