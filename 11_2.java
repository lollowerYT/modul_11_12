package org.example;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class PoolExample {

    public static void main(String[] args) throws InterruptedException {

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                3, 3, 1, TimeUnit.SECONDS, new LinkedBlockingQueue<>(3));

        // обработчик блокирующий отправителя при переполненной очереди
        executor.setRejectedExecutionHandler((r, exec) -> {
            try {
                exec.getQueue().put(r);  // блокирует поток, пока очередь не освободится
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        AtomicInteger count = new AtomicInteger(0);
        AtomicInteger inProgress = new AtomicInteger(0);

        // отправляем задачи на выполнение
        for (int i = 0; i < 30; i++) {
            final int number = i;
            Thread.sleep(10);

            System.out.println("creating #" + number);
            executor.submit(() -> {

                int working = inProgress.incrementAndGet();
                System.out.println("start #" + number + ", in progress: " + working);

                try {
                    Thread.sleep(Math.round(1000 + Math.random() * 2000));
                } catch (InterruptedException e) {
                }

                working = inProgress.decrementAndGet();
                System.out.println("end #" + number +
                        ", in progress: " + working +
                        ", done tasks: " + count.incrementAndGet());

                return null;
            });
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.HOURS);
    }
}
