/*
 * @lc app=leetcode.cn id=1116 lang=java
 *
 * [1116] 打印零与奇偶数
 */

// @lc code=start
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntConsumer;

/**
 * 使用AtomicInteger作为状态标志，Thread.yield()让出CPU资源
 * 0 - 打印0
 * 1 - 打印奇数
 * 2 - 打印偶数
 */
class ZeroEvenOdd {
    private int n;
    private AtomicInteger ai = new AtomicInteger(0);

    public ZeroEvenOdd(int n) {
        this.n = n;
    }

    public void zero(IntConsumer printNumber) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            while (ai.get() != 0 && ai.get() != 2) {
                Thread.yield();
            }
            printNumber.accept(0);
            ai.incrementAndGet();
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        for (int i = 2; i <= n; i += 2) {
            while (ai.get() != 3) {
                Thread.yield();
            }
            printNumber.accept(i);
            ai.set(0);
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i+=2) {
            while (ai.get() != 1) {
                Thread.yield();
            }
            printNumber.accept(i);
            ai.set(2);
        }
    }
}
// @lc code=end

