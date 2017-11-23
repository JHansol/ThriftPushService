package sol.xyz.linears.push_client.httpLib;

/**
 * Created by han on 2017-10-30.
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class HttpThreadExecutor extends Thread {

    private static final ThreadFactory THREAD_FACTORY = new ThreadFactory() {
        @Override
        public Thread newThread(Runnable runnable) {
            thread = new HttpThreadExecutor(runnable);
            thread.setName("ThreadExecutor");
            return thread;
        }
    };

    private static HttpThreadExecutor thread;

    private static ExecutorService service;

    private static int counter = 0;


    private HttpThreadExecutor(Runnable runnable) {
        super(runnable);
    }

    public static boolean isCurrent() {
        return currentThread() == thread;
    }

    /**
     * ThreadExecutor 안의 태스크를 실행
     *
     * @param task
     */
    public static void exec(Runnable task) {
        if (isCurrent()) {
            task.run();
        } else {
            nextTick(task);
        }
    }

    /**
     * ThreadExecutor 동기화
     *
     * @param task
     */
    public static void nextTick(final Runnable task) {
        ExecutorService executor;
        synchronized (HttpThreadExecutor.class) {
            counter++;
            if (service == null) {
                service = Executors.newSingleThreadExecutor(THREAD_FACTORY);
            }
            executor = service;
        }

        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    task.run();
                } finally {
                    synchronized (HttpThreadExecutor.class) {
                        counter--;
                        if (counter == 0) {
                            service.shutdown();
                            service = null;
                            thread = null;
                        }
                    }
                }
            }
        });
    }
}