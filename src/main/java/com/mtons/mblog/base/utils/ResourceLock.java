package com.mtons.mblog.base.utils;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * resource lock
 *
 * @author saxing 2019/4/3 16:20
 */
public class ResourceLock {

    private static final ConcurrentHashMap<String, AtomicInteger> lockMap = new ConcurrentHashMap<String, AtomicInteger>();

    public static AtomicInteger getAtomicInteger(String key) {
        if (lockMap.get(key) == null) {
            lockMap.putIfAbsent(key, new AtomicInteger(0));
        }
        int count = lockMap.get(key).incrementAndGet();
        return lockMap.get(key);
    }

    public static void giveUpAtomicInteger(String key) {
        if (lockMap.get(key) != null) {
            int source = lockMap.get(key).decrementAndGet();
            if (source <= 0) {
                lockMap.remove(key);
            }
        }
    }

    public static String getPostKey(Long postId){
        return "POST_OPERATE_{postId}".replace("{postId}", String.valueOf(postId));
    }

    public static String getPicKey(Long picId){
        return "PIC_OPERATE_{pic}".replace("{pic}", String.valueOf(picId));
    }

}
