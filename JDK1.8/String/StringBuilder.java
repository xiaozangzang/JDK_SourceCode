package java.lang;

public final class StringBuilder extends AbstractStringBuilder implements java.io.Serializable, CharSequence
{
    static final long serialVersionUID = 4383685877147921099L;

    public StringBuilder() {
	super(16);
    }

    public StringBuilder(int capacity) {
	super(capacity);
    }

    public StringBuilder(String str) {
	super(str.length() + 16);
	append(str);
    }

    public StringBuilder(CharSequence seq) {
        this(seq.length() + 16);
        append(seq);
    }

    public StringBuilder append(Object obj) {
	return append(String.valueOf(obj));
    }

    public StringBuilder append(String str) {
	super.append(str);
        return this;
    }

    // Appends the specified string builder to this sequence.
    private StringBuilder append(StringBuilder sb) {
	if (sb == null)
            return append("null");
	int len = sb.length();
	int newcount = count + len;
	if (newcount > value.length)
	    expandCapacity(newcount);
	sb.getChars(0, len, value, count);
	count = newcount;
        return this;
    }

    public StringBuilder append(StringBuffer sb) {
        super.append(sb);
        return this;
    }

    public StringBuilder append(CharSequence s) {
        if (s == null)
            s = "null";
        if (s instanceof String)
            return this.append((String)s);
        if (s instanceof StringBuffer)
            return this.append((StringBuffer)s);
        if (s instanceof StringBuilder)
            return this.append((StringBuilder)s);
        return this.append(s, 0, s.length());
    }

    public StringBuilder append(CharSequence s, int start, int end) {
        super.append(s, start, end);
        return this;
    }

    public StringBuilder append(char str[]) { 
	super.append(str);
        return this;
    }

    public StringBuilder append(char str[], int offset, int len) {
        super.append(str, offset, len);
        return this;
    }

    public StringBuilder append(boolean b) {
        super.append(b);
        return this;
    }

    public StringBuilder append(char c) {
        super.append(c);
        return this;
    }

    public StringBuilder append(int i) {
	super.append(i);
        return this;
    }

    public StringBuilder append(long lng) {
        super.append(lng);
        return this;
    }

    public StringBuilder append(float f) {
	super.append(f);
        return this;
    }

    public StringBuilder append(double d) {
	super.append(d);
        return this;
    }

    public StringBuilder appendCodePoint(int codePoint) {
	super.appendCodePoint(codePoint);
	return this;
    }

    public StringBuilder delete(int start, int end) {
	super.delete(start, end);
        return this;
    }

    /**
     * @throws StringIndexOutOfBoundsException {@inheritDoc}
     */
    public StringBuilder deleteCharAt(int index) {
        super.deleteCharAt(index);
        return this;
    }

    public StringBuilder replace(int start, int end, String str) {
        super.replace(start, end, str);
        return this;
    }

    public StringBuilder insert(int index, char str[], int offset,
                                int len) 
    {
        super.insert(index, str, offset, len);
	return this;
    }

    public StringBuilder insert(int offset, Object obj) {
	return insert(offset, String.valueOf(obj));
    }

    public StringBuilder insert(int offset, String str) {
	super.insert(offset, str);
        return this;
    }

    public StringBuilder insert(int offset, char str[]) {
	super.insert(offset, str);
        return this;
    }

    public StringBuilder insert(int dstOffset, CharSequence s) {
        if (s == null)
            s = "null";
        if (s instanceof String)
            return this.insert(dstOffset, (String)s);
        return this.insert(dstOffset, s, 0, s.length());
    }

    public StringBuilder insert(int dstOffset, CharSequence s,
				int start, int end)
    {
        super.insert(dstOffset, s, start, end);
        return this;
    }

    public StringBuilder insert(int offset, boolean b) {
	super.insert(offset, b);
        return this;
    }

    public StringBuilder insert(int offset, char c) {
        super.insert(offset, c);
	return this;
    }

    public StringBuilder insert(int offset, int i) {
	return insert(offset, String.valueOf(i));
    }

    public StringBuilder insert(int offset, long l) {
	return insert(offset, String.valueOf(l));
    }

    public StringBuilder insert(int offset, float f) {
	return insert(offset, String.valueOf(f));
    }

    public StringBuilder insert(int offset, double d) {
	return insert(offset, String.valueOf(d));
    }

    public int indexOf(String str) {
	return indexOf(str, 0);
    }

    public int indexOf(String str, int fromIndex) {
        return String.indexOf(value, 0, count,
                              str.toCharArray(), 0, str.length(), fromIndex);
    }

    public int lastIndexOf(String str) {
        return lastIndexOf(str, count);
    }

    public int lastIndexOf(String str, int fromIndex) {
        return String.lastIndexOf(value, 0, count,
                              str.toCharArray(), 0, str.length(), fromIndex);
    }

    public StringBuilder reverse() {
	super.reverse();
	return this;
    }

    public String toString() {
        // Create a copy, don't share the array
	return new String(value, 0, count);
    }

    private void writeObject(java.io.ObjectOutputStream s)
        throws java.io.IOException {
        s.defaultWriteObject();
        s.writeInt(count);
        s.writeObject(value);
    }

    private void readObject(java.io.ObjectInputStream s)
        throws java.io.IOException, ClassNotFoundException {
        s.defaultReadObject();
        count = s.readInt();
        value = (char[]) s.readObject();
    }

}
