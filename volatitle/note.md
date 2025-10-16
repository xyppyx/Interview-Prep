# Java volatile 关键字详解

## 目录

1. [概述](#概述)
2. [volatile 的两大核心功能](#volatile-的两大核心功能)
   - [2.1 保证变量的内存可见性](#21-保证变量的内存可见性)
   - [2.2 禁止指令重排序](#22-禁止指令重排序)
3. [内存屏障（Memory Barrier）](#内存屏障memory-barrier)
4. [volatile 与 synchronized 的对比](#volatile-与-synchronized-的对比)
5. [典型使用场景与示例](#典型使用场景与示例)
   - [5.1 状态标志](#51-状态标志)
   - [5.2 双重检查锁单例模式](#52-双重检查锁单例模式)
6. [volatile 的局限性](#volatile-的局限性)
7. [总结](#总结)

---

## 概述

在 Java 中，`volatile` 关键字是一种轻量级的同步机制，具有特殊的内存语义。它主要用于多线程环境下保证变量的可见性和有序性。

---

## volatile 的两大核心功能

### 2.1 保证变量的内存可见性

**内存可见性**是指当一个线程修改了共享变量的值，其他线程能够立即看到这个修改。

#### 工作原理

- **写操作**：当一个线程对 `volatile` 修饰的变量进行写操作时，JMM（Java 内存模型）会立即把该线程本地内存中的共享变量值刷新到主内存。
- **读操作**：当一个线程对 `volatile` 修饰的变量进行读操作时，JMM 会立即将该线程对应的本地内存置为无效，强制从主内存中读取共享变量的最新值。

#### 示例：状态标志

```java
public class VolatileVisibilityDemo {
    private static volatile boolean flag = false;

    public static void main(String[] args) throws InterruptedException {
        // 线程1：等待 flag 变为 true
        Thread thread1 = new Thread(() -> {
            System.out.println("线程1启动，等待flag变为true...");
            while (!flag) {
                // 如果 flag 没有 volatile 修饰，
                // 这个线程可能永远看不到 flag 的改变
            }
            System.out.println("线程1检测到flag=true，结束运行");
        });

        thread1.start();
        Thread.sleep(1000);

        // 主线程：修改 flag
        System.out.println("主线程将flag设置为true");
        flag = true;
    }
}
```

---

### 2.2 禁止指令重排序

为了提高性能，编译器和处理器可能会对指令进行重排序。但在多线程环境下，指令重排序可能导致程序出现意料之外的结果。

`volatile` 通过插入**内存屏障**来禁止特定类型的重排序，确保程序按照预期顺序执行。

#### 重排序示例

```java
public class ReorderingDemo {
    private int a = 0;
    private volatile boolean flag = false;

    // 线程1执行
    public void writer() {
        a = 1;           // 步骤1
        flag = true;     // 步骤2（volatile写）
    }

    // 线程2执行
    public void reader() {
        if (flag) {      // 步骤3（volatile读）
            int i = a;   // 步骤4
            // 由于flag是volatile，这里i一定等于1
        }
    }
}
```

在上面的例子中，由于 `flag` 是 `volatile` 变量：

- 步骤1不会被重排序到步骤2之后
- 步骤4不会被重排序到步骤3之前
- 保证了线程2读到 `flag=true` 时，一定能看到 `a=1`

---

## 内存屏障（Memory Barrier）

**内存屏障**是一种底层机制，用于实现 `volatile` 的语义。

### 内存屏障的类型

硬件层面，内存屏障分为两种：

- **读屏障（Load Barrier）**
- **写屏障（Store Barrier）**

### 内存屏障的作用

1. **阻止屏障两侧的指令重排序**
2. **强制刷新缓存**：将写缓冲区/高速缓存中的脏数据写回主内存，或让缓存中的数据失效

### volatile 的内存屏障插入策略

为了实现 `volatile` 的内存语义，JMM 会在以下位置插入内存屏障：

| 操作位置                      | 屏障类型        | 作用                                                    |
| ----------------------------- | --------------- | ------------------------------------------------------- |
| volatile 写操作**之前** | StoreStore 屏障 | 禁止上面的普通写和下面的 volatile 写重排序              |
| volatile 写操作**之后** | StoreLoad 屏障  | 禁止上面的 volatile 写与下面可能的 volatile 读/写重排序 |
| volatile 读操作**之后** | LoadLoad 屏障   | 禁止下面所有的普通读操作和上面的 volatile 读重排序      |
| volatile 读操作**之后** | LoadStore 屏障  | 禁止下面所有的普通写操作和上面的 volatile 读重排序      |

---

## volatile 与 synchronized 的对比

| 特性               | volatile                       | synchronized              |
| ------------------ | ------------------------------ | ------------------------- |
| **可见性**   | ✅ 保证                        | ✅ 保证                   |
| **原子性**   | ❌ 仅保证单个读/写操作的原子性 | ✅ 保证临界区代码的原子性 |
| **有序性**   | ✅ 禁止指令重排序              | ✅ 保证                   |
| **性能**     | ⚡ 轻量级，性能更好            | 🔒 需要加锁，性能开销较大 |
| **适用场景** | 状态标志、一次性安全发布       | 复合操作、临界区保护      |

**总结**：

- 在**功能上**，锁比 `volatile` 更强大
- 在**性能上**，`volatile` 更有优势

---

## 典型使用场景与示例

### 5.1 状态标志

使用 `volatile` 修饰状态标志，控制线程的启动和停止。

```java
public class VolatileFlagDemo {
    private volatile boolean running = true;

    public void run() {
        System.out.println("任务开始执行...");
        while (running) {
            // 执行任务
        }
        System.out.println("任务已停止");
    }

    public void stop() {
        running = false;
    }
}
```

---

### 5.2 双重检查锁单例模式

这是 `volatile` 最经典的应用场景之一。

```java
public class Singleton {
    // 必须使用 volatile 修饰
    private static volatile Singleton instance;

    private Singleton() {}

    public static Singleton getInstance() {
        if (instance == null) {  // 第一次检查
            synchronized (Singleton.class) {
                if (instance == null) {  // 第二次检查
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
```

**为什么需要 volatile？**

`instance = new Singleton()` 这行代码实际上可以分解为三个步骤：

1. 分配内存空间
2. 初始化对象
3. 将 instance 指向分配的内存地址

如果发生重排序（2和3交换），可能导致其他线程获取到未完全初始化的对象。使用 `volatile` 可以禁止这种重排序。

---

## volatile 的局限性

### ❌ 不保证复合操作的原子性

```java
public class VolatileAtomicityDemo {
    private volatile int count = 0;

    public void increment() {
        count++;  // 非原子操作：读取 -> 加1 -> 写入
    }

    // 正确做法：使用 synchronized 或 AtomicInteger
    public synchronized void incrementSafe() {
        count++;
    }
}
```

`count++` 包含三个操作：

1. 读取 count 的值
2. 将值加1
3. 写回 count

`volatile` 只能保证每个单独操作的可见性，但无法保证这三个操作作为整体的原子性。

### ❌ 不适合复杂的状态依赖场景

如果一个变量的新值依赖于旧值，且有多个线程同时修改，`volatile` 无法保证正确性。

---

## 总结

### 何时使用 volatile？

✅ **适用场景**：

- 状态标志（开关变量）
- 双重检查锁
- 读多写少的场景
- 一次性安全发布（初始化后不再修改）

❌ **不适用场景**：

- 复合操作（如 i++）
- 需要保证多个操作原子性的场景
- 一个变量依赖另一个变量的场景

### 核心要点

1. **可见性**：保证修改对其他线程立即可见
2. **有序性**：通过内存屏障禁止指令重排序
3. **轻量级**：相比 synchronized，性能开销更小
4. **局限性**：不保证复合操作的原子性
