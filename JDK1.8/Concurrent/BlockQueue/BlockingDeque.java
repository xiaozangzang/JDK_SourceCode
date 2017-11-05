package java.util.concurrent;
import java.util.*;


 * A {@link Deque} that additionally supports blocking operations that wait
 * for the deque to become non-empty when retrieving an element, and wait for
 * space to become available in the deque when storing an element.
 *
 * <p><tt>BlockingDeque</tt> methods come in four forms, with different ways
 * of handling operations that cannot be satisfied immediately, but may be
 * satisfied at some point in the future:
 * one throws an exception, the second returns a special value (either
 * <tt>null</tt> or <tt>false</tt>, depending on the operation), the third
 * blocks the current thread indefinitely until the operation can succeed,
 * and the fourth blocks for only a given maximum time limit before giving
 * up.  These methods are summarized in the following table:
 *
 * <p>
 * <table BORDER CELLPADDING=3 CELLSPACING=1>
 *  <tr>
 *    <td ALIGN=CENTER COLSPAN = 5> <b>First Element (Head)</b></td>
 *  </tr>
 *  <tr>
 *    <td></td>
 *    <td ALIGN=CENTER><em>Throws exception</em></td>
 *    <td ALIGN=CENTER><em>Special value</em></td>
 *    <td ALIGN=CENTER><em>Blocks</em></td>
 *    <td ALIGN=CENTER><em>Times out</em></td>
 *  </tr>
 *  <tr>
 *    <td><b>Insert</b></td>
 *    <td>{@link #addFirst addFirst(e)}</td>
 *    <td>{@link #offerFirst(Object) offerFirst(e)}</td>
 *    <td>{@link #putFirst putFirst(e)}</td>
 *    <td>{@link #offerFirst(Object, long, TimeUnit) offerFirst(e, time, unit)}</td>
 *  </tr>
 *  <tr>
 *    <td><b>Remove</b></td>
 *    <td>{@link #removeFirst removeFirst()}</td>
 *    <td>{@link #pollFirst pollFirst()}</td>
 *    <td>{@link #takeFirst takeFirst()}</td>
 *    <td>{@link #pollFirst(long, TimeUnit) pollFirst(time, unit)}</td>
 *  </tr>
 *  <tr>
 *    <td><b>Examine</b></td>
 *    <td>{@link #getFirst getFirst()}</td>
 *    <td>{@link #peekFirst peekFirst()}</td>
 *    <td><em>not applicable</em></td>
 *    <td><em>not applicable</em></td>
 *  </tr>
 *  <tr>
 *    <td ALIGN=CENTER COLSPAN = 5> <b>Last Element (Tail)</b></td>
 *  </tr>
 *  <tr>
 *    <td></td>
 *    <td ALIGN=CENTER><em>Throws exception</em></td>
 *    <td ALIGN=CENTER><em>Special value</em></td>
 *    <td ALIGN=CENTER><em>Blocks</em></td>
 *    <td ALIGN=CENTER><em>Times out</em></td>
 *  </tr>
 *  <tr>
 *    <td><b>Insert</b></td>
 *    <td>{@link #addLast addLast(e)}</td>
 *    <td>{@link #offerLast(Object) offerLast(e)}</td>
 *    <td>{@link #putLast putLast(e)}</td>
 *    <td>{@link #offerLast(Object, long, TimeUnit) offerLast(e, time, unit)}</td>
 *  </tr>
 *  <tr>
 *    <td><b>Remove</b></td>
 *    <td>{@link #removeLast() removeLast()}</td>
 *    <td>{@link #pollLast() pollLast()}</td>
 *    <td>{@link #takeLast takeLast()}</td>
 *    <td>{@link #pollLast(long, TimeUnit) pollLast(time, unit)}</td>
 *  </tr>
 *  <tr>
 *    <td><b>Examine</b></td>
 *    <td>{@link #getLast getLast()}</td>
 *    <td>{@link #peekLast peekLast()}</td>
 *    <td><em>not applicable</em></td>
 *    <td><em>not applicable</em></td>
 *  </tr>
 * </table>
 *
 * <p>Like any {@link BlockingQueue}, a <tt>BlockingDeque</tt> is thread safe,
 * does not permit null elements, and may (or may not) be
 * capacity-constrained.
 *
 * <p>A <tt>BlockingDeque</tt> implementation may be used directly as a FIFO
 * <tt>BlockingQueue</tt>. The methods inherited from the
 * <tt>BlockingQueue</tt> interface are precisely equivalent to
 * <tt>BlockingDeque</tt> methods as indicated in the following table:
 *
 * <p>
 * <table BORDER CELLPADDING=3 CELLSPACING=1>
 *  <tr>
 *    <td ALIGN=CENTER> <b><tt>BlockingQueue</tt> Method</b></td>
 *    <td ALIGN=CENTER> <b>Equivalent <tt>BlockingDeque</tt> Method</b></td>
 *  </tr>
 *  <tr>
 *    <td ALIGN=CENTER COLSPAN = 2> <b>Insert</b></td>
 *  </tr>
 *  <tr>
 *    <td>{@link #add(Object) add(e)}</td>
 *    <td>{@link #addLast(Object) addLast(e)}</td>
 *  </tr>
 *  <tr>
 *    <td>{@link #offer(Object) offer(e)}</td>
 *    <td>{@link #offerLast(Object) offerLast(e)}</td>
 *  </tr>
 *  <tr>
 *    <td>{@link #put(Object) put(e)}</td>
 *    <td>{@link #putLast(Object) putLast(e)}</td>
 *  </tr>
 *  <tr>
 *    <td>{@link #offer(Object, long, TimeUnit) offer(e, time, unit)}</td>
 *    <td>{@link #offerLast(Object, long, TimeUnit) offerLast(e, time, unit)}</td>
 *  </tr>
 *  <tr>
 *    <td ALIGN=CENTER COLSPAN = 2> <b>Remove</b></td>
 *  </tr>
 *  <tr>
 *    <td>{@link #remove() remove()}</td>
 *    <td>{@link #removeFirst() removeFirst()}</td>
 *  </tr>
 *  <tr>
 *    <td>{@link #poll() poll()}</td>
 *    <td>{@link #pollFirst() pollFirst()}</td>
 *  </tr>
 *  <tr>
 *    <td>{@link #take() take()}</td>
 *    <td>{@link #takeFirst() takeFirst()}</td>
 *  </tr>
 *  <tr>
 *    <td>{@link #poll(long, TimeUnit) poll(time, unit)}</td>
 *    <td>{@link #pollFirst(long, TimeUnit) pollFirst(time, unit)}</td>
 *  </tr>
 *  <tr>
 *    <td ALIGN=CENTER COLSPAN = 2> <b>Examine</b></td>
 *  </tr>
 *  <tr>
 *    <td>{@link #element() element()}</td>
 *    <td>{@link #getFirst() getFirst()}</td>
 *  </tr>
 *  <tr>
 *    <td>{@link #peek() peek()}</td>
 *    <td>{@link #peekFirst() peekFirst()}</td>
 *  </tr>
 * </table>
 *
 * <p>Memory consistency effects: As with other concurrent
 * collections, actions in a thread prior to placing an object into a
 * {@code BlockingDeque}
 * <a href="package-summary.html#MemoryVisibility"><i>happen-before</i></a>
 * actions subsequent to the access or removal of that element from
 * the {@code BlockingDeque} in another thread.
 *
 * <p>This interface is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * @since 1.6
 * @author Doug Lea
 * @param <E> the type of elements held in this collection
 */
public interface BlockingDeque<E> extends BlockingQueue<E>, Deque<E> {
    
     * Inserts the specified element at the front of this deque if it is
     * possible to do so immediately without violating capacity restrictions,
     * throwing an <tt>IllegalStateException</tt> if no space is currently
     * available.  When using a capacity-restricted deque, it is generally
     * preferable to use {@link #offerFirst(Object) offerFirst}.
     *
     * @param e the element to add
     * @throws IllegalStateException {@inheritDoc}
     * @throws ClassCastException {@inheritDoc}
     * @throws NullPointerException if the specified element is null
     * @throws IllegalArgumentException {@inheritDoc}
     */
    void addFirst(E e);

   
    void addLast(E e);

    boolean offerFirst(E e);

    boolean offerLast(E e);

    void putFirst(E e) throws InterruptedException;

    void putLast(E e) throws InterruptedException;

    boolean offerFirst(E e, long timeout, TimeUnit unit)
        throws InterruptedException;

    boolean offerLast(E e, long timeout, TimeUnit unit)
        throws InterruptedException;

    E takeFirst() throws InterruptedException;

    
    E takeLast() throws InterruptedException;

    E pollFirst(long timeout, TimeUnit unit)
        throws InterruptedException;

    E pollLast(long timeout, TimeUnit unit)
        throws InterruptedException;

    boolean removeFirstOccurrence(Object o);

    boolean removeLastOccurrence(Object o);

   
    boolean add(E e);

   
    boolean offer(E e);

    void put(E e) throws InterruptedException;

    boolean offer(E e, long timeout, TimeUnit unit)
        throws InterruptedException;

    E remove();

    E poll();

    E take() throws InterruptedException;

    E poll(long timeout, TimeUnit unit)
        throws InterruptedException;

    E element();

    E peek();
	
    boolean remove(Object o);

  
    public boolean contains(Object o);

    public int size();

    
    Iterator<E> iterator();

    void push(E e);
}
