import java.util.Iterator;
import java.util.Stack;

/*
 * Class that represents a binary search tree.
 * 
 * The generic type parameter E extends Comparable<E> ensures that whatever
 * data type we place into the tree must be comparable (i.e., must be from a 
 * class that implements the Comparable interface).  This is because all
 * of the BST algorithms (add, find, delete) require comparisons to determine
 * which side of the tree to travel down at each step.
 */
public class BinarySearchTree<E extends Comparable<E>> {
	
	// Nested class that implements an in-order iterator over the BST.  The
	//  iterator can be used to go through the elements of the tree from
	//  least to greatest, one at a time.
	private class InOrderIterator implements Iterator<E> {
		private Node<E> current = root;					// the iterator's current position
		private Stack<Node<E>> stack = new Stack<>();	// stack to maintain nodes to visit next
		
		// Returns whether the iterator has a next element.
		//  Only a placeholder for now; this should not always return false!
		public boolean hasNext() {
			return !(current == null && stack.isEmpty());
		}

		// Returns the iterator's next element.
		public E next() {
			while (current != null) {
				stack.push(current);
				current = current.left;
			}
			
			Node<E> popped = stack.pop();
			current = popped.right;
			return popped.data;
		}

		// Removes the element last returned by calling next().  Not implemented
		//  in this version.
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	
	// Nested class to represent a single node in the tree.  This is the
	//  same as our nested Node class in LinkedList, except each node
	//  now keeps track of two "next" nodes.
	private static class Node<E> {
		private E data;
		private Node<E> left, right;
		
		private Node(E data, Node<E> left, Node<E> right) {
			this.data = data;
			this.left = left;
			this.right = right;
		}
	}
	
	private Node<E> root;
	private E deleteReturn;		// keeps track of what to return when calling delete
	
	// Returns an iterator over this collection.  (Required for the class to implement
	// Iterable, which allows us to use the for-each syntax with objects of this class.)
	public Iterator<E> iterator() {
			return new InOrderIterator();
		}
	
	//*****************************HW 2 STARTS HERE**************************\\
	
	private Node<E> temp;      // keeps track of where we are in the bst
	
	// if tree is empty then we set that item to the root
	// while the temp which is equal to the root is not null
	// we iterate thru the tree comparing the new item and depending
	// on whether the item is greater or less than the root we make the
	// appropriate turn. If the item is already in the tree we just ignore it
	public void addIterative(E newItem) {
		if (root == null) {
			root = new Node<>(newItem, null, null);
			temp = root;
		}
		else {
			temp = root;
			while (temp != null) {
				if (newItem.compareTo(temp.data) < 0) {
					if (temp.left == null) {
						temp.left = new Node<>(newItem, null, null);
						return;
					}	
					else
						temp = temp.left;
				}
				else if (newItem.compareTo(temp.data) > 0) {
					if (temp.right == null) {
						temp.right = new Node<>(newItem, null, null);
						return;
					}
					else
						temp = temp.right;
				}
				else
					return;	
			}
		}
	}
	
	// this is basically the same code as the addIterative
	// if the bst is empty then we can just simply return null
	// we use the same method as the addIterative in which we
	// compare the item we are looking for with the data in the
	// tree and if we find it we just return the data of where its at
	public E findIterative(E someItem) {
		if (root == null)
			return null;
		
		temp = root;
		while (temp != null) {
			if (someItem.compareTo(temp.data) < 0) {
				//System.out.println(temp.data);
				if (temp.left == someItem) {
					return temp.left.data;
				}	
				else
					temp = temp.left;
			}
			else if (someItem.compareTo(temp.data) > 0) {
				//System.out.println(temp.data);
				if (temp.right == someItem) {
					return temp.right.data;
				}
				else
					temp = temp.right;
			}
			else
				return temp.data;	
		}
		return null;
	}
	
	//***************************STOPS HERE*********************************\\
	
	// Wrapper for inOrderTraversal.
	public void inOrderTraversal() {
		System.out.println("In-order traversal:");
		inOrderTraversal(root);
	}

	// Performs an in-order traversal of the tree, starting from the node where.
	private void inOrderTraversal(Node<E> where) {
		if (where != null) {	// base case is when where == null... do nothing if this happens
			inOrderTraversal(where.left);
			System.out.println(where.data);
			inOrderTraversal(where.right);
		}
	}

	// Wrapper for add.  Handles the special case of adding to the root (initially empty tree).
	public void add(E newItem) {
		if (root == null)
			root = new Node<>(newItem, null, null);
		else
			add(newItem, root);
	}

	// Adds the newItem to the tree rooted at where.
	private void add(E newItem, Node<E> where) {
		if (newItem.compareTo(where.data) < 0 && where.left == null)	// base case - add newItem as where's left child
			where.left = new Node<>(newItem, null, null);
		else if (newItem.compareTo(where.data) > 0 && where.right == null)	// base case - add newItem as where's right child
			where.right = new Node<>(newItem, null, null);
		else if (newItem.compareTo(where.data) < 0)		// recursively add newItem to where's left subtree
			add(newItem, where.left);
		else if (newItem.compareTo(where.data) > 0)		// recursively add newItem to where's right subtree
			add(newItem, where.right);
		
		// do nothing if compareTo returns 0 (i.e., newItem already exists in the tree)
	}
	
