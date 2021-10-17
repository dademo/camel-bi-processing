package fr.dademo.bi.companies.repositories.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "batch_execution_custom_metric")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BatchExecutionCustomMetricEntity {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "metric_name", nullable = false, length = 255)
    private String name;

    @Column(name = "metric_value", nullable = true, length = Integer.MAX_VALUE)
    private String value;
}
