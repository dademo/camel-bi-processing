package fr.dademo.data.definitions.data_gouv_fr.dimensions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nonnull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataGouvFrDataSetTemporalCoverage {

    @Nonnull
    private LocalDate start;

    @Nonnull
    private LocalDate end;
}
