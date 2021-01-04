
public class TestingBitwise {

    public static void main(String[] args) {
        System.out.println(getType(false, false));
        System.out.println(getType(true, false));
        System.out.println(getType(false, true));
        System.out.println(getType(true, true));
    }
    
    public static int getType(boolean first, boolean second) {
        return 1 << (first ? 1 : 0) << (second ? 2 : 0);
    }
}
