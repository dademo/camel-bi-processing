/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.entities.database;

import lombok.*;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
@Table(name = "data_backend_database_connection")
public class DataBackendDatabaseConnectionEntity implements Serializable {

    private static final long serialVersionUID = -6016932834303708425L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_global_database")
    @ToString.Exclude
    private DataBackendGlobalDatabaseDescriptionEntity globalDatabase;

    @Nullable
    @Size(min = 1)
    @Column(name = "connection_state", updatable = false)
    private String connectionState;

    @Nullable
    @Min(1)
    @Column(name = "connection_pid", updatable = false)
    private Long connectionPID;

    @Nullable
    @Size(min = 1)
    @Column(name = "connected_database_name", updatable = false)
    private String connectedDatabaseName;

    @Nullable
    @Size(min = 1)
    @Column(name = "user_name", updatable = false)
    private String userName;

    @Nullable
    @Size(min = 1)
    @Column(name = "application_name", updatable = false)
    private String applicationName;

    @Nullable
    @Size(min = 1)
    @Column(name = "client_address", updatable = false)
    private String clientAddress;

    @Nullable
    @Size(min = 1)
    @Column(name = "client_hostname", updatable = false)
    private String clientHostname;

    @Nullable
    @Min(1)
    @Column(name = "client_port", updatable = false)
    private Long clientPort;

    @Nullable
    @Column(name = "connection_start", updatable = false)
    private Date connectionStart;

    @Nullable
    @Column(name = "transaction_start", updatable = false)
    private Date transactionStart;

    @Nullable
    @Column(name = "last_query_start", updatable = false)
    private Date lastQueryStart;

    @Nullable
    @Column(name = "last_state_change", updatable = false)
    private Date lastStateChange;

    @Nullable
    @Size(min = 1)
    @Column(name = "wait_event_type", updatable = false)
    private String waitEventType;

    @Nullable
    @Size(min = 1)
    @Column(name = "wait_event_name", updatable = false)
    private String waitEventName;

    @Nullable
    @Size(min = 1)
    @Column(name = "last_query", updatable = false)
    private String lastQuery;

    @Nullable
    @Size(min = 1)
    @Column(name = "backend_type_name", updatable = false)
    private String backendTypeName;
}
