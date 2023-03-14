package priorityQueue;

import common.Item;
import common.Key;
import common.Utils;

// This is a rudimentary example of a priorityQueue that doesn't order the keys as they are inserted
// K represent any type of object given will extend The Key class and hence will be Comparable
// This class inserts the key of objects/items into a PriorityQueue
public class UnorderedMaxPQ <K extends Comparable<Item>>{
    private static Item[] pq; // pq[i] = ith element on pq
    private int N; // number of elements on pq

    public UnorderedMaxPQ() {
        // This is an array that contains keys that are comparable
        // It is set to 1 since we do not want the client to explicitly give the size of the queue
        pq = new Item[1];
        N = 0;
    }

    public boolean isEmpty() { return N == 0; }

    public int size() { return N; }

    // N is also incremented at this stage
    public void insert(Item x) {
        // If the number of items is equal to the size of the array double the array size
        if(N == pq.length) pq = Utils.resize(2 * pq.length, pq);
        // Insert key at the end of array
        // N++ inserts then is ready for next insertion
        pq[N++] = x; }

    // Deletes the maximum key and returns it
    public void delMax() {
        // Reduce size of array
        if(N < pq.length/2) pq = Utils.resize(pq.length/2,pq);
        int maxPos = 0;
        Item max = pq[maxPos];
        // i starts at 1 since we are comparing with the other values
        for(int i = 1; i < N; i++)
            if(Utils.less(max,pq[i]))// If the maximum value is less than the value at index i, set the value at index i as the maximum
            {
                max = pq[i];
                maxPos = i;
            }

        // Put the max value at the end of the queue
        Utils.exch(pq, maxPos, N-1);
        pq[--N] = new Item("null",new Key(0));
    }

    // Print queue
    public void printQueue() {
        for(Item key : pq)
            System.out.print(key.itemName + " ");
    }
}
