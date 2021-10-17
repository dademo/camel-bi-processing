package fr.dademo.bi.companies.repositories.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "batch_execution_error")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BatchExecutionErrorEntity {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "error_name", nullable = false, length = 255)
    private String name;

    @Column(name = "error_error", nullable = false, length = Integer.MAX_VALUE)
    private String error;

    @Column(name = "error_localized_error", nullable = false, length = Integer.MAX_VALUE)
    private String localizedError;

    @Column(name = "error_stack_trace", nullable = false, length = Integer.MAX_VALUE)
    private String stackTrace;
}
