package fr.dademo.bi.companies;

public interface AppTask {

    default void run() {
        if (isEnabled()) {
            doRun();
        }
    }

    void doRun();

    default boolean isEnabled() {
        return true;
    }
}
