package binarySearchTree;

import common.Item;
import common.Key;

import java.util.ArrayList;

// If less than parent, go to left node. If greater than parent go to right node -> This is the invariant that governs operations of BST
/*Symmetric order. Each node has a key,
and every node’s key is:
・Larger than all keys in its left subtree
・Smaller than all keys in its right subtree. */



/*
* TODO(A good analogy of searching via recursion is instead of knowing the whole road you just
*  follow instructions until one arrives at the intended destination...apply metaphor where relevant)
* */
public class BinarySearchTree <k extends Comparable<Item>>{
    private Node root;

    public class Node {
        public Item item;
        public Key key;
        public Node left, right;
        public int count;

        public Node(Key key,Item item) {
            this.key = key;
            this.item = item;
        }
    }

    // Two cases
    /*
    * Key in tree -> reset value
    * Key not in tree -> add new Node
    * */
    public void put(Key key,Item item) {
        root = put(root,key,item);
    }
    private Node put(Node x,Key key,Item item) {
        if(x == null) {
            x = new Node(key,item);
            ++x.count;
            return x; // Root or new node is created here
        }
        // In this case the items position is determined by their external key value
        int cmp = key.compareTo(x.key);
        // Current key is smaller than the root key
        if(cmp < 0)
            x.left = put(x.left,key,item);
        // Current key is larger than the root
        else if (cmp > 0)
            x.right = put(x.right,key,item);
        // If they are equal replace the contents
        else
            x.item = item;

        x.count = 1 + size(x.left) + size(x.right);
        return x;
    }
    // get Operation represents the search operation
    // Tree shape is dependent on the order of insertion
    public Item get(Item item) {
        Node x = root;
        while(x != null) {
            int cmp = item.compareTo(x.item);
            if (cmp < 0) x = x.left;
            if (cmp > 0) x = x.right;
            else return x.item; // They are equal hence the searched item has been found
        }
        return null;
    }
    // Delete operation
    // Delete any key
    public void delete(Key key) {
        root = delete(root,key);
    }
    // Deleting a node with zero children
    // We simply set the link of the parent to null
    // Deleting a node with one child
    // We set the link from the parent point to the child node
    // Deleting a node with 2 children
    // Search for a node t containing key k
    // Find successor x of t
    // The successor x is the minimum value of t.right
    // Set the children of x to be the same as the children of t
    public Node delete(Node x, Key key) {
        if(x == null) return null; // Ends the recursion
        int cmp = key.compareTo(x.key);
        // Searching recursively if the key to be deleted is to the left of the root
        if(cmp < 0) x.left = delete(x.left, key);
            // Searching recursively if the key to be deleted is to the right of the root
        else if(cmp > 0) x.right = delete(x.right,key);
            // We have found the key to delete and since there is no return statement execution continues
        else {
            // Key has no right child i.e. only has one child
            if(x.right == null) return x.left;
            // Key has no left child i.e. only has one child
            if(x.left == null) return x.right;

            // Has 2 children or no children
            Node t = x;
            // This is the successor of the deleted node
            // If the node has no children then t.right will return null since t == x
            x = min(t.right);
            // The right tree shouldn't have the minimum value since it was chosen as the successor of the deleted node
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        x.count = size(x.left) + size(x.right) + 1;
        return x;
    }

    // Return root of the binary tree
    public String root() {return root.item.itemName;}

    // Check the number of key value pairs
    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if(x == null) return 0;
        return x.count;
    }
    // The minimum value in a BST is the last/the deepest node without a left child in the left subtree
    public Node min() {
        return min(root);
    }
    // The minimum value of the tree
    // This same method can be reused within the class to find the minimum value within a specific subtree
    private Node min(Node x) {
        if(x.left == null) return x;
        // Recursively search for the minimum value
        return min(x.left);
    }

    // The maximum value of the tree
    // The maximum value in a BST is the last/the deepest node without a right child in the right subtree
    public Node max() {
        return max(root);
    }
    private Node max(Node x) {
        if(x.right == null) return x;
        // Recursively search fo the max value by checking the right children
        return max(x.right);
    }
    //TODO(FOR FLOOR AND CEILING THE RESULT IS AUTOMATICALLY AT THE END OF THE RECURSIVE TRANSVERSAL)

