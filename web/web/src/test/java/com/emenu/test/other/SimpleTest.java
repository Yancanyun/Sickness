package com.emenu.test.other;

import com.pandawork.core.common.cache.Cache;
import com.pandawork.core.common.cache.decorators.LRUCache;
import com.pandawork.core.common.cache.decorators.SynchronizedCache;
import com.pandawork.core.common.cache.impl.LocalCache;

import java.util.ArrayList;
import java.util.List;

/**
 * SimpleTest
 *
 * @author: zhangteng
 * @time: 2015/8/26 8:46
 **/
public class SimpleTest {

    public static void main(String[] args) {
        Cache lruCache = new LRUCache(
                new SynchronizedCache(
                        new LocalCache()
                ),
                100
        );

        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < 10; ++i) {
            list.add(i);
        }
        System.out.println(list.subList(0, 3));
        System.out.println(list.subList(3, 7));
        System.out.println(list.subList(7, 10));
    }
}
