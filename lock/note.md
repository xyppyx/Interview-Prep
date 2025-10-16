# Java 多线程锁机制详解

## 目录

1. [Synchronized 关键字](#synchronized-关键字)
   - [1.1 基本使用](#11-基本使用)
   - [1.2 锁的对象](#12-锁的对象)
2. [锁的分类](#锁的分类)
   - [2.1 按 JVM 优化/底层状态划分（synchronized 的锁升级）](#21-按-jvm-优化底层状态划分synchronized-的锁升级)
   - [2.2 按乐观/悲观划分](#22-按乐观悲观划分)
   - [2.3 按公平性划分](#23-按公平性划分)
   - [2.4 按可重入性划分](#24-按可重入性划分)
   - [2.5 按共享/独占划分](#25-按共享独占划分)
3. [CAS 机制详解](#cas-机制详解)
   - [3.1 CAS 原理](#31-cas-原理)
   - [3.2 CAS 的问题](#32-cas-的问题)
   - [3.3 CAS 在 Java 中的应用](#33-cas-在-java-中的应用)
4. [Lock 接口与实现类](#lock-接口与实现类)
   - [4.1 ReentrantLock](#41-reentrantlock)
   - [4.2 ReadWriteLock](#42-readwritelock)
   - [4.3 StampedLock](#43-stampedlock)
5. [死锁问题](#死锁问题)
   - [5.1 死锁的四个必要条件](#51-死锁的四个必要条件)
   - [5.2 死锁示例](#52-死锁示例)
   - [5.3 如何避免死锁](#53-如何避免死锁)
6. [锁优化建议](#锁优化建议)
7. [总结](#总结)

---

## Synchronized 关键字

### 1.1 基本使用

`synchronized` 是 Java 中最基本的同步机制，用于保证多线程环境下的线程安全。它有三种使用形式：

```java
public class SynchronizedDemo {
  
    // 1. 修饰实例方法，锁是当前实例对象
    public synchronized void instanceMethod() {
        // 临界区代码
        System.out.println("实例方法被线程 " + Thread.currentThread().getName() + " 执行");
    }
  
    // 2. 修饰静态方法，锁是当前类的 Class 对象
    public static synchronized void staticMethod() {
        // 临界区代码
        System.out.println("静态方法被线程 " + Thread.currentThread().getName() + " 执行");
    }
  
    // 3. 修饰代码块，锁是括号里的对象
    public void blockMethod() {
        Object lock = new Object();
        synchronized (lock) {
            // 临界区代码
            System.out.println("代码块被线程 " + Thread.currentThread().getName() + " 执行");
        }
    }
  
    // 使用 this 作为锁对象
    public void blockMethodWithThis() {
        synchronized (this) {
            System.out.println("使用 this 作为锁");
        }
    }
  
    // 使用 Class 对象作为锁
    public void blockMethodWithClass() {
        synchronized (SynchronizedDemo.class) {
            System.out.println("使用 Class 对象作为锁");
        }
    }
}
```

### 1.2 锁的对象

| 锁的形式              | 锁的对象            | 作用范围                   |
| --------------------- | ------------------- | -------------------------- |
| 修饰实例方法          | 当前实例对象 (this) | 同一个对象的不同线程互斥   |
| 修饰静态方法          | 类的 Class 对象     | 该类的所有实例共享同一把锁 |
| 同步代码块 (this)     | 当前实例对象        | 同一个对象的不同线程互斥   |
| 同步代码块 (Class)    | 类的 Class 对象     | 该类的所有实例共享同一把锁 |
| 同步代码块 (任意对象) | 指定的对象          | 以该对象为锁               |

---

## 锁的分类

### 2.1 按 JVM 优化/底层状态划分（synchronized 的锁升级）

Java 6 为了减少获得锁和释放锁带来的性能消耗，引入了"偏向锁"和"轻量级锁"。一个对象有四种锁状态，级别由低到高依次是：

#### （1）无锁状态

对象刚创建时的状态，没有任何线程访问。

#### （2）偏向锁状态

**核心思想**：偏向锁会偏向于第一个访问锁的线程，如果在接下来的运行过程中，该锁没有被其他线程访问，则持有偏向锁的线程将永远不需要触发同步。

**实现原理**：

一个线程在第一次进入同步块时，会在对象头和栈帧中的锁记录里存储锁偏向的线程 ID。当下次该线程进入这个同步块时，会去检查锁的 Mark Word 里面是不是存放的自己的线程 ID。

- **如果是**：表明该线程已经获得了锁，无需再进行 CAS 操作来加锁和解锁
- **如果不是**：代表有另一个线程来竞争这个偏向锁，会尝试使用 CAS 来替换 Mark Word 里面的线程 ID
  - **成功**：之前的线程不存在了，锁不会升级，仍然为偏向锁
  - **失败**：之前的线程仍然存在，暂停之前的线程，升级为轻量级锁

**偏向锁的废弃**：

- JDK 15：偏向锁被默认关闭（可使用 `-XX:+UseBiasedLocking` 启用）
- JDK 18：偏向锁被彻底废弃

**废弃原因**：

1. **性能收益不明显**：现代 Java 应用更多使用高性能的并发集合类（如 `ConcurrentHashMap`），而不是早期的 `HashTable`、`Vector`
2. **撤销成本高**：偏向锁撤销需要等待全局安全点（Safe Point），此时所有线程都会暂停

#### （3）轻量级锁状态

**加锁过程**：

1. JVM 为每个线程在当前线程的栈帧中创建用于存储锁记录的空间（**Displaced Mark Word**）
2. 线程将锁对象的 Mark Word 复制到 Displaced Mark Word
3. 线程尝试用 CAS 将锁的 Mark Word 替换为指向锁记录的指针
   - **成功**：当前线程获得锁
   - **失败**：说明存在竞争，线程尝试使用**自旋**来获取锁

**自旋机制**：

不断尝试去获取锁，一般用循环来实现。

```java
// 自旋获取锁的伪代码
while (!tryLock()) {
    // 继续循环尝试
}
```

**适应性自旋**：

- 如果线程自旋成功了，则下次自旋的次数会更多
- 如果自旋失败了，则自旋的次数会减少
- 自旋到一定程度仍未获取锁，线程会阻塞，锁升级为重量级锁

**释放锁**：

使用 CAS 操作将 Displaced Mark Word 的内容复制回锁的 Mark Word。

- **成功**：没有发生竞争，释放完成
- **失败**：说明锁已升级为重量级锁，释放锁并唤醒被阻塞的线程

#### （4）重量级锁状态

重量级锁是 `synchronized` 锁的**最终状态**，依赖于操作系统的**互斥量（Mutex）**来实现线程间的互斥。

**触发时机**：

- 轻量级锁自旋失败
- 锁竞争程度过高

**特点**：

| 特性               | 说明                                 |
| ------------------ | ------------------------------------ |
| **保证互斥** | 彻底实现线程间的互斥，避免 CPU 空转  |
| **适用场景** | 锁持有时间长、竞争激烈的场景         |
| **代价**     | 线程阻塞和唤醒需要上下文切换，开销大 |

**锁升级过程图**：

```
无锁 → 偏向锁 → 轻量级锁 → 重量级锁
```

**注意**：锁可以升级但不能降级（为了提高获得锁和释放锁的效率）。

---

### 2.2 按乐观/悲观划分

#### （1）乐观锁

**核心思想**：假设对共享资源的访问没有冲突，线程可以不停地执行，无需加锁也无需等待。一旦多个线程发生冲突，通常使用 **CAS** 技术来保证线程执行的安全性。

**特点**：

- ✅ 天生免疫死锁
- ✅ 无阻塞，性能好
- ❌ 可能出现 ABA 问题

**适用场景**：读多写少的环境

**示例**：

```java
import java.util.concurrent.atomic.AtomicInteger;

public class OptimisticLockDemo {
    private AtomicInteger count = new AtomicInteger(0);
  
    public void increment() {
        // 使用 CAS 实现乐观锁
        int oldValue, newValue;
        do {
            oldValue = count.get();
            newValue = oldValue + 1;
        } while (!count.compareAndSet(oldValue, newValue));
    }
  
    public int getCount() {
        return count.get();
    }
}
```

#### （2）悲观锁

**核心思想**：假设每次访问共享资源时都会发生冲突，所以必须对每次数据操作加上锁，以保证临界区的程序同一时间只能有一个线程在执行。

**特点**：

- ✅ 保证数据的一致性
- ❌ 可能发生死锁
- ❌ 性能开销大

**适用场景**：写多读少的环境

**示例**：

```java
public class PessimisticLockDemo {
    private int count = 0;
  
    public synchronized void increment() {
        // synchronized 就是典型的悲观锁
        count++;
    }
  
    public synchronized int getCount() {
        return count;
    }
}
```

**对比总结**：

| 特性           | 乐观锁         | 悲观锁             |
| -------------- | -------------- | ------------------ |
| **假设** | 认为冲突少     | 认为冲突多         |
| **实现** | CAS            | synchronized、Lock |
| **性能** | 读多写少性能好 | 写多读少性能好     |
| **死锁** | 不会死锁       | 可能死锁           |

---

### 2.3 按公平性划分

#### （1）公平锁

**定义**：多个线程按照申请锁的顺序来获取锁，先到先得。

**优点**：所有线程都能得到资源，不会饿死
**缺点**：吞吐量低，除了第一个线程，其他线程都会阻塞

**示例**：

```java
import java.util.concurrent.locks.ReentrantLock;

public class FairLockDemo {
    // 创建公平锁，参数 true 表示公平
    private ReentrantLock lock = new ReentrantLock(true);
  
    public void fairMethod() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " 获得锁");
        } finally {
            lock.unlock();
        }
    }
}
```

#### （2）非公平锁

**定义**：多个线程获取锁的顺序并不是按照申请锁的顺序，可能后申请的线程比先申请的线程优先获取锁。

**优点**：吞吐量高
**缺点**：可能导致某些线程永远无法获取锁（饥饿）

**示例**：

```java
import java.util.concurrent.locks.ReentrantLock;

public class NonFairLockDemo {
    // 创建非公平锁（默认）
    private ReentrantLock lock = new ReentrantLock(false);
  
    public void nonFairMethod() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " 获得锁");
        } finally {
            lock.unlock();
        }
    }
}
```

**注意**：

- `synchronized` 是非公平锁
- `ReentrantLock` 默认是非公平锁，但可以通过构造函数指定为公平锁

---

### 2.4 按可重入性划分

#### （1）可重入锁（递归锁）

**定义**：同一个线程在外层方法获取锁之后，在内层方法会自动获取锁。

**典型实现**：`synchronized`、`ReentrantLock`

**示例**：

```java
public class ReentrantLockDemo {
  
    public synchronized void method1() {
        System.out.println("method1 获得锁");
        method2(); // 可以直接调用，因为是可重入锁
    }
  
    public synchronized void method2() {
        System.out.println("method2 获得锁");
    }
  
    public static void main(String[] args) {
        ReentrantLockDemo demo = new ReentrantLockDemo();
        demo.method1();
    }
}
```

```java
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo2 {
    private ReentrantLock lock = new ReentrantLock();
  
    public void method1() {
        lock.lock();
        try {
            System.out.println("method1 获得锁，重入次数：" + lock.getHoldCount());
            method2();
        } finally {
            lock.unlock();
        }
    }
  
    public void method2() {
        lock.lock();
        try {
            System.out.println("method2 获得锁，重入次数：" + lock.getHoldCount());
        } finally {
            lock.unlock();
        }
    }
}
```

#### （2）非可重入锁

**定义**：线程获取锁之后，再次尝试获取锁会被阻塞。

**注意**：Java 中大部分锁都是可重入锁，非可重入锁需要自己实现。

---

### 2.5 按共享/独占划分

#### （1）独占锁（排他锁）

**定义**：该锁一次只能被一个线程持有。

**典型实现**：`synchronized`、`ReentrantLock`

#### （2）共享锁

**定义**：该锁可以被多个线程持有。

**典型实现**：`ReadWriteLock` 的读锁

**示例**：

```java
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SharedLockDemo {
    private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private int data = 0;
  
    // 读操作使用共享锁
    public void read() {
        rwLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " 读取数据：" + data);
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            rwLock.readLock().unlock();
        }
    }
  
    // 写操作使用独占锁
    public void write(int value) {
        rwLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " 写入数据：" + value);
            data = value;
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            rwLock.writeLock().unlock();
        }
    }
}
```

---

## CAS 机制详解

### 3.1 CAS 原理

**CAS（Compare And Swap，比较并交换）** 是一种无锁算法，它包含三个操作数：

- **V（内存位置）**：要更新的变量
- **E（预期值）**：预期的旧值
- **N（新值）**：要设置的新值

**执行过程**：

1. 比较 V 的值是否等于 E
2. 如果相等，将 V 的值设置为 N，返回成功
3. 如果不相等，说明有其他线程修改了 V，返回失败

**伪代码**：

```java
boolean compareAndSwap(V, E, N) {
    if (V == E) {
        V = N;
        return true;
    }
    return false;
}
```

**Java 示例**：

```java
import java.util.concurrent.atomic.AtomicInteger;

public class CASDemo {
    public static void main(String[] args) {
        AtomicInteger atomicInt = new AtomicInteger(5);
      
        // 期望值是 5，如果是，则更新为 10
        boolean success = atomicInt.compareAndSet(5, 10);
        System.out.println("更新成功：" + success); // true
        System.out.println("当前值：" + atomicInt.get()); // 10
      
        // 期望值是 5，但当前值是 10，更新失败
        success = atomicInt.compareAndSet(5, 20);
        System.out.println("更新成功：" + success); // false
        System.out.println("当前值：" + atomicInt.get()); // 10
    }
}
```

### 3.2 CAS 的问题

#### （1）ABA 问题

**问题描述**：

一个值原来是 A，变成了 B，又变回了 A。CAS 检查时发现值没有变化，但实际上已经被修改过了。

**示例**：

```
时间线：
T1: 线程1读取值 A
T2: 线程2将 A 改为 B
T3: 线程3将 B 改回 A
T4: 线程1使用 CAS 更新，发现值还是 A，更新成功
```

**解决方案**：使用版本号或时间戳

```java
import java.util.concurrent.atomic.AtomicStampedReference;

public class ABASolution {
    public static void main(String[] args) {
        // 初始值为 100，版本号为 1
        AtomicStampedReference<Integer> atomicRef = 
            new AtomicStampedReference<>(100, 1);
      
        int stamp = atomicRef.getStamp();
        Integer value = atomicRef.getReference();
      
        // 只有当值是 100 且版本号是 1 时才更新
        boolean success = atomicRef.compareAndSet(
            value, 101, stamp, stamp + 1
        );
      
        System.out.println("更新成功：" + success);
    }
}
```

#### （2）循环时间长开销大

自旋 CAS 如果长时间不成功，会给 CPU 带来非常大的执行开销。

#### （3）只能保证一个共享变量的原子操作

CAS 只能对单个变量进行操作。如果需要对多个变量进行操作，可以：

1. 使用锁
2. 封装成对象（如 `AtomicReference`）

### 3.3 atmoic类(CAS 在 Java 中的应用)

Java 中的原子类都是基于 CAS 实现的：

```java
import java.util.concurrent.atomic.*;

public class AtomicDemo {
    public static void main(String[] args) {
        // 原子整型
        AtomicInteger atomicInt = new AtomicInteger(0);
        atomicInt.incrementAndGet(); // i++
        atomicInt.getAndIncrement(); // ++i
      
        // 原子长整型
        AtomicLong atomicLong = new AtomicLong(0);
      
        // 原子布尔型
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
      
        // 原子引用
        AtomicReference<String> atomicRef = new AtomicReference<>("hello");
        atomicRef.compareAndSet("hello", "world");
      
        // 原子数组
        AtomicIntegerArray atomicArray = new AtomicIntegerArray(10);
        atomicArray.getAndIncrement(0);
    }
}
```

---

## Lock 接口与实现类

### 4.1 ReentrantLock

`ReentrantLock` 是 `Lock` 接口的实现类，是一个可重入的独占锁。

**与 synchronized 的区别**：

| 特性               | synchronized | ReentrantLock                  |
| ------------------ | ------------ | ------------------------------ |
| **锁的实现** | JVM 实现     | JDK 实现                       |
| **性能**     | 优化后差不多 | 优化后差不多                   |
| **功能**     | 简单         | 更丰富（可中断、超时、公平锁） |
| **释放锁**   | 自动释放     | 需要手动释放                   |

**基本使用**：

```java
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {
    private ReentrantLock lock = new ReentrantLock();
  
    public void method() {
        lock.lock(); // 加锁
        try {
            // 临界区代码
            System.out.println("执行业务逻辑");
        } finally {
            lock.unlock(); // 释放锁（必须在 finally 中）
        }
    }
  
    // 尝试获取锁，避免死锁
    public void tryLockMethod() {
        if (lock.tryLock()) {
            try {
                System.out.println("获取锁成功");
            } finally {
                lock.unlock();
            }
        } else {
            System.out.println("获取锁失败");
        }
    }
  
    // 支持中断
    public void lockInterruptiblyMethod() throws InterruptedException {
        lock.lockInterruptibly();
        try {
            System.out.println("执行业务逻辑");
        } finally {
            lock.unlock();
        }
    }
}
```

### 4.2 ReadWriteLock

**读写锁**允许多个读操作并发进行，但写操作是独占的。

**规则**：

- 读-读：可以并发
- 读-写：互斥
- 写-写：互斥

**示例**：

```java
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockDemo {
    private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private int data = 0;
  
    // 读操作
    public int read() {
        rwLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " 正在读取");
            Thread.sleep(1000);
            return data;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            rwLock.readLock().unlock();
        }
    }
  
    // 写操作
    public void write(int value) {
        rwLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " 正在写入");
            Thread.sleep(1000);
            data = value;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            rwLock.writeLock().unlock();
        }
    }
}
```

### 4.3 StampedLock

`StampedLock` 是 JDK 8 引入的新锁，性能比 `ReadWriteLock` 更好。

**三种模式**：

1. **写锁**：独占锁
2. **悲观读锁**：类似 ReadWriteLock 的读锁
3. **乐观读**：不加锁，通过版本号验证

**示例**：

```java
import java.util.concurrent.locks.StampedLock;

public class StampedLockDemo {
    private StampedLock lock = new StampedLock();
    private int data = 0;
  
    // 写操作
    public void write(int value) {
        long stamp = lock.writeLock();
        try {
            data = value;
        } finally {
            lock.unlockWrite(stamp);
        }
    }
  
    // 乐观读
    public int optimisticRead() {
        long stamp = lock.tryOptimisticRead();
        int currentData = data;
      
        // 验证版本号是否变化
        if (!lock.validate(stamp)) {
            // 版本号变化，升级为悲观读锁
            stamp = lock.readLock();
            try {
                currentData = data;
            } finally {
                lock.unlockRead(stamp);
            }
        }
        return currentData;
    }
  
    // 悲观读
    public int pessimisticRead() {
        long stamp = lock.readLock();
        try {
            return data;
        } finally {
            lock.unlockRead(stamp);
        }
    }
}
```

---

## 死锁问题

### 5.1 死锁的四个必要条件

1. **互斥条件**：资源不能被共享，只能由一个线程使用
2. **请求与保持条件**：线程已经持有至少一个资源，但又提出了新的资源请求
3. **不剥夺条件**：线程已获得的资源，在未使用完之前，不能被其他线程强行剥夺
4. **循环等待条件**：存在一种线程资源的循环等待链

### 5.2 死锁示例

```java
public class DeadLockDemo {
    private static Object lock1 = new Object();
    private static Object lock2 = new Object();
  
    public static void main(String[] args) {
        // 线程1：先获取 lock1，再获取 lock2
        Thread thread1 = new Thread(() -> {
            synchronized (lock1) {
                System.out.println("线程1获取lock1");
                try { Thread.sleep(100); } catch (InterruptedException e) {}
              
                synchronized (lock2) {
                    System.out.println("线程1获取lock2");
                }
            }
        });
      
        // 线程2：先获取 lock2，再获取 lock1
        Thread thread2 = new Thread(() -> {
            synchronized (lock2) {
                System.out.println("线程2获取lock2");
                try { Thread.sleep(100); } catch (InterruptedException e) {}
              
                synchronized (lock1) {
                    System.out.println("线程2获取lock1");
                }
            }
        });
      
        thread1.start();
        thread2.start();
        // 结果：两个线程互相等待，产生死锁
    }
}
```

### 5.3 如何避免死锁

1. **破坏互斥条件**：尽量使用无锁算法（CAS）
2. **破坏请求与保持条件**：一次性申请所有资源
3. **破坏不剥夺条件**：使用 `tryLock()` 尝试获取锁
4. **破坏循环等待条件**：按顺序申请资源

**改进示例**：

```java
public class DeadLockSolution {
    private static Object lock1 = new Object();
    private static Object lock2 = new Object();
  
    public static void main(String[] args) {
        // 两个线程都按相同顺序获取锁
        Thread thread1 = new Thread(() -> {
            synchronized (lock1) {
                System.out.println("线程1获取lock1");
                synchronized (lock2) {
                    System.out.println("线程1获取lock2");
                }
            }
        });
      
        Thread thread2 = new Thread(() -> {
            synchronized (lock1) { // 与线程1顺序一致
                System.out.println("线程2获取lock1");
                synchronized (lock2) {
                    System.out.println("线程2获取lock2");
                }
            }
        });
      
        thread1.start();
        thread2.start();
    }
}
```

---

## 锁优化建议

1. **减少锁的持有时间**：只在必要的代码段加锁
2. **减小锁的粒度**：将大锁拆分成小锁（如 `ConcurrentHashMap` 的分段锁）
3. **使用读写锁**：读多写少的场景使用 `ReadWriteLock`
4. **锁粗化**：如果频繁地加锁解锁，不如扩大锁的范围
5. **使用无锁数据结构**：如 `AtomicInteger`、`ConcurrentHashMap`
6. **避免锁的嵌套**：减少死锁的可能性

---

## 总结

### 锁的选择建议

| 场景                 | 推荐锁                             |
| -------------------- | ---------------------------------- |
| 简单的同步需求       | `synchronized`                   |
| 需要公平锁、可中断锁 | `ReentrantLock`                  |
| 读多写少             | `ReadWriteLock`、`StampedLock` |
| 高并发、无锁         | `AtomicXXX` 类                   |
| 集合类并发           | `ConcurrentHashMap` 等并发集合   |

### 核心要点

1. **synchronized** 是 JVM 层面的锁，会自动升级（偏向锁 → 轻量级锁 → 重量级锁）
2. **Lock** 是 JDK 层面的锁，功能更丰富，但需要手动释放
3. **CAS** 是无锁算法，性能好但有 ABA 问题
4. **死锁** 的预防要破坏四个必要条件之一
5. **锁优化** 要根据具体场景选择合适的锁类型

---

## 参考资料

- 《Java 并发编程的艺术》
- 《深入理解 Java 虚拟机》
- [Java Concurrency in Practice](https://jcip.net/)
