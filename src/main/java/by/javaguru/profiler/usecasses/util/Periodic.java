package by.javaguru.profiler.usecasses.util;

public interface Periodic<T> {

    T periodFrom();

    T periodTo();

    Boolean presentTime();
}
