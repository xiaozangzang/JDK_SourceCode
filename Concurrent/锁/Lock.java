package java.util.concurrent.locks;
import java.util.concurrent.TimeUnit;

public interface Lock {

    void lock();

    void lockInterruptibly() throws InterruptedException;

    boolean tryLock();

    boolean tryLock(long time, TimeUnit unit) throws InterruptedException;

    void unlock();
	//创建与该Lock相关联的Condition对象
    Condition newCondition();
}
