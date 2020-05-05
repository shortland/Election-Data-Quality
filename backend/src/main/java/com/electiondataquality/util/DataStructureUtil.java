package com.electiondataquality.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DataStructureUtil {
    public static <E> Set<E> listToHashSet(List<E> list) {
        Set<E> t = new HashSet<>();

        for (E l : list) {
            t.add(l);
        }

        return t;
    }
}
