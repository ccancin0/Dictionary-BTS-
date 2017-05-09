/*
 * Interface specifying the basic set operations.  A set is an unordered collection
 *  of distinct items (i.e., it can't contain duplicate elements).
 */
public interface Set<E> {
	// Adds a new element to the set.  Must also check to make sure
	//  that the element doesn't already exist.
	void add(E newItem);

	// Removes a specific element from the set.
	void remove(E someItem);
	
	// Checks whether the set contains a specific element.
	boolean contains(E someItem);
}