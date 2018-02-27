package java.util;

public class Vector<E>extends AbstractList<E>
    implements List<E>, RandomAccess, Cloneable, java.io.Serializable
{
	//Object数组
    protected Object[] elementData;
	//数组元素的个数
    protected int elementCount;
	//容量增长
    protected int capacityIncrement;
	//序列化
    private static final long serialVersionUID = -2767605614048989439L;
	//构造方法
    public Vector(int initialCapacity, int capacityIncrement) {
	super();
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal Capacity: "+
                                               initialCapacity);
	this.elementData = new Object[initialCapacity];
	this.capacityIncrement = capacityIncrement;
    }

	//初始化数组容量
    public Vector(int initialCapacity) {
	this(initialCapacity, 0);
    }

	//默认数组容量为10
    public Vector() {
	this(10);
    }
	//构造参数为Collection集合，元素的顺序按迭代器返回元素的顺序排列
    public Vector(Collection<? extends E> c) {
	elementData = c.toArray();
	elementCount = elementData.length;
	// c.toArray might (incorrectly) not return Object[] (see 6260652)
	if (elementData.getClass() != Object[].class)
	    elementData = Arrays.copyOf(elementData, elementCount, Object[].class);
    }
	//复制到指定数组中
    public synchronized void copyInto(Object[] anArray) {
	System.arraycopy(elementData, 0, anArray, 0, elementCount);
    }
	//对此向量的容量进行微调，使其等于向量的当前大小。
    public synchronized void trimToSize() {
	modCount++;
	int oldCapacity = elementData.length;
	if (elementCount < oldCapacity) {
            elementData = Arrays.copyOf(elementData, elementCount);
	}
    }
	//modCount表示此列表修改的次数，继承自AbstractList的属性
	//增加容量
    public synchronized void ensureCapacity(int minCapacity) {
	modCount++;
	ensureCapacityHelper(minCapacity);
    }


    private void ensureCapacityHelper(int minCapacity) {
	int oldCapacity = elementData.length;
	if (minCapacity > oldCapacity) {
	    Object[] oldData = elementData;
	    int newCapacity = (capacityIncrement > 0) ?
		(oldCapacity + capacityIncrement) : (oldCapacity * 2);
    	    if (newCapacity < minCapacity) {
		newCapacity = minCapacity;
	    }
            elementData = Arrays.copyOf(elementData, newCapacity);
	}
    }

	//设置新的容量
    public synchronized void setSize(int newSize) {
	modCount++;
	if (newSize > elementCount) {
	    ensureCapacityHelper(newSize);
	} else {
	    for (int i = newSize ; i < elementCount ; i++) {
		elementData[i] = null;
	    }
	}
	elementCount = newSize;
    }
	//返回数组容量大小
    public synchronized int capacity() {
	return elementData.length;
    }
	//返回数组元素数量
    public synchronized int size() {
	return elementCount;
    }
	//判空
    public synchronized boolean isEmpty() {
	return elementCount == 0;
    }
	//返回枚举
    public Enumeration<E> elements() {
	return new Enumeration<E>() {
	    int count = 0;

	    public boolean hasMoreElements() {
		return count < elementCount;
	    }

	    public E nextElement() {
		synchronized (Vector.this) {
		    if (count < elementCount) {
			return (E)elementData[count++];
		    }
		}
		throw new NoSuchElementException("Vector Enumeration");
	    }
	};
    }

    public boolean contains(Object o) {
	return indexOf(o, 0) >= 0;
   
    public int indexOf(Object o) {
	return indexOf(o, 0);
    }

    public synchronized int indexOf(Object o, int index) {
	if (o == null) {
	    for (int i = index ; i < elementCount ; i++)
		if (elementData[i]==null)
		    return i;
	} else {
	    for (int i = index ; i < elementCount ; i++)
		if (o.equals(elementData[i]))
		    return i;
	}
	return -1;
    }

    
    public synchronized int lastIndexOf(Object o) {
	return lastIndexOf(o, elementCount-1);
    }

  
    public synchronized int lastIndexOf(Object o, int index) {
        if (index >= elementCount)
            throw new IndexOutOfBoundsException(index + " >= "+ elementCount);

	if (o == null) {
	    for (int i = index; i >= 0; i--)
		if (elementData[i]==null)
		    return i;
	} else {
	    for (int i = index; i >= 0; i--)
		if (o.equals(elementData[i]))
		    return i;
	}
	return -1;
    }

  
    public synchronized E elementAt(int index) {
	if (index >= elementCount) {
	    throw new ArrayIndexOutOfBoundsException(index + " >= " + elementCount);
	}

        return (E)elementData[index];
    }

   
    public synchronized E firstElement() {
	if (elementCount == 0) {
	    throw new NoSuchElementException();
	}
	return (E)elementData[0];
    }

   
    public synchronized E lastElement() {
	if (elementCount == 0) {
	    throw new NoSuchElementException();
	}
	return (E)elementData[elementCount - 1];
    }

   
    public synchronized void setElementAt(E obj, int index) {
	if (index >= elementCount) {
	    throw new ArrayIndexOutOfBoundsException(index + " >= " +
						     elementCount);
	}
	elementData[index] = obj;
    }

  
    public synchronized void removeElementAt(int index) {
	modCount++;
	if (index >= elementCount) {
	    throw new ArrayIndexOutOfBoundsException(index + " >= " +
						     elementCount);
	}
	else if (index < 0) {
	    throw new ArrayIndexOutOfBoundsException(index);
	}
	int j = elementCount - index - 1;
	if (j > 0) {
	    System.arraycopy(elementData, index + 1, elementData, index, j);
	}
	elementCount--;
	elementData[elementCount] = null; /* to let gc do its work */
    }

   
    public synchronized void insertElementAt(E obj, int index) {
	modCount++;
	if (index > elementCount) {
	    throw new ArrayIndexOutOfBoundsException(index
						     + " > " + elementCount);
	}
	ensureCapacityHelper(elementCount + 1);
	System.arraycopy(elementData, index, elementData, index + 1, elementCount - index);
	elementData[index] = obj;
	elementCount++;
    }

   
    public synchronized void addElement(E obj) {
	modCount++;
	ensureCapacityHelper(elementCount + 1);
	elementData[elementCount++] = obj;
    }

   
    public synchronized boolean removeElement(Object obj) {
	modCount++;
	int i = indexOf(obj);
	if (i >= 0) {
	    removeElementAt(i);
	    return true;
	}
	return false;
    }

   
    public synchronized void removeAllElements() {
        modCount++;
	// Let gc do its work
	for (int i = 0; i < elementCount; i++)
	    elementData[i] = null;

	elementCount = 0;
    }

    public synchronized Object clone() {
	try {
	    Vector<E> v = (Vector<E>) super.clone();
	    v.elementData = Arrays.copyOf(elementData, elementCount);
	    v.modCount = 0;
	    return v;
	} catch (CloneNotSupportedException e) {
	    // this shouldn't happen, since we are Cloneable
	    throw new InternalError();
	}
    }
	//返回一个数组
    public synchronized Object[] toArray() {
        return Arrays.copyOf(elementData, elementCount);
    }

    public synchronized <T> T[] toArray(T[] a) {
        if (a.length < elementCount)
            return (T[]) Arrays.copyOf(elementData, elementCount, a.getClass());

	System.arraycopy(elementData, 0, a, 0, elementCount);

        if (a.length > elementCount)
            a[elementCount] = null;

        return a;
    }

    // Positional Access Operations

  
    public synchronized E get(int index) {
	if (index >= elementCount)
	    throw new ArrayIndexOutOfBoundsException(index);

	return (E)elementData[index];
    }

    public synchronized E set(int index, E element) {
	if (index >= elementCount)
	    throw new ArrayIndexOutOfBoundsException(index);

	Object oldValue = elementData[index];
	elementData[index] = element;
	return (E)oldValue;
    }

    
    public synchronized boolean add(E e) {
	modCount++;
	ensureCapacityHelper(elementCount + 1);
	elementData[elementCount++] = e;
        return true;
    }

    public boolean remove(Object o) {
        return removeElement(o);
    }

    public void add(int index, E element) {
        insertElementAt(element, index);
    }

    public synchronized E remove(int index) {
	modCount++;
	if (index >= elementCount)
	    throw new ArrayIndexOutOfBoundsException(index);
	Object oldValue = elementData[index];

	int numMoved = elementCount - index - 1;
	if (numMoved > 0)
	    System.arraycopy(elementData, index+1, elementData, index,
			     numMoved);
	elementData[--elementCount] = null; // Let gc do its work

	return (E)oldValue;
    }

   
    public void clear() {
        removeAllElements();
    }

    // Bulk Operations

    public synchronized boolean containsAll(Collection<?> c) {
        return super.containsAll(c);
    }

  
    public synchronized boolean addAll(Collection<? extends E> c) {
	modCount++;
        Object[] a = c.toArray();
        int numNew = a.length;
	ensureCapacityHelper(elementCount + numNew);
        System.arraycopy(a, 0, elementData, elementCount, numNew);
        elementCount += numNew;
	return numNew != 0;
    }

    public synchronized boolean removeAll(Collection<?> c) {
        return super.removeAll(c);
    }

    public synchronized boolean retainAll(Collection<?> c)  {
        return super.retainAll(c);
    }

    public synchronized boolean addAll(int index, Collection<? extends E> c) {
	modCount++;
	if (index < 0 || index > elementCount)
	    throw new ArrayIndexOutOfBoundsException(index);

        Object[] a = c.toArray();
	int numNew = a.length;
	ensureCapacityHelper(elementCount + numNew);

	int numMoved = elementCount - index;
	if (numMoved > 0)
	    System.arraycopy(elementData, index, elementData, index + numNew,
			     numMoved);

        System.arraycopy(a, 0, elementData, index, numNew);
	elementCount += numNew;
	return numNew != 0;
    }

    public synchronized boolean equals(Object o) {
        return super.equals(o);
    }

    public synchronized int hashCode() {
        return super.hashCode();
    }

    public synchronized String toString() {
        return super.toString();
    }

    public synchronized List<E> subList(int fromIndex, int toIndex) {
        return Collections.synchronizedList(super.subList(fromIndex, toIndex),
                                            this);
    }

 
    protected synchronized void removeRange(int fromIndex, int toIndex) {
	modCount++;
	int numMoved = elementCount - toIndex;
        System.arraycopy(elementData, toIndex, elementData, fromIndex,
                         numMoved);

	// Let gc do its work
	int newElementCount = elementCount - (toIndex-fromIndex);
	while (elementCount != newElementCount)
	    elementData[--elementCount] = null;
    }

    private synchronized void writeObject(java.io.ObjectOutputStream s)
        throws java.io.IOException
    {
	s.defaultWriteObject();
    }
}
