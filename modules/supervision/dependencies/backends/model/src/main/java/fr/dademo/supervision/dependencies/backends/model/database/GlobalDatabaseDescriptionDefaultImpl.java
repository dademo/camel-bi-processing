/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.dependencies.backends.model.database;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fr.dademo.supervision.dependencies.backends.model.database.resources.DatabaseConnection;
import fr.dademo.supervision.dependencies.backends.model.database.resources.DatabaseConnectionDefaultImpl;
import fr.dademo.supervision.dependencies.backends.model.database.resources.DatabaseReplicationPeer;
import fr.dademo.supervision.dependencies.backends.model.database.resources.DatabaseReplicationPeerDefaultImpl;
import fr.dademo.supervision.dependencies.backends.model.shared.DataBackendDescriptionDefaultImpl;
import fr.dademo.supervision.dependencies.backends.model.shared.DataBackendKind;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * @author dademo
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class GlobalDatabaseDescriptionDefaultImpl extends DataBackendDescriptionDefaultImpl implements GlobalDatabaseDescription {

    @Nonnull
    @JsonDeserialize(as = List.class, contentAs = DatabaseConnectionDefaultImpl.class)
    private Iterable<DatabaseConnection> databaseConnections;

    @Nonnull
    @JsonDeserialize(as = List.class, contentAs = DatabaseDescriptionDefaultImpl.class)
    private Iterable<DatabaseDescription> databasesDescriptions;

    @Nonnull
    @JsonDeserialize(as = List.class, contentAs = DatabaseReplicationPeerDefaultImpl.class)
    private Iterable<DatabaseReplicationPeer> databaseReplicationPeers;

    @JsonIgnore
    @Nonnull
    @Override
    public final DataBackendKind getBackendKind() {
        return DataBackendKind.DATABASE;
    }
}
