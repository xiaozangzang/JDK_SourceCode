package java.util.concurrent;

public interface Executor {
	//基于生产者-消费者模式实现，从任务队列取任务来执行
    void execute(Runnable command);
}
