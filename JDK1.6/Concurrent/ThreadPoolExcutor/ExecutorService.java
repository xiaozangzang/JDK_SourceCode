package java.util.concurrent;
import java.util.List;
import java.util.Collection;
import java.security.PrivilegedAction;
import java.security.PrivilegedExceptionAction;

public interface ExecutorService extends Executor {
	//此时线程池不能接受新的任务，它会等待所有的任务都执行完毕；线程池状态shutdown=1
    void shutdown();
	//此时线程池不能接受新的任务，并且尝试中止正在执行的任务；线程池状态STOP=2
    List<Runnable> shutdownNow();
	//判断线程池是否线程池状态shutdown=1
    boolean isShutdown();
	//判断线程是否处于TERMINATED=3状态，当线程池处于1,2状态，所有工作线程已经销毁，
	//任务缓存队列已清空或执行结束后，线程池被设置为TERMINATED状态；
    boolean isTerminated();

    boolean awaitTermination(long timeout, TimeUnit unit)
        throws InterruptedException;
	//执行任务，有返回值，执行Runnable和Callable；
    <T> Future<T> submit(Callable<T> task);

    <T> Future<T> submit(Runnable task, T result);

    Future<?> submit(Runnable task);
	//执行给定的任务，当所有任务完成时，返回保持任务状态和结果的 Future 列表。
    <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks)
        throws InterruptedException;

    <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks,
                                  long timeout, TimeUnit unit)
        throws InterruptedException;

    <T> T invokeAny(Collection<? extends Callable<T>> tasks)
        throws InterruptedException, ExecutionException;

    <T> T invokeAny(Collection<? extends Callable<T>> tasks,
                    long timeout, TimeUnit unit)
        throws InterruptedException, ExecutionException, TimeoutException;
}
