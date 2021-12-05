/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.reader.file.repository;

import fr.dademo.data.generic.stream_definitions.repository.DataStreamGetter;
import fr.dademo.reader.file.data_model.FileInputStreamIdentifier;

/**
 * @author dademo
 */
public interface FileDataQuerierRepository extends DataStreamGetter<FileInputStreamIdentifier> {

}
