package com.enzo.flkit.services;

import java.util.Iterator;
import java.util.ServiceLoader;

public final class Services {

    public static <T> T load(Class<T> tClass) throws ServiceNotFoundException {
        Iterator<T> iterator = ServiceLoader.load(tClass).iterator();
        if (iterator.hasNext()) {
            return iterator.next();
        } else {
            throw new ServiceNotFoundException();
        }
    }
}