	// Wrapper for the textbook's version of add.
	public void add_book(E newItem) {
		root = add_book(newItem, root);
	}

	// Adds the newItem to the tree rooted at where.  Returns a reference
	//  to the root of that tree, with the newItem already added.
	private Node<E> add_book(E newItem, Node<E> where) {
		if (where == null)	// base case - adding to an empty tree (the root of the tree will be the new node containing newItem)
			return new Node<>(newItem, null, null);
		else {
			int compare = newItem.compareTo(where.data);
			if (compare < 0)	// recursively add to where's left subtree
				where.left = add_book(newItem, where.left);
			else if (compare > 0)	// recursively add to where's right subtree
				where.right = add_book(newItem, where.right);
			
			// do nothing if compare == 0 (newItem already exists in the tree)

			return where;	// the root of the tree is still at where!
		}
	}

	// Wrapper for find.
	public E find(E someItem) {
		return find(someItem, root);
	}

	// Searches the someItem in the tree rooted at where.  Returns the item from the tree
	//  if found, or null if not.
	private E find(E someItem, Node<E> where) {
		if (where == null)	// base case - empty tree (item not found!)
			return null;
		
		int compare = someItem.compareTo(where.data);
		if (compare == 0)	// base case - item found!
			return where.data;
		else if (compare < 0)	// recursively search in where's left subtree
			return find(someItem, where.left);
		else					// recursively search in where's right subtree
			return find(someItem, where.right);
	}

	// Wrapper for delete.  Returns the item from the tree if found, or null if not found.
	public E delete(E someItem) {
		root = delete(someItem, root);
		return deleteReturn;
	}
	
	// Deletes the specified someItem from the subtree rooted at where.
	// Returns a reference to the root of that subtree, with someItem deleted.
	private Node<E> delete(E someItem, Node<E> where) {
		if (where == null) {		// empty tree - item not found
			deleteReturn = null;
			return null;
		}
		
		int compare = someItem.compareTo(where.data);
		if (compare < 0) {			// recursively delete from where's left subtree
			where.left = delete(someItem, where.left);
			return where;
		} else if (compare > 0) {	// recursively delete from where's right subtree
			where.right = delete(someItem, where.right);
			return where;
		} else {	// someItem has been found, and now we need to get rid of it!
			deleteReturn = where.data;
			
			if (where.left == null && where.right == null) {		// case 1: no children
				return null;
			} else if (where.left != null && where.right == null) {	// case 2a: left child only
				return where.left;
			} else if (where.left == null && where.right != null) {	// case 2b: right child only
				return where.right;
			} else {												// case 3: oh noes!  we have two children
				where.data = findAndDeleteIOP(where);
				return where;
			}
		}
	}

	// Finds and deletes the in-order predecessor (IOP) of the 
	//  specified node.  Also returns the value of the IOP that
	//  was deleted.
	private E findAndDeleteIOP(Node<E> where) {
		Node<E> temp = where.left, parent = where;

		// search for the largest element from where's left subtree
		while (temp.right != null) {
			parent = temp;
			temp = temp.right;
		}

		if (parent == where)
			parent.left = temp.left;
		else
			parent.right = temp.left;
		return temp.data;
	}
	
	// Wrapper for toString.
	public String toString() {
		return toString(root, "");
	}

	// Returns a string representation of the tree rooted at where.
	private String toString(Node<E> where, String indent) {
		if (where == null)	// base case - empty tree
			return " ";
		else
			return where.data + toString(where.left, indent + " ") + toString(where.right, indent + " ");
	}
	
	public static void main(String[] args) {
		/*int[] stuff = {4, 4, 4, 2, 5, 3, 12, 2, 7, 2, 12, 3};
		
		BinarySearchTree<Integer> bst = new BinarySearchTree<>();
		for (int i : stuff)
			bst.add(i);

		System.out.println(bst);
		bst.inOrderTraversal();
		
		int[] stuffToFind = {12, 7, 5, 2, 3, 4, 0, 182390218, 21};
		for (int i : stuffToFind)
			System.out.println(bst.find(i));
		
		
		int[] stuffToDelete = {4, 3, 12, 5, 4};
		for (int i : stuffToDelete) {
			System.out.println("Deleting " + i);
			System.out.println(" returned: " + bst.delete(i));
			System.out.println(bst);
		}*/
		
		BinarySearchTree<Integer> bst2 = new BinarySearchTree<>();
		bst2.addIterative(5);
		bst2.addIterative(2);
		bst2.addIterative(7);
		bst2.addIterative(1);
		System.out.println(bst2);
		
		System.out.println(bst2.findIterative(3));
		System.out.println(bst2.findIterative(2));
		System.out.println(bst2.findIterative(7));
		System.out.println(bst2.findIterative(8));
		
	}
}