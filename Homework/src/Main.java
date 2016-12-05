import java.util.ArrayList;


public class Main {

    private static ArrayList<Integer> arr = new ArrayList<Integer>();

    public static void main(String[] args) {

        new Thread(new ThisIsWrite()).start();
        new Thread(new ThisIsRead()).start();

    }

    public static class ThisIsWrite implements Runnable {

        public void run() {

            try {

                for (int i = 0; i < 50; i++) {
                    arr.add(i);
                    Thread.sleep((long) (Math.random() * 2000));
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static class ThisIsRead implements Runnable {

        public void run() {

            try {

                for (int i = 0; i < 50; i++) {
                    Thread.sleep(1000);
                    System.out.println(i);
                    arr.remove(i);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();

            } catch (IndexOutOfBoundsException e) {
                System.out.println("Приложение завершило работу");
                System.exit(0);
            }
        }
    }
}