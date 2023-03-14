package priorityQueue;

import common.Item;
import common.Key;

import java.util.Scanner;

public class priorityQueueClient {
    private static Scanner name;
    private static Scanner value;
    private static Scanner deleteMax;
    private static Scanner end;

    public static void main(String[] args) {
        UnorderedMaxPQ<Item> testList = new UnorderedMaxPQ<>();
        name = new Scanner(System.in);
        value = new Scanner(System.in);
        deleteMax = new Scanner(System.in);
        end = new Scanner(System.in);
        String ended = "n";
        System.out.println("Unordered Priority Queue operations");
        while(!ended.equals("end")) {
            int keyValue;
            String keyName;
            System.out.print("Enter string key name -> ");
            keyName = name.nextLine();
            System.out.println();
            System.out.print("Enter int key value -> ");
            keyValue = value.nextInt();
            testList.insert(new Item(keyName,new Key(keyValue)));
            System.out.println();
            System.out.print("Is empty value: " + testList.isEmpty());
            System.out.println();
            System.out.print("Priority queue: ");
            testList.printQueue();
            System.out.println();
            System.out.print("Queue size " + testList.size());
            System.out.println();
            System.out.print("If you wish to delete max value type x : -  " );
            String choice = deleteMax.nextLine();
            if(choice.equals("x")) {
                testList.delMax();
                System.out.print("Priority queue: ");
                testList.printQueue();
                System.out.println();
            }
            System.out.print("If you wish to exit Unordered Priority Queue operations type the word 'end' ->  ");
            ended = end.nextLine();
        }
    }
}