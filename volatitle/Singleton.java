package volatitle;

public class Singleton {
    // 必须使用 volatile。
    // 1. 保证可见性：其他线程能看到 _instance 被初始化后的值。
    // 2. 保证有序性：防止指令重排序。如果没有 volatile，
    //    _instance = new Singleton(); 可能会被重排为：
    //    a. 分配内存空间
    //    b. _instance 指向内存空间 (此时 _instance != null 但对象未完全初始化)
    //    c. 初始化对象
    //    如果 b 在 c 之前发生，其他线程可能看到一个未完全初始化的对象。
    private static volatile Singleton _instance; 

    private Singleton() {}

    public static Singleton getInstance() {
        // 第一次检查：无需同步，高性能
        if (_instance == null) { 
            // 锁定：只在第一次初始化时需要同步
            // 对象锁定 Singleton 类
            synchronized (Singleton.class) {
                // 第二次检查：防止多个线程同时通过第一次检查
                if (_instance == null) {
                    _instance = new Singleton();
                }
            }
        }
        return _instance;
    }
}