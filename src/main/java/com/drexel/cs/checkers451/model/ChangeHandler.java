package com.drexel.cs.checkers451.model;

public interface ChangeHandler<T extends ChangeDetectable> {
    public void onChange(int version, T object);
}
