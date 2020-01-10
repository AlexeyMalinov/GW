package ru.alexeymalinov.taskautomation.core.services;

import java.util.function.BiFunction;

public interface Operation<T> extends BiFunction<T,String,String> {
    String getName();
}
