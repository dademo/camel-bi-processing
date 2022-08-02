/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.tools.cache.beans;

import fr.dademo.tools.cache.configuration.CacheVFSConfiguration;
import lombok.SneakyThrows;
import org.apache.commons.vfs2.*;
import org.apache.commons.vfs2.auth.StaticUserAuthenticator;
import org.apache.commons.vfs2.impl.DefaultFileSystemConfigBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.util.Optional;
import java.util.function.Function;

@Configuration
@ConditionalOnBean(CacheVFSEnabledConditional.class)
public class VFSBeans {

    @Bean
    @ConditionalOnMissingBean(UserAuthenticator.class)
    public UserAuthenticator userAuthenticator(CacheVFSConfiguration cacheVfsConfiguration) {

        final var authenticationInformation = Optional.ofNullable(cacheVfsConfiguration.getAuthentication());
        return new StaticUserAuthenticator(
            authenticationInformation
                .map(CacheVFSConfiguration.VFSAuthenticationConfiguration::getDomain)
                .orElse(null),
            authenticationInformation
                .map(CacheVFSConfiguration.VFSAuthenticationConfiguration::getUsername)
                .orElse(null),
            authenticationInformation
                .map(CacheVFSConfiguration.VFSAuthenticationConfiguration::getPassword)
                .orElse(null)
        );
    }

    @Bean
    @ConditionalOnMissingBean(FileSystemOptions.class)
    public FileSystemOptions fileSystemOptions(UserAuthenticator userAuthenticator) {

        final var fileSystemOptions = new FileSystemOptions();
        DefaultFileSystemConfigBuilder.getInstance().setUserAuthenticator(fileSystemOptions, userAuthenticator);
        return fileSystemOptions;
    }

    @Bean
    @ConditionalOnMissingBean(FileSystemManager.class)
    @SneakyThrows
    public FileSystemManager fileSystemManager(FileSystemOptions fileSystemOptions,
                                               CacheVFSConfiguration cacheVfsConfiguration) {
        return VFS.getManager();
    }

    @SneakyThrows
    private Function<URI, FileObject> fileResolverUsingFileSystemManager(FileSystemManager fileSystemManager) {

        return uri -> {
            try {
                return fileSystemManager.resolveFile(uri);
            } catch (IOException ex) {
                throw new UncheckedIOException(ex);
            }
        };
    }
}
