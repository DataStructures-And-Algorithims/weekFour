public class test {
    public static void main(String[] args) {
        String[] alphabet = new String[] {"-","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
        String A = "G";
        System.out.print(" " + returnKey(A,alphabet));
        Integer in = 2;
    }
    public static int returnKey(String key,String[] a) {
            int lo = 0;
            int hi = a.length - 1;
            while (lo <= hi) {
                int mid = lo + (hi - lo) / 2;
                if      (key.compareTo(a[mid]) < 0) hi = mid - 1;
                else if (key.compareTo(a[mid]) > 0) lo = mid + 1;
                else return mid;
            }
            return -1;
    }
}
