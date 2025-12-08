import java.util.concurrent.atomic.AtomicInteger;

public class Lucky {

    private static final AtomicInteger x = new AtomicInteger(0);
    private static final AtomicInteger count = new AtomicInteger(0);
    private static final int MAX = 999999;

    static class LuckyThread extends Thread {
        @Override
        public void run() {
            while (true) {
                int value = x.incrementAndGet();
                if (value > MAX) break;

                int a = value % 10;
                int b = (value / 10) % 10;
                int c = (value / 100) % 10;

                int d = (value / 1000) % 10;
                int e = (value / 10000) % 10;
                int f = (value / 100000) % 10;

                if (a + b + c == d + e + f) {
                    System.out.println(value);
                    count.incrementAndGet();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new LuckyThread();
        Thread t2 = new LuckyThread();
        Thread t3 = new LuckyThread();

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println("Total: " + count.get());
    }
}

