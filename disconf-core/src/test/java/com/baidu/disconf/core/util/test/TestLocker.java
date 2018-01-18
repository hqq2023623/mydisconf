package com.baidu.disconf.core.util.test;

import com.disconf.core.common.lock.Locker;
import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lzj
 * @date 2018/1/18
 */
public class TestLocker {

    private Locker locker = new Locker();

    private final int threadCount = 1000;

    private Lock lock = new ReentrantLock();

    //sync比lock快，因为没有CAS的空转
    @Test
    public void testLock() throws Exception {
        System.out.println("lock");
        long before = System.currentTimeMillis();
        for (int i = 0; i < threadCount; i++) {
            new Thread(new AddThread(i)).start();
        }
        System.out.println("lock cost : " + (System.currentTimeMillis() - before));
    }

    @Test
    public void testSync() throws Exception {
        System.out.println("sync");
        long before = System.currentTimeMillis();
        for (int i = 0; i < threadCount; i++) {
            new Thread(new syncThread(i)).start();
        }
        System.out.println("sync cost : " + (System.currentTimeMillis() - before));
    }

    class syncThread implements Runnable {

        private int i;

        public syncThread(int i) {
            this.i = i;
        }

        @Override
        public synchronized void run() {
            System.out.println(i);
            try {
                TimeUnit.MILLISECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    class AddThread implements Runnable {

        private int i;

        public AddThread(int i) {
            this.i = i;
        }

        @Override
        public void run() {
//            try (Locker.Lock lock = locker.lock()) {
////                TimeUnit.MILLISECONDS.sleep(1);
//                System.out.println(i);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

            try {
                lock.lock();
                TimeUnit.MILLISECONDS.sleep(1);
                System.out.println(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }



        }

    }

}
