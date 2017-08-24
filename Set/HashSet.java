package java.util;

public class HashSet<E>
    extends AbstractSet<E>
    implements Set<E>, Cloneable, java.io.Serializable
{
    static final long serialVersionUID = -5024744406713321676L;

    private transient HashMap<E,Object> map;

    // Dummy value to associate with an Object in the backing Map
    private static final Object PRESENT = new Object();

    public HashSet() {
	map = new HashMap<E,Object>();
    }

    public HashSet(Collection<? extends E> c) {
	map = new HashMap<E,Object>(Math.max((int) (c.size()/.75f) + 1, 16));
	addAll(c);
    }

    public HashSet(int initialCapacity, float loadFactor) {
	map = new HashMap<E,Object>(initialCapacity, loadFactor);
    }

    public HashSet(int initialCapacity) {
	map = new HashMap<E,Object>(initialCapacity);
    }

    HashSet(int initialCapacity, float loadFactor, boolean dummy) {
	map = new LinkedHashMap<E,Object>(initialCapacity, loadFactor);
    }

    public Iterator<E> iterator() {
	return map.keySet().iterator();
    }

    public int size() {
	return map.size();
    }

    public boolean isEmpty() {
	return map.isEmpty();
    }

    public boolean contains(Object o) {
	return map.containsKey(o);
    }

    public boolean add(E e) {
	return map.put(e, PRESENT)==null;
    }

    public boolean remove(Object o) {
	return map.remove(o)==PRESENT;
    }

    public void clear() {
	map.clear();
    }

    public Object clone() {
	try {
	    HashSet<E> newSet = (HashSet<E>) super.clone();
	    newSet.map = (HashMap<E, Object>) map.clone();
	    return newSet;
	} catch (CloneNotSupportedException e) {
	    throw new InternalError();
	}
    }

    private void writeObject(java.io.ObjectOutputStream s)
        throws java.io.IOException {
	// Write out any hidden serialization magic
	s.defaultWriteObject();

        // Write out HashMap capacity and load factor
        s.writeInt(map.capacity());
        s.writeFloat(map.loadFactor());

        // Write out size
        s.writeInt(map.size());

	// Write out all elements in the proper order.
	for (Iterator i=map.keySet().iterator(); i.hasNext(); )
            s.writeObject(i.next());
    }

    
    private void readObject(java.io.ObjectInputStream s)
        throws java.io.IOException, ClassNotFoundException {
	// Read in any hidden serialization magic
	s.defaultReadObject();

        // Read in HashMap capacity and load factor and create backing HashMap
        int capacity = s.readInt();
        float loadFactor = s.readFloat();
        map = (((HashSet)this) instanceof LinkedHashSet ?
               new LinkedHashMap<E,Object>(capacity, loadFactor) :
               new HashMap<E,Object>(capacity, loadFactor));

        // Read in size
        int size = s.readInt();

	// Read in all elements in the proper order.
	for (int i=0; i<size; i++) {
            E e = (E) s.readObject();
            map.put(e, PRESENT);
        }
    }
}
