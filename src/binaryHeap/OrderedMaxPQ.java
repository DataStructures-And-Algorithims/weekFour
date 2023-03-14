package binaryHeap;

import common.Item;
import common.Key;
import common.Utils;

import static common.Utils.exch;
import static common.Utils.less;

// This is a queue that is heap ordered
// This means that there is no parent key that is smaller than the children
// The Key whose position is 1 is the largest.
public class OrderedMaxPQ <K extends Comparable<Item>> {
    private static  Item[] pq; // pq[i] = ith element on pq
    private int N; // number of elements on pq

    public OrderedMaxPQ() {
        pq = new Item[2];
        // Set the value at index 0 to null to avoid null pointer exception
        pq[0] = new Item("null",new Key(0));
        N = 0;
    }

    // Automatically the largest key will be stored in a[1]
    public boolean isEmpty() { return N == 0; }

    public int size() { return N; }

    // A node is inserted at the end, and then it is swum up
    public void insert(Item x) {
        // The capacity is increased by twice
        if(N == (pq.length - 1)) pq = Utils.resize((2 * pq.length), pq);
        // Insert key at the end of the node
        pq[++N] = x;
        // Swim the node to the right spot where the invariant is maintained
        swim(N);
    }

    // The maximum value is always located at pq[1]
    public void delMax() {
        // Reduce size of array
        if(N < pq.length/2) pq = Utils.resize(pq.length/2,pq);
        Item max = pq[1];
        // Exchange root Key with the key at the end then decrement N
        exch(pq,1,N--);
        // Remove the key that was the largest
        pq[N + 1] = new Item("null",new Key(0));
        // Sink the newly created root until the invariant is maintained
        sink(1);
    }

    // int k corresponds to array position
    // The largest value is placed in index 1
    public void swim(int k) {
        // Parent of Key current is a Key parent whose array position == k/2
        // (k > 1)Checks if the array position is greater than 1
        // less(k/2,k) checks if the parent of a key located at position k/2 is smaller than the key located at position k
        while(k > 1 && less(pq[k/2],pq[k])) {
            // If true it means that the Key at position k/2 is smaller than the Key at position k which breaks the invariant
            // that no parent is smaller than its child
            // We therefore exchange positions to maintain the invariant
            exch(pq,k,k/2);
            // The key at the parent position becomes the new key to be swum if necessary
            k = k/2;
        }
    }

    // Sink a parent key that is smaller than its children
    public void sink(int k) {
       while(2*k <= N) {
           // One child of the key located at k
           int j = 2*k;
           // The smaller parent is sunk towards the bigger child
           // less(pq[j],pq[j + 1]) checks which child is smaller
           // if pq[j] is smaller, then j++ is executed meaning that the key will be sunk towards the pq[j+1] child
           // so that the larger child becomes the new root
           // otherwise the child will be sunk towards the pq[j]
           if(j < N && less(pq[j],pq[j+1])) j++;
           // if the key at position k is not smaller than its child exit the loop
           if(!less(pq[k],pq[j])) break;
           exch(pq,k,j);
           // The key at the child becomes the new key that is to be sunk if necessary
           k = j;
       }
    }
    // Print queue
    public void printQueue() {
        for(Item key : pq)
            System.out.print(key.itemName + " ");
    }
}
