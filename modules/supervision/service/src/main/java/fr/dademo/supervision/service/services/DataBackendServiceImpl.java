/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.services;

import fr.dademo.supervision.service.repository.ExtendedDataBackendDescriptionRepository;
import fr.dademo.supervision.service.services.dto.DataBackendDescriptionDto;
import fr.dademo.supervision.service.services.mappers.DataBackendDescriptionEntityToDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.Optional;

/**
 * @author dademo
 */
@Service
public class DataBackendServiceImpl implements DataBackendService {

    @Autowired
    private ExtendedDataBackendDescriptionRepository repository;

    @Override
    public Page<DataBackendDescriptionDto> findDataBackends(@Nonnull Pageable pageable) {

        return repository.findDescriptionWithLinks(pageable)
            .map(DataBackendDescriptionEntityToDtoMapper.INSTANCE::viewToDto);
    }

    @Override
    public Optional<DataBackendDescriptionDto> findDataBackendById(@Nonnull Long id) {

        return repository.findOneDescriptionWithLinks(id)
            .map(DataBackendDescriptionEntityToDtoMapper.INSTANCE::viewToDto);
    }
}
