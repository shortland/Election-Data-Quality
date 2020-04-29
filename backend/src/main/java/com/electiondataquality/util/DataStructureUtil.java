package com.electiondataquality.util;

import java.util.HashSet;
import java.util.List;

public class DataStructureUtil {
    public static <E> HashSet<E> listToHashSet(List<E> list) {
        HashSet<E> t = new HashSet<>();

        for (E l : list) {
            t.add(l);
        }

        return t;
    }
}
