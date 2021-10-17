package fr.dademo.bi.companies.repositories.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "batch_execution_property")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BatchExecutionPropertyEntity {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "property_name", nullable = false, length = 255)
    private String propertyName;

    @Column(name = "property_value", nullable = false, length = Integer.MAX_VALUE)
    private String propertyValue;
}
