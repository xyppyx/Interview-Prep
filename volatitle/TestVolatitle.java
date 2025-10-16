package volatitle;

public class TestVolatitle {
    
    class Worker implements Runnable {
        // 必须使用 volatile 保证 main 线程对 running 的修改，
        // 对 Worker 线程始终可见，防止 Worker 线程从缓存中读取旧值。
        private volatile boolean running = true; 

        @Override
        public void run() {
            // 业务逻辑循环
            while (running) {
                // ... 处理任务 ...
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    // 通常在这里设置 running = false;
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println("Worker 线程已停止。");
        }

        public void stop() {
            // 主线程修改 running 的值
            this.running = false; 
        }
    }

    public static void main(String[] args) throws InterruptedException {
        TestVolatitle test = new TestVolatitle();
        Worker worker = test.new Worker();
        Thread workerThread = new Thread(worker);
        workerThread.start();

        // 主线程等待一段时间后，停止 Worker 线程
        Thread.sleep(500);
        System.out.println("请求停止 Worker 线程...");
        worker.stop();

        // 等待 Worker 线程结束
        workerThread.join();
        System.out.println("主线程结束。");
    }
}
