package heapSort;

// Heap sort works by removing the maximum value one at a time and placing it at the end of the array instead of nulling it out
import common.Utils;
import static common.Utils.less;

public class Heap {
    public static void sortNumbers(Integer[] a) {
        // int N represents the number of items to be sorted
        int N = a.length;
        // This is meant to build the heap using bottom up method
        /* This simply means that we first check if the bottom
        * row of the tree is heap ordered, and then we move up one level
        * and check if the invariant that no child is bigger than the parent
        * is maintained*/
        // Array has to be resized since the largest item is in a[1]
        a = new Utils<Integer>().resize(N + 1, a);

        for(int k = N/2; k >= 1; k--)
            sink(a,k,N);// Build heap from bottom up, left to right
        System.out.println("Heap formed");
        for (Integer anInt : a) {
            System.out.print(anInt + " ");
        }
        System.out.println();
        // Remove the maximum and place it at the end of the array
        while(N > 1) {
            exch(a,1,N);
            sink(a,1,--N);
        }
        System.out.println("Sorted array");
        for (Integer anInt : a) {
            System.out.print(anInt + " ");
        }
        System.out.println();
    }

    public static void sink(Integer[] pq, int k, int N) {
        while(2*k <= N) {
            // One child of the key located at k
            int j = 2*k;
            // The smaller parent is sunk towards the bigger child
            // less(pq[j],pq[j + 1]) checks which child is smaller
            // if pq[j] is smaller, then j++ is executed meaning that the key will be sunk towards the pq[j+1] child
            // so that the larger child becomes the new root
            // otherwise the child will be sunk towards the pq[j]
            if( j < N && (less(pq[j],pq[j+1]) < 0) ) j++;
            // if the key at position k is not smaller than its child exit the loop
            if( !(less(pq[k],pq[j]) < 0) ) break;
            exch(pq,k,j);
            // The key at the child becomes the new key that is to be sunk if necessary
            k = j;
        }
    }
    public static int less(Integer a, Integer b) {
        return a.compareTo(b);
    }

    public static void exch(Integer[] a, int i, int j) {
        Integer swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    public static void main(String[] args){
        Integer[] ints = new Integer[] {3,4,7,1,0,9,3,9,4,3,8,6,4};
        sortNumbers(ints);
    }
}
