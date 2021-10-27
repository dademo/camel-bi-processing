package fr.dademo.bi.companies.configuration.data_sources;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.Duration;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JDBCDataSourceConfiguration {

    @NotBlank
    private String url;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @Min(1)
    private int maxPoolSize = 10;

    @Min(1)
    private long connectionTimeoutMS = Duration.ofSeconds(30).toMillis();
}
