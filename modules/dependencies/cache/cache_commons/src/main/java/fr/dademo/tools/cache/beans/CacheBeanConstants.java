/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.tools.cache.beans;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CacheBeanConstants {

    public static final String DEFAULT_TEMP_BUCKET_NAME = "dev-temp";
    public static final String MINIO_STREAM_THREAD_POOL_BEAN = "minioStreamThreadPool";
    public static final int MINIO_STREAM_THREAD_POOL_SIZE = 10;
    public static final String MINIO_STREAM_THREADS_NAME_FORMAT = "minio-stream-%d";

}
