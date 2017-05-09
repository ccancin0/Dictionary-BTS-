/*
 * Class that represents a single entry (key-value pair) in a map.
 */
public class Entry<K, V> {
	private K key;
	private V value;
	
	// getters/setters for key/value
	public K getKey() {
		return key;
	}
	
	public void setKey(K key) {
		this.key = key;
	}
	
	public V getValue() {
		return value;
	}
	
	public void setValue(V value) {
		this.value = value;
	}
	
	public Entry(K key, V value) {
		setKey(key);
		setValue(value);
	}
	
	public String toString() {
		return "<" + value + ">";
	}

	// Entry objects should be compared only by their keys!
	public boolean equals(Object o) {
		if (o instanceof Entry)
			return key.equals(((Entry<K, V>)o).key);
		return false;
	}
}
