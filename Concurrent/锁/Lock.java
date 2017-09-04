package java.util.concurrent.locks;
import java.util.concurrent.TimeUnit;

public interface Lock {
	
    void lock();
	//可中断的所获取操作
    void lockInterruptibly() throws InterruptedException;
	//可定时与可轮询的锁获取模式实现
    boolean tryLock();

    boolean tryLock(long time, TimeUnit unit) throws InterruptedException;

    void unlock();
	//创建与该Lock相关联的Condition对象
    Condition newCondition();
}
