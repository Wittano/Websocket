package com.wittano.websocket.interfaces;

/**
 * General mapper for every objects
 *
 * @param <T> Type of object, which will be given in map function
 * @param <K> Type of object, which will be returned
 */
public interface Mapper<T, K> {
    /**
     * Map object from T type to K type
     *
     * @throws Throwable throw, when something went wrong, during object mapping
     */
    K map(T data) throws Throwable;
}
