// TO DO: add your implementation and JavaDocs.
package Assignment;
import java.util.Iterator;

public class DynamicArray<T> implements Iterable<T> {
    private static final int INITCAP = 2; //default initial capacity
    private T[] storage; //underlying array, you MUST use this for credit (do not change the name or type)
    private int size;
    @SuppressWarnings("unchecked")
    public DynamicArray(){
        // constructor
        // Initial capacity of the storage should be INITCAP
        this(INITCAP);

    }

    @SuppressWarnings("unchecked")
    public DynamicArray(int initCapacity) {
        //constructor
        // The initial capacity of the storage should be initCapacity
        // Throw IllegalArgumentException if initCapacity < 1
        // Use this _exact_ error message for the exception
        // (quotes are not part of the message):
        //    "Capacity cannot be zero or negative."
        if (initCapacity < 1) {
            throw new IllegalArgumentException("Capacity cannot be zero or negative.");
        }
        storage = (T[]) new Object[initCapacity];
        size = 0;
    }
    public int size() {
        // Report the current number of elements.
        // O(1)
        return size;
    }

    public int capacity() {
        // Report the max number of elements before expansion.
        // O(1)
        return storage.length;
    }

    public T set(int index, T value) {
        // Change the item at the given index to be the given value.
        // Return the old item at that index.
        // Note: You cannot add new items with this method.

        // O(1)

        // For an invalid index, throw an IndexOutOfBoundsException
        // Use this code to produce the correct error message for
        // the exception (do not use a different message):
        //	  "Index " + index + " out of bounds!"

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds!");
        }
        T old = storage[index];
        storage[index] = value;
        return old;
    }

    public T get(int index) {
        // Return the item at the given index
        // O(1)
        // Use the exception (and error message) described in set()
        // for invalid indicies.
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds!");
        }
        return storage[index];
    }
    public boolean contains(T current) {
        for (int i = 0; i < size; i++) {
            if (storage[i] == null && current == null||(storage[i] != null && storage[i].equals(current))) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public boolean add(T value) {
        // Append an element to the end of the list and return true.
        // Double the capacity if no space available.
        // Amortized O(1)
        // nulls ARE accepted as valid values and are treated normally
        if (size == storage.length) {
            T[] newsto = (T[]) new Object[storage.length * 2];
            System.arraycopy(storage, 0, newsto, 0, storage.length);
            storage = newsto;
        }
        storage[size] = value;
        size++;
        return true;
    }

    @SuppressWarnings("unchecked")
    public void add(int index, T value) {
        // Insert the given value at the given index. Shift elements if needed,
        // double capacity if no space available, throw an exception if you cannot
        // insert at the given index. You _can_ append items with this method.
        // For the exception, use the same exception and message as set() and
        // get()... however remember that the condition of the exception is
        // different (different indexes are invalid).
        // O(N) where N is the number of elements currently in the list
        // nulls ARE accepted as valid values and are treated normally

        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds!");
        }
        if (size == storage.length) {
            T[] newsto = (T[]) new Object[storage.length * 2];
            System.arraycopy(storage, 0, newsto, 0, storage.length);
            storage = newsto;
        }
        System.arraycopy(storage, index, storage, index + 1, size - index);
        storage[index] = value;
        size++;
    }


    @SuppressWarnings("unchecked")
    public T remove(int index) {
        // Remove and return the element at the given index. Shift elements
        // to remove the gap. Throw an exception when there is an invalid
        // index (see set(), get(), etc. above).

        // Halve capacity of the storage if the number of elements falls
        // below 1/3 of the capacity.
        // USE INTEGER DIVISION
        // see main() testing for example of expected shrinking behavior

        // O(N) where N is the number of elements currently in the list

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds!");
        }
        T removedValue = storage[index];
        System.arraycopy(storage, index + 1, storage, index, size - index - 1);
        size--;
        if (size < storage.length / 3 && storage.length > INITCAP) {
            int newcap = Math.max(INITCAP, storage.length / 2);
            T[] newsto = (T[]) new Object[newcap];
            System.arraycopy(storage, 0, newsto, 0, size);
            storage = newsto;
        }
        return removedValue;
    }

    public Iterator<T> iterator() {
        // Uses an anonymous class style, complete the iterator code
        // below. Note that this uses the "diamond syntax" which is
        // only available for nested classes from Java 9 forward.
        // If you get an error on the next line you can add a <T>
        // betwen the <> or you can (and should) update your
        // version of the JDK.


        return new Iterator<>() {
            //instance variables here
            //only _required_ methods are outlined below
            //the interface also has some optional methods
            //you may implement if you find them helpful

            private int currentIndex = 0;
            public T next() {
                //your code here
                return storage[currentIndex++];
            }

            public boolean hasNext() {
                //your code here
                return currentIndex < size;
            }
        };
    }

    //******************************************************
    //*******     BELOW THIS LINE IS TESTING CODE    *******
    //*******      Edit it as much as you'd like!    *******
    //******************************************************

    public String toString() {
        //This method is provided for debugging purposes
        //(use/modify as much as you'd like), it just prints
        //out the list ifor easy viewing.
        StringBuilder s = new StringBuilder("Dynamic array with " + size()
                + " items and a capacity of " + capacity() + ":");
        for (int i = 0; i < size(); i++) {
            s.append("\n  ["+i+"]: " + get(i));
        }
        return s.toString();

    }

    //JavaDoc note: How do you document a main? See Simulation.java for an example
    public static void main(String args[]){
        //These are _sample_ tests. If you're seeing all the "yays" that's
        //an excellend first step! But it might not mean your code is 100%
        //working... You may edit this as much as you want, so you can add
        //own tests here, modify these tests, or whatever you need!

        DynamicArray<Integer> ida = new DynamicArray<>();
        if ((ida.size() == 0) && (ida.capacity() == 2)){
            System.out.println("Yay 1");
        }

        boolean ok = true;
        for (int i=0; i<3;i++)
            ok = ok && ida.add(i*5);

        if (ok && ida.size()==3 && ida.get(2) == 10 && ida.capacity() == 4 ){
            System.out.println("Yay 2");
        }

        ida.add(1,-10);
        ida.add(4,100);
        if (ida.set(1,-20)==-10 && ida.get(2) == 5 && ida.size() == 5
                && ida.capacity() == 8 ){
            System.out.println("Yay 3");
        }

        if (ida.remove(0) == 0 && ida.remove(0) == -20 && ida.remove(2) == 100
                && ida.size() == 2 && ida.capacity() == 4 ){
            System.out.println("Yay 4");
        }

        //Uncomment this after doing the iterator for testing

        System.out.print("Printing values: ");
        for(Integer i : ida) {
            System.out.print(i);
            System.out.print(" ");
        }

    }


}