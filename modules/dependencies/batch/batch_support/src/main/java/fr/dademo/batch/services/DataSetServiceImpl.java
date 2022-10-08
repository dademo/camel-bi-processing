/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.services;

import fr.dademo.batch.beans.BeanValues;
import fr.dademo.batch.beans.jdbc.tools.LiquibaseMigrationsSupplier;
import fr.dademo.batch.configuration.BatchConfiguration;
import fr.dademo.batch.configuration.BatchDataSourcesConfiguration;
import fr.dademo.batch.repository.DataSetRepository;
import fr.dademo.batch.repository.datamodel.DataSetEntity;
import fr.dademo.batch.services.dto.DataSetDto;
import fr.dademo.batch.services.exceptions.MissingDataSetException;
import fr.dademo.batch.services.mappers.DataSetMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.sql.DataSource;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static fr.dademo.batch.beans.BeanValues.BATCH_DATA_SOURCE_BEAN_NAME;
import static fr.dademo.batch.beans.BeanValues.BATCH_DATA_SOURCE_NAME;

@SuppressWarnings("unused")
@Slf4j
@Service
public class DataSetServiceImpl implements DataSetService {

    private final DataSetRepository dataSetRepository;

    public DataSetServiceImpl(@Qualifier(BATCH_DATA_SOURCE_BEAN_NAME) DataSource dataSource,
                              BatchDataSourcesConfiguration batchDataSourcesConfiguration,
                              ResourceLoader resourceLoader,
                              DataSetRepository dataSetRepository) {
        performMigration(dataSource, batchDataSourcesConfiguration, resourceLoader);
        this.dataSetRepository = dataSetRepository;
    }

    @Override
    public Optional<DataSetDto> findById(@Nonnull @NotBlank String id) {
        return dataSetRepository.findById(id).map(this::mapToDto);
    }

    @Override
    public List<DataSetDto> getDataSetByName(@Nonnull @NotBlank String name) {

        return dataSetRepository.findByName(name).stream()
            .map(this::mapToDto)
            .collect(Collectors.toList());
    }

    @Override
    public DataSetDto createDataSet(@Nonnull String name, @Nonnull @NotBlank String source, @Nonnull @NotBlank String sourceSub) {

        var dataSetEntity = DataSetEntity.builder()
            .id(null)
            .name(name)
            .parent(null)
            .source(source)
            .sourceSub(sourceSub)
            .state(DataSetEntity.DataSetState.RUNNING)
            .timestamp(LocalDateTime.now())
            .build();

        dataSetEntity = dataSetRepository.save(dataSetEntity);

        return mapToDto(dataSetEntity);
    }

    @Override
    public DataSetDto createDataSet(@Nonnull String name, @Nonnull String parentId) {

        if (!dataSetRepository.existsById(parentId)) {
            throw MissingDataSetException.byName(name);
        }

        var dataSetEntity = DataSetEntity.builder()
            .id(null)
            .name(name)
            .parent(parentId)
            .source(null)
            .sourceSub(null)
            .state(DataSetEntity.DataSetState.RUNNING)
            .timestamp(LocalDateTime.now())
            .build();

        dataSetEntity = dataSetRepository.save(dataSetEntity);

        return mapToDto(dataSetEntity);
    }

    @Override
    public DataSetDto updateDataSet(@Nonnull DataSetDto dataSetDto) {

        DataSetEntity entity;
        if (Objects.nonNull(dataSetDto.getParent())) {
            entity = dataSetRepository.findFirstByNameAndParentOrderByTimestampDesc(
                dataSetDto.getName(),
                dataSetDto.getParent()
            ).orElseThrow(() -> MissingDataSetException.byNameWithParentId(
                dataSetDto.getName(),
                dataSetDto.getParent()
            ));
        } else {
            entity = dataSetRepository.findFirstByNameAndSourceAndSourceSubOrderByTimestampDesc(
                dataSetDto.getName(),
                dataSetDto.getSource(),
                dataSetDto.getSourceSub()
            ).orElseThrow(() -> MissingDataSetException.byNameWithSourceAndSourceSub(
                dataSetDto.getName(),
                dataSetDto.getSource(),
                dataSetDto.getSourceSub()
            ));
        }

        entity.setState(DataSetEntity.DataSetState.valueOf(dataSetDto.getState().toString()));
        entity.setTimestamp(LocalDateTime.now());

        return mapToDto(dataSetRepository.save(entity));
    }

    @Override
    public void deleteDataSet(@Nonnull DataSetDto dataSetDto) {
        dataSetRepository.delete(findDataSet(dataSetDto.getName()));
    }

    private DataSetEntity findDataSet(String name) {

        return dataSetRepository.findFirstByNameOrderByTimestampDesc(name)
            .orElseThrow(() -> MissingDataSetException.byName(name));
    }

    private DataSetDto mapToDto(DataSetEntity entity) {
        return DataSetMapper.INSTANCE.dataSetEntityToDataSetDto(entity);
    }

    private void performMigration(DataSource dataSource,
                                  BatchDataSourcesConfiguration batchDataSourcesConfiguration,
                                  ResourceLoader resourceLoader) {


        final var dataSourceConfiguration = batchDataSourcesConfiguration
            .getJDBCDataSourceConfigurationByName(BATCH_DATA_SOURCE_NAME);

        final var migrationsSupplier = LiquibaseMigrationsSupplier.builder()
            .migrationFolder(BeanValues.DATA_SET_MIGRATIONS_FOLDER_NAME)
            .changeLogFileName(BatchConfiguration.JobConfiguration.DEFAULT_CHANGELOG_FILE)
            .databaseCatalog(dataSourceConfiguration.getCatalog())
            .databaseSchema(dataSourceConfiguration.getSchema())
            .contexts(Collections.emptyList())
            .dataSource(dataSource)
            .resourceLoader(resourceLoader)
            .build();

        log.info("Performing migrations for {}", getClass().getName());
        migrationsSupplier.applyMigrations();
        log.info("Migrations performed");
    }
}
