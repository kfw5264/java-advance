### Java并发编程

#### 一、`synchronized`锁优化

- 锁细化：由于`synchronized`只允许一个线程进入，其他线程会进入等待状态，这样如果加锁的代码块中有很多不影响线程安全的逻辑代码的时候，效率就会变得很差，所以这种情况下就需要对锁进行细化，对于不影响线程安全的代码不用加锁。

- 锁粗化：在锁细化的情况中，可能会有一种情况，就是说如果在一段代码中，每个一段逻辑代码就有一段加锁的代码快，如果有加锁的细化代码块太多的时候也会影响一些效率，这种情况下可以酌情对锁的粒度进行粗化处理。

- 无锁优化(CAS)-Atomic类：

  `java.util.concurrent.atomic`包下的提供的一些列原子类。这些类可以保证多线程环境下，某个线程在执行atomic的方法时，不会被其他线程干扰。

  根据操作的数据类型，可以将atomic包下的类分为四类：

  1. 基本类型
  2. 数组类型
  3. 引用类型
  4. 对象属性修改类型

  基本原理用代码表示如下 : 

  ```
  CAS(value, expectedValue, newValue ) {
  	if(value == expectedValue) {
  		value = newValue;
  	} else {
  		fail or try again
  	}
  }
  ```

  - ABA问题：如果一个线程中修改的流程 `value--->newValue--->value`，这样另外一个线程获取值的时候获取到的value没有变化，依然可以执行操作，在一般情况下这种操作没有问题，但是有些特殊情况下会有一些问题产生。举个例子：A跟女朋友分手了，然后其女朋友经历了几任对象之后又指向了A，这时候尽管女朋友还是原来的女朋友，但是性质就有了很大的变化，这就是ABA问题。ABA问题可以通过增加版本号解决

    ```
    A: v1.0
    B: v2.0
    A: V3.0
    ```

    

#### 二、递增几种实现方法

1. `synchronized`:锁升级

2. `AtomicInteger`:CAS

3. `LongAdder`:分段锁

   下方代码为三种方式递增效率测试：

   ```java
   public class IncreaseDemo {
   
       public static void main(String[] args) throws InterruptedException {
           ThreeMethodIncreaseDemo demo = new ThreeMethodIncreaseDemo();
           List<Thread> syncThreads = new ArrayList<>();
           List<Thread> atomicThreads = new ArrayList<>();
           List<Thread> adderThreads = new ArrayList<>();
           for (int i = 0; i < 10000; i++) {
               // lambda表达式写法，等同于：
               /*  syncThreads.add(new Thread(new Runnable() {
                       @Override
                       public void run() {
                           demo.syncCount
                       }
                   })) */
               syncThreads.add(new Thread(demo :: increaseWithSync));
               atomicThreads.add(new Thread(demo :: increaseWithAtomic));
               adderThreads.add(new Thread(demo :: increaseWithLongAdder));
           }
   
           long syncTime = testTime(syncThreads);
           long atomicTime = testTime(atomicThreads);
           long adderTime = testTime(adderThreads);
   
           System.out.println("sync:" + syncTime + "-" + demo.syncCount);
           System.out.println("atomic:" + atomicTime + "-" + demo.atomicCount);
           System.out.println("adder:" + adderTime + "-" + demo.adderCount);
       }
   
       private static Long testTime(List<Thread> threads) throws InterruptedException {
           long start = System.currentTimeMillis();
           for (Thread thread : threads) {
               thread.start();
           }
           for (Thread thread : threads) {
               thread.join();
           }
           return System.currentTimeMillis() - start;
       }
   
   }
   
   class ThreeMethodIncreaseDemo {
       Integer syncCount = 0;
       AtomicInteger atomicCount = new AtomicInteger(0);
       LongAdder adderCount = new LongAdder();
   
       public void increaseWithSync() {
           for (int i = 0; i < 10000; i++) {
               synchronized (this) {
                   syncCount++;
               }
           }
       }
   
       public void increaseWithAtomic() {
           for (int i = 0; i < 10000; i++) {
               atomicCount.incrementAndGet();
           }
       }
   
       public void increaseWithLongAdder() {
           for (int i = 0; i < 10000; i++) {
               adderCount.increment();
           }
       }
   
   }
   ```

   最终测试结果为：

   ```console
   sync:7215-100000000
   atomic:4199-100000000
   adder:1086-100000000
   ```

   在上面测试中，不同数量级的情况下会有不同的结果，并发量比较小的情况下三种效率方面其实并没有什么大的区别，甚至有时候`synchronzied`的效率要高于另外两种方式。但是在并发量逐渐增大的情况下后两种方式的效率明显会高于`synchronzied`。在并发量很大的情况下，`LongAdder`的效率会比较高。

#### 三、 JUC包的一些类

1. `ReentrantLock`：
2. `CountDownLatch`：
3. `CyclicBarrier`:
4. `Phaser`:
5. `ReadWriteLock`:
6. `Semaphore`:
7. `Exchanger`: