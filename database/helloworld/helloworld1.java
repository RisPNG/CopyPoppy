public class helloworld1 {
    public static void main(String[] args) {
        try {
            System.out.println("Hello World!");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
