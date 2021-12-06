package org.headroyce.dp1;

import java.util.Iterator;

/**
 * @author Brian Sea
 *
 * An iterable sigularly linked list data structure
 * @param <T> the type of data stored in the list
 */
public class LList<T> implements Iterable<T>{

    // TODO: Add appropriate attributes
    private Node <T> head;
    /**
     * Initializes an empty list
     */
    public LList(){
        // TODO: Complete Me
        head = null;
    }

    /**
     * Gets the size of the linked list.  Size is a read-only attribute.
     * @return the number of elements in the list
     */
    // Time Complexity: O(n)
    public int size(){
        // TODO: Complete Me
        Node<T> n = head;
        Integer count = 0;
        while (n != null) {
            count++;
            n = n.next;
        }
        return count;
    }

    public void clear(){
        this.head = null;
    }

    /**
     * Add to the end of the linked list
     * @param data the data to add to the list
     * @return true on success, false otherwise
     */
    // Time Complexity: O(n)
    public boolean add( T data ){
        // TODO: Complete Me
        Node<T> addMe = new Node<>(data);
        if (head == null) {
            head = addMe;
        } else {
            Node<T> n = head;
            while (n.next != null) {
                n = n.next;
            }
            n.next = addMe;
        }
        return true;
    }

    /**
     * Inserts data into the list after the index provided.
     * @param data the data to insert into the linked list
     * @param place the index to insert after. -1 indicates before head, > size indicates at the end of the list
     * @return true on success, false otherwise
     */
    // Time Complexity: O(n)
    public boolean insert( T data, int place ){
        // TODO: Complete Me
        Node<T> insertMe = new Node<>(data);
        if (place == -1) {
            insertMe.next = head;
            head = insertMe;
        }
        if (place >= this.size()) {
            this.add(data);
            return true;
        }

        Node<T> n = head;
        for (int i = 0; i < place; i++) {
            n = n.next;
        }
        insertMe.next = n.next;
        n.next = insertMe;
        return true;

        // return false;
    }

    /**
     * Removes an element from the list at the index provided.
     * @param place index to remove; <= 0 indicates removal of first element; > size indicates removal of last element
     * @return the data that was removed
     */
    // Time Complexity: O(n)
    public T remove( int place ){
        // TODO: Complete Me
        if (head == null) {
            return null;
        }
        T d = null;
        Node<T> curr = head;
        Node<T> prev = null;
        for (int i = 0; i < place && curr.next != null; i++) {
            prev = curr;
            curr = curr.next;
        }
        if (prev == null) {
            d = head.data;
            head = head.next;
            curr.next = null;
            return d;
        }
        prev.next = curr.next;
        curr.next = null;
        d = curr.data;
        return d;

    }

    /**
     * Gets the data from a provided index (stating at index zero)
     * @param place the index to retreive data from
     * @return the data at index place
     * @throws ArrayIndexOutOfBoundsException if place is outside the list
     */
    // Time Complexity: O(n)
    public T get( int place ){
        // TODO: Complete Me
        Node<T> n = head;
        for (int i = 0; i < place; i++) {
            if (n == null) {
                throw new ArrayIndexOutOfBoundsException("Attempted to get " + place + "out of list with maximum index " + (i));
            }
            n = n.next;
        }
        return n.data;
    }

    /**
     * Convert the LList into a String
     * @return a String in format [E0, E1, E2, ...]
     */
    // Time Complexity: O(n)
    public String toString(){
        // TODO: Complete Me
        String list = "[";
        Node<T> n = head;
        while (n != null) {
            list += n.data;
            if (n.next != null) {
                list +=  ", ";
            }
        }
        list += "]";
        return list;
    }

    /**
     * Provides an iterator for the list
     * @return a new iterator starting at the first element of the list
     */
    @Override
    public Iterator<T> iterator() {
        return new LListIterator<T>();
    }

    /**
     * Prints the linked list to the console
     */
    public void print(){
        //TODO: Comment this before submission

		/*Node<T> current = this.head;
        int spot = 0;
        while( current != null ){
            System.out.println(spot+": " + current.data.toString());
            spot = spot + 1;
            current = current.next;
        }*/
    }

    /**
     * Nodes that specific to the linked list
     * @param <E> the type of the Node. It must by T or extend it.
     */
    private class Node<E extends T>{
        // TODO: Add appropriate attributes
        public E data;
        public Node<E> next;
        public Node( E data ){
            // TODO: Complete Me
            this.data = data;
            this.next = null;
        }
    }

    /**
     * The iterator that is used for our list.
     */
    private class LListIterator<E extends T> implements Iterator<E>{

        // TODO: Add appropriate attributes
        Node<T> current;

        public LListIterator(){
            // TODO: Complete Me
            current = head;
        }

        // Time Complexity: O(1)
        @Override
        public boolean hasNext() {
            // TODO: Complete Me
            return current != null;
        }

        // Time Complexity: O(1)
        @Override
        public E next() {
            // TODO: Complete Me
            T data = current.data;
            current = current.next;
            return (E)data;
        }
    }
}
