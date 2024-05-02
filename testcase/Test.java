public class Test {

    public static void main(String[] args) {
        for (int i = 0; i < 1000000000; ++i){
            int a = 10;
            int b = 20;
            int c = a + b;   // This value is used
            int d = 30;      // Dead code: d is written but never used
            int e = 40;      // Dead code: e is written but never used
            // System.out.println("Sum of a and b is: " + c);
        }
    }
}