    // Floor -> This is the largest item that is smaller than a given item
    public String floor(Key key) {
        Node x = floor(root,key);
        if (x == null) return null;
        return x.item.itemName;
    }
    private Node floor(Node x,Key key) {
        if(x == null) return null;
        int cmp = key.compareTo(x.key);
        // There are 3 situations we need to consider when looking for the floor of an item
        // If  the parameter item is equal to the current item
        if(cmp == 0) return x;

        // Here we are traversing the left subtree to look for a possible floor item
        // If the parameter item is less than the current item search recursively for the floor in the left subtree
        // In this case null just means that the floor of this key is not located in the left subtree
        if(cmp < 0) return floor(x.left,key);

        // If the parameter item is greater than the current item search recursively for the floor in the right subtree of the current item since the floor can not be lesser than the current item therefore no need for left traversal. If there exists a child in the right subtree return it, otherwise return the current item that resulted in the initial right branch
        Node t = floor(x.right,key);
        // Floor is located in the right subtree
        if(t != null) return t;
        // Floor is not located in the right subtree therefore x is returned
        else return x;
    }
    // ceiling implementation
    // Ceiling is the smallest item greater or equal to
    // It has to be greater
    public String ceiling(Key key) {
        Node x = ceiling(root,key);
        if(x == null) return null;
        return x.item.itemName;
    }
    private Node ceiling(Node x, Key key) {
        if(x == null) return null;
        int cmp = key.compareTo(x.key);
        // If the current item is equal to the parameter item given by (key) return the current item
        if(cmp == 0) return x;

        // If the parameter item is less than the current item cpm == -1, search recursively of there is a child item that is greater than the parametrized item in the left subtree since we are looking for the smallest but greatest item
        // If there is return it, if there isn't return the item at the point where it branched to the left subtree
        if(cmp < 0) {
            Node t = ceiling(x.left,key); // The return function at this point will not lead to termination of this method
            if(t != null) return t;
            else return x;
        }
        // If the parameter item is greater than the current item cpm == 1, recursively search for the ceiling in the right subtree
        // The ceiling has to be greater or equal to the parameter item
        return ceiling(x.right,key); // The return function at this point will lead to termination of this method like a chain reaction /*
    }

    // Rank -> This is the number of items that are less than the passed key
    public int rank(Key key) {
        return rank(key,root);
    }

    private int rank(Key key, Node x) {
        if(x == null) return 0;
        int cmp = key.compareTo(x.key);
        // If the passed key called key is smaller than x
        // Check the rank of the node in the left subtree
        if(cmp < 0) return rank(key,x.left);
            // If the key passed is larger
            // 1. Count the current node as part of the rank
            // 2. Count all the children to the left of the current node
            // 3. Finally check if there are any smaller nodes to the right of the current node
        else if (cmp > 0) return 1 + size(x.left) + rank(key,x.right);
            // If they are equal count all the nodes to the left of the current node
        else return size(x.left);
    }

    // Delete min
    // Go left until you find a node without a left child
    // Replace that node by its right link
    // Update subtree counts
    public void deleteMin() {
        root = deleteMin(root);
    }
    private Node deleteMin(Node x) {
        if(x.left == null) return x.right;
        // The new smallest value is the right child of the old smallest value
        x.left = deleteMin(x.left);
        x.count = 1 + size(x.left) + size(x.right);
        return x;
    }
    // In BST the max is not always the root
    public void deleteMax() {
        root = deleteMax(root);
    }

    private Node deleteMax(Node x) {
        if(x.right == null) return x.left;
        // The new max value is the left child of the old max value
        x.right = deleteMax(x.right);
        x.count = 1 + size(x.right) + size(x.left);
        return x;
    }

    public Iterable<Item> iteratorOrdered() {
        ArrayList<Item> q = new ArrayList<>();
        inorder(root,q);
        for(Item item : q)
            System.out.print(item.itemName + " ");
        return q;
    }
    private void inorder(Node x,ArrayList<Item> q) {
        if(x == null) return;
        // Recursively iterate through the all left children
        inorder(x.left,q);
        // Place the element in the queue
        q.add(x.item);
        // Recursively iterate through all the right children
        inorder(x.right,q);
    }
    public void iteratorReverse() {
        ArrayList<Item> q = new ArrayList<>();
        inReverse(root,q);
        for(Item item : q)
            System.out.print(item.itemName + " ");
    }
    private void inReverse(Node x,ArrayList<Item> q){
        if(x == null) return;
        inReverse(x.right,q);
        q.add(x.item);
        inReverse(x.left,q);
    }


}
