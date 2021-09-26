package fr.dademo.bi.companies.tools.batch;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jeasy.batch.core.writer.RecordWriter;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

@RequiredArgsConstructor
public abstract class JpaRecordWriterSupport<T> implements RecordWriter<T> {

    @Getter
    private EntityManager localEntityManager;

    @Getter
    private EntityTransaction localEntityManagerTransaction;

    protected abstract EntityManagerFactory getEntityManagerFactory();

    @Override
    public synchronized void open() {

        var entityManagerFactory = getEntityManagerFactory();
        localEntityManager = entityManagerFactory.createEntityManager(entityManagerFactory.getProperties());
        localEntityManagerTransaction = localEntityManager.getTransaction();
    }

    @Override
    public synchronized void close() {

        entityManagerFlush();
        if (localEntityManager.isOpen()) {
            localEntityManager.close();
        }
    }

    protected synchronized void entityManagerFlush() {

        if (!localEntityManagerTransaction.isActive()) {
            localEntityManagerTransaction.begin();
        }
        localEntityManager.flush();
        localEntityManagerTransaction.commit();
        localEntityManager.clear();
    }
}
