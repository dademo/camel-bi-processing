/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.data.helpers.data_gouv_fr.http.validators;

import fr.dademo.data.definitions.data_gouv_fr.dimensions.DataGouvFrDataSetResource;
import fr.dademo.data.definitions.data_gouv_fr.dimensions.DataGouvFrDataSetResourceChecksum;
import fr.dademo.reader.http.data_model.HttpInputStreamIdentifier;
import fr.dademo.reader.http.validators.HashValidator;
import fr.dademo.tools.tools.HashTools;
import jakarta.annotation.Nonnull;

import java.util.Optional;

/**
 * @author dademo
 */
public class DataGouvFrInputStreamValidator extends HashValidator<HttpInputStreamIdentifier> {

    private DataGouvFrInputStreamValidator(@Nonnull DataGouvFrDataSetResourceChecksum dataGouvFrDataSetResourceChecksum) {
        super(
            HashTools.getHashComputerForAlgorithm(dataGouvFrDataSetResourceChecksum.getType()),
            dataGouvFrDataSetResourceChecksum.getValue()
        );
    }

    public static DataGouvFrInputStreamValidator of(@Nonnull DataGouvFrDataSetResourceChecksum dataGouvFrDataSetResourceChecksum) {
        return new DataGouvFrInputStreamValidator(dataGouvFrDataSetResourceChecksum);
    }

    public static Optional<DataGouvFrInputStreamValidator> of(@Nonnull DataGouvFrDataSetResource dataGouvFrDataSetResource) {
        return Optional.ofNullable(dataGouvFrDataSetResource.getChecksum())
            .map(DataGouvFrInputStreamValidator::of);
    }
}
