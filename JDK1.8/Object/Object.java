package java.lang;

public class Object {
	//调用本地方法
    private static native void registerNatives();
    static {
        registerNatives();
    }
	//返回此Object的运行时类
    public final native Class<?> getClass();
	//返回对象的哈希值
    public native int hashCode();
	//判断两个对象相等
    public boolean equals(Object obj) {
	return (this == obj);
    }
    protected native Object clone() throws CloneNotSupportedException;
	//返回对象的字符串表示
    public String toString() {
	return getClass().getName() + "@" + Integer.toHexString(hashCode());
    }
	//唤醒线程
    public final native void notify();
    public final native void notifyAll();
	//在其他线程调用此对象的 notify() 方法或 notifyAll() 方法前，导致当前线程等待。
    public final native void wait(long timeout) throws InterruptedException;

  
    public final void wait(long timeout, int nanos) throws InterruptedException {
        if (timeout < 0) {
            throw new IllegalArgumentException("timeout value is negative");
        }

        if (nanos < 0 || nanos > 999999) {
            throw new IllegalArgumentException(
				"nanosecond timeout value out of range");
        }

	if (nanos >= 500000 || (nanos != 0 && timeout == 0)) {
	    timeout++;
	}

	wait(timeout);
    }


    public final void wait() throws InterruptedException {
	wait(0);
    }
	//当垃圾回收器确定不存在对该对象的更多引用时，由对象的垃圾回收器调用此方法。
    protected void finalize() throws Throwable { }
}
