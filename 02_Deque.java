import java.util.Iterator;
import java.util.NoSuchElementException;
/**
 *  implements a first-in-first-out (FIFO) queue of generic items.
*/

public class Deque<Item> implements Iterable<Item> {
    // initial capacity of underlying resizing array
    private static final int INIT_CAPACITY = 8;

    private Item[] q;       // queue elements
    private int n;          // number of elements on queue
    private int first;      // index of first element of queue
    private int last;       // index of next available slot

    //initialize empty queue
    public Deque() {
        q = (Item[]) new Object[INIT_CAPACITY];
        n = 0;
        first = 0;
        last = 0;
    }

    //checks if queue is empty
    public boolean isEmpty() {
        return n == 0;
    }

    //Returns the number of items in this queue
    public int size() {
        return n;
    }

    // resize the underlying array
    private void resize(int capacity) {
        assert capacity >= n;
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            copy[i] = q[(first + i) % q.length];
        }
        q = copy;
        first = 0;
        last  = n-1;
    }

    //adds to the front of the queue
    public void addFirst(Item item){
        if(item == null)
            throw new IllegalArgumentException("cannot add null");

        if (size() == q.length - 1){ //double size of array if necessary, recopy to front
            resize(2*q.length);   // double size of array if necessary
        }
        if(isEmpty()){ //queue is empty, first & last point to same index, insert
           first = 0;
           last = 0;
           q[0] = item;
        }
        else if(first == 0){// queue not empty, first @ index 0
            first = q.length - 1;
        }
        else{ // queue not empty, first not @ index 0
            first--;
        }
        q[first] = item;
        n++;
    }

    //adds to rear of the queue
    public void addLast(Item item){
        if(item == null)
            throw new IllegalArgumentException("cannot add null");

        if (size() == q.length - 1){ //double size of array if necessary, recopy to front
            resize(2*q.length);   // double size of array if necessary
        }
        if(size() == 0){ //queue is empty, first & last point to same index, insert
            first = 0;
            last = 0;
            q[0] = item;
        }
        else if(last == q.length - 1){ // last points to last element in array, wrap
            last = 0;
        }
        else{
            last++;
        }
        q[last] = item;
        n++;
    }

    //removes first element of queue
    public Item removeFirst(){
        if(isEmpty()){ // if try to dequeue empty queue, throw exception
            throw new NoSuchElementException("cannot dequeue from empty queue");
        }
        if (size() > 0 && n == q.length/4){ // resize array if necessary
            resize(q.length/2);
        }
        if(size() == 1){ // queue of size 1
            Item item = q[first];
            q[first] = null; // avoid loitering
            n = 0;
            first = 0;
            last = 0;
            StdOut.println(item);
            return item;
        }
        else if( n > 1 && first != q.length-1){ // queue size > 1, first not at end of array
            Item item = q[first];
            q[first] = null; // avoid loitering
            n--;
            first++;
            StdOut.println(item);
            return item;
        }
        else if(size() > 1 && first == q.length - 1){ //queue !empty, first not at last position in array
            Item item = q[first];
            q[first] = null;
            n--;
            first = 0;
            StdOut.println(item);
            return item;
        }
        else{
            throw new NoSuchElementException("n should not be less than zero");
        }
    }

    // removes last element of queue
    public Item removeLast(){
        if(isEmpty()){ // if try to dequeue empty queue, throw exception
            throw new NoSuchElementException("cannot dequeue from empty queue");
        }
        if (size() > 0 && n == q.length/4){ // remove array if necessary
            resize(q.length/2);
        }

        if(size() == 1){ // queue of size 1
            Item item = q[last];
            q[last] = null; // avoid loitering
            n = 0;
            first = 0;
            last = 0;
            StdOut.println(item);
            return item;
        }
        else if(size() > 1 && last != 0){ // queue !empty, last not at first position in array
            Item item = q[last];
            q[last] = null;
            n--;
            last--;
            StdOut.println(item);
            return item;
        }
        else if(size() > 1 && last == 0){
            Item item = q[last];
            q[last] = null;
            n--;
            last = q.length-1;
            StdOut.println(item);
            return item;
        }
        else{
            throw new NoSuchElementException("n should not be less than zero");
        }
    }

    //Returns an iterator that iterates over the items in this queue in FIFO order.
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }
    // an iterator, doesn't implement remove() since it's optional
    private class ArrayIterator implements Iterator<Item> {
        private int i = 0;
        public boolean hasNext()  { return i < n;                               }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = q[(i + first) % q.length];
            i++;
            return item;
        }
    }

    public static void main(String[] args) {
        Deque<String> queue = new Deque<String>();

        for(int i = 1; i <= 10; i++) {
            queue.addFirst(Integer.toString(i));
        }
        for(int j = 11; j <= 20; j++){
            queue.addLast(Integer.toString(j));
        }
        for(int k = 1; k <= 10; k++){
            queue.removeFirst();
        }
        for(int l = 1; l <= 10; l++){
            queue.removeLast();
        }

        /**
         * alternate iterator test client
         Deque<String> queue = new Deque<String>();

         for(int i = 1; i <= 10; i++) {
            queue.addFirst(Integer.toString(i));
         }
         for(String a : queue){
            StdOut.println(a);
        }*/
    }
}
