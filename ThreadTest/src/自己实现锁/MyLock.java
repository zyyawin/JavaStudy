package 自己实现锁;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class MyLock implements Lock {
    private boolean isLocked =false;

    private Thread lockBy =null;
    private int lockCount = 0;
    @Override
    public synchronized void lock() {
        Thread currentThread  = Thread.currentThread();
        //自旋
        while(isLocked && currentThread!=lockBy){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            isLocked=true;
            lockBy=currentThread;
            lockCount++;
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long l, TimeUnit timeUnit) {
        return false;
    }

    @Override
    public synchronized void unlock() {
        if(lockBy==Thread.currentThread()){
            lockCount--;
            if(lockCount==0){
                notify();
                isLocked=false;
            }
        }
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
