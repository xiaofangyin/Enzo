package com.enzo.flkit.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

public final class FLServiceLoader {

    public static <T> T load(Class<T> tClass) throws FLServiceNotFoundException {
        Iterator<T> iterator = ServiceLoader.load(tClass).iterator();
        if (iterator.hasNext()) {
            return iterator.next();
        } else {
            throw new FLServiceNotFoundException();
        }
    }

    public static <T> List<T> loadList(Class<T> tClass) throws FLServiceNotFoundException {
        Iterator<T> iterator = ServiceLoader.load(tClass).iterator();
        List<T> list = new ArrayList<>();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        return list;
    }
}
