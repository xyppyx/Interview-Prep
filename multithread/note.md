# 📚 Java 多线程核心概念笔记

## 🚀 1. 多线程基础

### 核心概念

| 概念                         | 描述                                                                                 |
| ---------------------------- | ------------------------------------------------------------------------------------ |
| **进程 (Process)**     | 操作系统中一个独立的执行环境，拥有独立的内存空间。一个 Java 程序就是一个 JVM 进程。  |
| **线程 (Thread)**      | 进程内的**独立执行路径** ，是 CPU 调度的最小单位。多个线程共享进程的内存资源。 |
| **并发 (Concurrency)** | 宏观上**看似同时**运行多个任务。即使单核 CPU 也能实现（通过时间片轮转）。      |
| **并行 (Parallelism)** | 多个任务在多核 CPU 上**真正地同时**运行。                                      |

### 为什么要使用多线程？

* **提高 CPU 利用率：** 充分利用多核处理器，特别是对于计算密集型任务。
* **提升响应速度：** 允许耗时的 I/O 或网络操作在后台运行，不阻塞主程序或用户界面。
* **简化程序设计：** 对于需要同时处理多个独立事件（如服务器处理多个客户端连接）的场景，设计更清晰。

## ⚙️ 2. 创建线程的两种主要方式（`Runnable` vs `Thread`）

Java 提供了两种创建线程任务的方式，其中 **实现 `Runnable` 接口是推荐的做法** 。

### 方式一：实现 `Runnable` 接口 **(推荐)**

这是最常用的方法，因为它将**任务（`Runnable`）**和**执行机制（`Thread`）**分离。

#### 步骤

1. **定义任务：** 创建一个类，实现 `java.lang.Runnable` 接口，并重写唯一的抽象方法 `run()`。
   **Java**

   ```
   public class MyTask implements Runnable {
       @Override
       public void run() {
           // 任务逻辑代码
           System.out.println("新线程正在执行...");
       }
   }
   ```
2. **创建线程：** 将 `Runnable` 实例作为参数传递给 `Thread` 类的构造函数。
   **Java**

   ```
   MyTask task = new MyTask();
   Thread t = new Thread(task, "Worker-Thread-A"); 
   ```
3. **启动线程：** 调用 `Thread` 对象的 `start()` 方法。
   **Java**

   ```
   t.start(); // 必须使用 start()，不能直接调用 run()
   ```

#### 优势

* **避免单继承限制：** 任务类可以继承其他父类，因为 Java 不支持多重继承，但支持实现多个接口。
* **实现资源共享：** 多个 `Thread` 对象可以共享同一个 `Runnable` 实例的数据。
* **解耦：** 任务逻辑 (What to do) 与线程控制 (How to run) 分离，更有利于线程池管理。

#### 现代（Java 8+）写法

可以使用 Lambda 表达式简化 `Runnable` 任务的创建：

**Java**

```
Thread lambdaThread = new Thread(() -> {
    System.out.println("Lambda 线程正在运行...");
});
lambdaThread.start();
```

### 方式二： 继承 `Thread` 类

这种方法直接利用 Java 线程的基类 `java.lang.Thread` 来定义任务。

#### 步骤

1. **定义任务：** 创建一个类，继承 `java.lang.Thread`，并重写父类的 `run()` 方法。
   **Java**

   ```
   public class MyThread extends Thread {
       private String name;

       public MyThread(String name) {
           super(name); // 调用父类构造函数设置线程名
           this.name = name;
       }

       @Override
       public void run() {
           // 任务逻辑代码
           System.out.println(this.name + " 线程正在执行...");
       }
   }
   ```
2. **创建线程：** 直接创建该子类的实例，这个实例 **本身就是线程对象** 。
   **Java**

   ```
   MyThread t = new MyThread("My-Worker-Thread");
   ```
3. **启动线程：** 调用线程对象的 `start()` 方法。
   **Java**

   ```
   t.start(); 
   ```

#### 局限性（不推荐的原因）

* **单继承限制：** Java 不支持多重继承。如果您的任务类已经继承了另一个业务类，就 **无法再继承 `Thread` 类** 。这是选择 `Runnable` 接口的主要原因。
* **任务与执行机制强耦合：** 任务逻辑被绑定在 `Thread` 类的子类中，不利于使用线程池等高级并发工具进行解耦和管理。
* **资源共享困难：** 当多个线程需要处理同一个任务实例的数据时，使用继承方式不如 `Runnable` 方便。

### 总结对比：`implements Runnable` vs `extends Thread`

