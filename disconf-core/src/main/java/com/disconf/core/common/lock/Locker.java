package com.disconf.core.common.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * try-with style lock , from jetty
 * @author lzj
 * @date 2017/12/6
 */
public class Locker {

    //表示为非lock的状态
    private static final Lock LOCKED = new Lock();
    //主要实现类
    private ReentrantLock _lock = new ReentrantLock();
    //表示为已lock状态，需要解锁
    private Lock _unlock = new Lock();

    public Lock lock() {
        if (!_lock.isHeldByCurrentThread()) {
            _lock.lock();
        }
        return _unlock;
    }

    public boolean isLocked() {
        return _lock.isLocked();
    }

    public Lock lockIfNotHeld() {
        if (_lock.isHeldByCurrentThread()) {
            return LOCKED;
        }
        _lock.lock();
        return _unlock;
    }


    public static class Lock implements AutoCloseable {
        public void close() throws Exception {
        }
    }

    public class Unlock extends Lock {
        public void close() throws Exception {
            _lock.unlock();
        }
    }

    public static void main(String[] args) {
        Locker _locker = new Locker();
        try (Lock lock = _locker.lock()) {
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}