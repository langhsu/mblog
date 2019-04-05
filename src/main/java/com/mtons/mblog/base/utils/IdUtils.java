package com.mtons.mblog.base.utils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * id generate
 *
 * @author saxing 2019/4/5 9:15
 */
public class IdUtils {

    private final static AtomicInteger resource = ResourceLock.getAtomicInteger("ID_GENERATE");

    /**
     * 生成id
     *
     * @return
     */
    public static long getId(){
        synchronized (resource){
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return System.currentTimeMillis();
        }
    }

}
