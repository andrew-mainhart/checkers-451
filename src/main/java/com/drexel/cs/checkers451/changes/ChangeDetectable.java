package com.drexel.cs.checkers451.changes;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class ChangeDetectable {

    private final List<ChangeHandler> changeHandlers = new ArrayList<>();
    protected final AtomicInteger version = new AtomicInteger(0);

    public void blockForChange(int currentVersion) throws InterruptedException {
        while(version.get() == currentVersion){
            Thread.sleep(2);
        }
    }

    public void callbackForChange(ChangeHandler handler) {
        synchronized (this.changeHandlers) {
            this.changeHandlers.add(handler);
        }
    }

    public int getVersion() {
        return version.get();
    }

    protected synchronized void triggerChange() {
        synchronized (version) {
            synchronized (changeHandlers) {
                int version = this.version.incrementAndGet();
                for (ChangeHandler handler : this.changeHandlers) {
                    handler.onChange(version, this);
                }
                this.notifyAll();
            }
        }
    }
}
