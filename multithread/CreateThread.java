package multithread;

import java.lang.Thread;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * Java 中创建线程的三种主要方式：
 * 1. 实现 Runnable 接口
 * 2. 继承 Thread 类
 * 3. 通过 Callable 和 Future 创建线程
 */
public class CreateThread {
    public static void main(String[] args) {
        //(1) 实现 Runnable 接口
        createThreadWithRunnable();

        //(2) 继承 Thread 类
        createThreadWithThreadSubclass();

        //(3) 通过 Callable 和 Future 创建线程
        createThreadWithCF();
    }

    //#region (1) 实现 Runnable 接口
    public static class MyRunnable implements Runnable {

        private String name;

        public MyRunnable(String name) {
            this.name = name;
        }

        //重写 run 方法
        @Override
        public void run() {
            System.out.println(name + " 任务开始执行，线程名: " + Thread.currentThread().getName());
            try {
                Thread.sleep(50); 
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println(name + " 任务执行完毕。");
        }
    }

    /**
     * 两种实现 Runnable 接口的方式
     * 1. 创建一个类实现 Runnable 接口，并重写 run 方法
     * 2. 使用匿名类或 Lambda 表达式
     */
    public static void createThreadWithRunnable() {
        MyRunnable task1 = new MyRunnable("任务1");
        MyRunnable task2 = new MyRunnable("任务2");
        Thread thread1 = new Thread(task1);
        Thread thread2 = new Thread(task2);

        Thread thread3 = new Thread(() -> {
            System.out.println("任务3 任务开始执行，线程名: " + Thread.currentThread().getName());
            try {
                Thread.sleep(50); 
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("任务3 任务执行完毕。");
        });

        thread1.start();
        thread2.start();
        thread3.start();
    }
    //#endregion

    //#region (2) 继承 Thread 类
    public static class MyThread extends Thread {
        private String name;

        public MyThread(String name) {
            this.name = name;
        }

        //重写 run 方法
        @Override
        public void run() {
            System.out.println(name + " 任务开始执行，线程名: " + Thread.currentThread().getName());
            try {
                Thread.sleep(50); 
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println(name + " 任务执行完毕。");
        }
    }

    /**
     * 两种继承 Thread 类的方式
     * 1. 创建一个类继承 Thread 类，并重写 run 方法
     * 2. 使用匿名类
     */
    public static void createThreadWithThreadSubclass() {
        MyThread thread1 = new MyThread("任务A");
        MyThread thread2 = new MyThread("任务B");

        Thread thread3 = new Thread() {
            @Override
            public void run() {
                System.out.println("任务C 任务开始执行，线程名: " + Thread.currentThread().getName());
                try {
                    Thread.sleep(50); 
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("任务C 任务执行完毕。");
            }
        };

        thread1.start();
        thread2.start();
        thread3.start();
    }
    //#endregion

    //#region (3) 通过 Callable 和 Future 创建线程
    /**
     * 实现 Callable 接口
     * <T> - 任务返回的结果类型
     * V - 任务可能抛出的异常类型
     */
    public static class MyCallableTask implements Callable<Integer> {
        private int number;

        public MyCallableTask(int number) {
            this.number = number;
        }

        //重写 call 方法
        @Override
        public Integer call() throws Exception {
            System.out.println("任务 [" + number + "] 在线程 " + Thread.currentThread().getName() + " 中开始计算...");
            Thread.sleep(1000);
            
            // 任务逻辑：计算并返回一个结果
            int result = number * number; 
            
            // 可以抛出受检异常
            if (number < 0) {
                throw new IllegalArgumentException("数字不能为负数");
            }
            
            System.out.println("任务 [" + number + "] 计算完成。");
            return result; 
        }
    }

    /**
     * 通常使用 ExecutorService 提交 Callable 任务，并通过 Future 获取结果
     */
    public static void createThreadWithCF() {

        // 创建一个固定线程池
        ExecutorService executor = Executors.newFixedThreadPool(3); 

        MyCallableTask task1 = new MyCallableTask(5);
        MyCallableTask task2 = new MyCallableTask(10);

        Future<Integer> future1 = executor.submit(task1);
        Future<Integer> future2 = executor.submit(task2);

        //不使用线程池(不常用)
        //使用 FutureTask 包装 Callable 对象
        FutureTask<Integer> future3 = new FutureTask<>(new MyCallableTask(15));
        new Thread(future3, "任务3").start();

        try {
            Integer result1 = future1.get(); 
            Integer result2 = future2.get(); 

            System.out.println("任务1 结果: " + result1);
            System.out.println("任务2 结果: " + result2);
            System.out.println("任务3 结果: " + future3.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }
    //#endregion
}