| 特性               | 实现 `Runnable`接口 (推荐)                                            | 继承 `Thread`类 (传统)                         |
| ------------------ | ----------------------------------------------------------------------- | ------------------------------------------------ |
| **类的关系** | **组合** ：任务类 (`Runnable`) 和线程类 (`Thread`) 是分离的。 | **继承** ：任务类**就是**线程类。    |
| **可否继承** | 可以继承其他父类。                                                      | 不可以再继承其他类（受 Java 单继承限制）。       |
| **资源共享** | 容易实现，多个 `Thread`可传入同一个 `Runnable`实例。                | 相对困难，每个任务类实例本身就是一个独立的线程。 |
| **与线程池** | **高度兼容** ，是线程池框架的基础。                               | 兼容性差，不适合现代线程池模型。                 |

### 线程执行顺序的不确定性

**核心原则：**  **启动顺序 ****= 执行顺序** 。

* 调用 `thread.start()` 只是通知操作系统，该线程已准备好运行（进入**就绪**状态）。
* **线程调度器** （操作系统的一部分）决定哪个线程何时获得 CPU 时间片执行。
* 这种调度过程是**不可预测且高度依赖操作系统**的**异步**行为。

---

## 💻 3. 获取当前线程信息

### `Thread.currentThread().getName()`

| 方法调用                   | 作用                                                              |
| -------------------------- | ----------------------------------------------------------------- |
| `Thread.currentThread()` | 静态方法，返回**当前正在执行**这段代码的线程对象引用。      |
| `.getName()`             | 实例方法，获取该线程对象的名称。                                  |
| **组合效果**         | 获取并打印当前正在执行代码块的线程名称，常用于日志记录和调试。    |
| **默认名称**         | 主线程为 `"main"`；其他线程为 `"Thread-0"`,`"Thread-1"`等。 |

---

## ✨ 4. 使用 `Callable` 和 `Future`（高级方式）

`Callable` 和 `Future` 是更强大的并发模型，主要用于弥补 `Runnable` **不能返回值**和**不能抛出受检异常**的局限性。

### 核心组件

| 组件               | 接口 / 类           | 作用                                                                                          |
| ------------------ | ------------------- | --------------------------------------------------------------------------------------------- |
| **任务**     | `Callable<V>`     | 类似于 `Runnable`，但其 `call()`方法可以返回一个泛型结果 `V`，并能抛出异常。            |
| **执行器**   | `ExecutorService` | Java 线程池管理器，负责接收 `Callable`任务并分配给线程执行。                                |
| **结果凭证** | `Future<V>`       | 代表任务的**异步结果** 。它提供方法来检查任务是否完成、取消任务，以及阻塞式地获取结果。 |

### 步骤

1. **定义任务：** 实现 `Callable<V>` 接口，重写 `V call() throws Exception`。
2. **创建线程池：** 使用 `Executors` 工厂类创建 `ExecutorService` 实例。
   **Java**

   ```
   ExecutorService executor = Executors.newFixedThreadPool(10);
   ```
3. **提交任务并获取 Future：** 使用执行器的 `submit()` 方法提交任务。
   **Java**

   ```
   Callable<Integer> task = new MyCallableTask();
   Future<Integer> future = executor.submit(task);
   ```
4. **获取结果：** 通过 `Future` 对象的 `get()` 方法获取任务结果（该方法会**阻塞**直到结果可用）。
   **Java**

   ```
   Integer result = future.get(); // 阻塞获取结果
   ```
5. **关闭线程池：** 任务完成后，必须调用 `executor.shutdown()` 释放资源。

### 优势对比

| 特性               | `Runnable`                              | `Callable`                                                                   |
| ------------------ | ----------------------------------------- | ------------------------------------------------------------------------------ |
| **返回值**   | `void`（无）                            | `V`（有，泛型）                                                              |
| **异常抛出** | 只能抛出运行时异常 (`RuntimeException`) | 可以抛出任何 `Exception`（被 `Future.get()`封装为 `ExecutionException`） |
| **资源管理** | 手动创建 `Thread`，开销大               | 与 `ExecutorService`结合，高效管理线程池                                     |
| **任务控制** | 缺乏控制机制                              | `Future`提供 `isDone()`,`cancel()`,`get(timeout)`等完整控制            |

### 📢 FutureTask 补充

* **`FutureTask<V>`** 是一个实现了 `Runnable` 和 `Future<V>` 接口的类。
* 它可以将一个 `Callable` 任务包装成一个 `Runnable` 任务，从而能够被传统的 `Thread` 类执行（不使用线程池）。
* 但即便如此，在生产环境中仍 **强烈推荐使用 `ExecutorService`** 。
