/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.helpers;

import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

public class JobTaskExecutorWrapper implements TaskExecutor {

    @Getter
    private final ThreadPoolTaskExecutor delegate;

    private final List<Future<?>> tasks;

    public JobTaskExecutorWrapper(@Nonnull final ThreadPoolTaskExecutor delegate) {

        this.delegate = delegate;
        tasks = new ArrayList<>();
    }

    @Override
    public void execute(@Nonnull Runnable task) {
        tasks.add(delegate.submit(task));
    }

    public void shutdown() {
        delegate.shutdown();
    }

    public void waitAll() {
        tasks.forEach(this::getFuture);
    }

    @SneakyThrows
    private void getFuture(Future<?> future) {
        future.get();
    }
}
