package fr.dademo.bi.companies.repositories.entities;

import lombok.*;
import org.jeasy.batch.core.job.JobStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "batch_execution_history")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@With
public class BatchExecutionHistoryEntity {

    @Id
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "execution_name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "execution_status", nullable = false)
    private BatchStatus status;

    @Column(name = "execution_start", nullable = false)
    private LocalDateTime start;

    @Column(name = "execution_end", nullable = false)
    private LocalDateTime end;

    @Column(name = "execution_read_count", nullable = false)
    private long readCount;

    @Column(name = "execution_write_count", nullable = false)
    private long writeCount;

    @Column(name = "execution_filter_count", nullable = false)
    private long filterCount;

    @Column(name = "execution_error_count", nullable = false)
    private long errorCount;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "batch_execution_id", nullable = false, updatable = false)
    @ToString.Exclude
    private List<BatchExecutionCustomMetricEntity> batchExecutionCustomMetrics;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "batch_execution_id", nullable = false, updatable = false)
    @ToString.Exclude
    private List<BatchExecutionPropertyEntity> executionProperties;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "batch_execution_error_id", updatable = false)
    @ToString.Exclude
    private BatchExecutionErrorEntity executionError;

    @Override
    public boolean equals(Object o) {
        return o instanceof BatchExecutionHistoryEntity && Objects.equals(((BatchExecutionHistoryEntity) o).id, id);
    }

    @Override
    public int hashCode() {
        return id.intValue();
    }

    @AllArgsConstructor
    public enum BatchStatus {
        STARTING("STARTING"),
        STARTED("STARTED"),
        STOPPING("STOPPING"),
        FAILED("FAILED"),
        COMPLETED("COMPLETED"),
        ABORTED("ABORTED");

        private final String status;

        public static BatchStatus of(JobStatus jobStatus) {
            return BatchStatus.valueOf(jobStatus.name());
        }

        @Override
        public String toString() {
            return status;
        }
    }
}
