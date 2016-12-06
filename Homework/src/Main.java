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
                int i = 0;
                while (true) {
                    arr.add(i);
                    i++;
                    Thread.sleep((long) (Math.random() * 1300));
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static class ThisIsRead implements Runnable {

        public void run() {

            try {
                int i = 0;
                while (true) {
                    Thread.sleep(1000);
                    System.out.println(i);
                    arr.remove(i);
                    i++;
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