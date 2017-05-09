/*
 * Implements the Set interface using a binary search tree.  We delegate
 * most of the work to existing methods in our BinarySearchTree class.
 */
import java.util.Iterator;
public class BSTSet<E extends Comparable<E>> implements Set<E> {

	private BinarySearchTree<E> data = new BinarySearchTree<>();
	
	@Override
	public void add(E newItem) {	// no need to explicitly check for duplicates -- the BST add method already does this
		data.add(newItem);
	}

	@Override
	public void remove(E someItem) {
		data.delete(someItem);
	}

	@Override
	public boolean contains(E someItem) {
		return (data.find(someItem) != null);
	}
	
//******************************************HERE*****************************************\\
//we create new iterators so that we can traverse thru the bst, then we create a new bst set
// so that we can store the values we need for the union. Since the new bst set is empty
// we can simple add all the elements from the calling set. Afterwards we traverse thru the 
// second bst to check if the elements in the new bst set are not alrdy there
	public BSTSet<E> union(BSTSet<E> s) {
			Iterator<E> a = data.iterator();
			Iterator<E> b = s.data.iterator();
			
			
			BSTSet<E> c = new BSTSet<>();
			while (a.hasNext())
				c.add(a.next());
			while (b.hasNext()) {
				E temp = b.next();
				if (!c.contains(temp))
				c.add(temp);
		}
			return c;
	}
	
	
	public String toString() {
		Iterator<E> a = data.iterator();
		String r = "";
		while (a.hasNext())
			r += a.next() + ", ";
		return r;
	}
}
