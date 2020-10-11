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

##### `ReentrantLock`

- 可重入锁，同`synchronized`一样，同一个线程可以多次获取同一把锁。

  ```java
  public class ReentrantLockDemo {
      public static void main(String[] args) throws InterruptedException {
          ReentrantLockInstance instance = new ReentrantLockInstance();
          List<Thread> threads = new ArrayList<>();
          for (int i = 0; i < 10000; i++) {
              threads.add(new Thread(instance :: increase));
          }
  
          for (Thread thread : threads) {
              thread.start();
          }
          for (Thread thread : threads) {
              thread.join();
          }
  
          System.out.println(instance.count);
      }
  }
  
  class ReentrantLockInstance {
      Integer count = 0;
      ReentrantLock lock = new ReentrantLock();
      public void increase () {
          for (int i = 0; i < 1000; i++) {
              try {
                  lock.lock();
                  count++;
              } finally {
                  lock.unlock();
              }
          }
          try {
              Thread.sleep(100);
          } catch (InterruptedException e) {
              e.printStackTrace();
          }
      }
  }
  
  ```
  
  **注意：**synchronized是Java语言层面提供的锁，所以不需要考虑异常。而`ReentrantLock`是Java代码实现的锁，所以需要考虑产生异常的情况，所以应该在finally中释放锁。
  
- `ReentrantLock`可以尝试获取锁，如果获取不到可以转而做其他的事：

  ```java
  try {
      if(lock.tryLock(1, TimeUnit.SECONDS)) {
          try {
              count2 ++;
              TimeUnit.MILLISECONDS.sleep(200);
              System.out.println(Thread.currentThread().getName() + "--" + count2);
              System.out.println("===============================");
          } finally {
              lock.unlock();
          }
      } else {
          System.out.println(Thread.currentThread().getName() + "没有获取到锁......");
      }
  } catch (InterruptedException e) {
      e.printStackTrace();
  }
  ```

  

- `ReentrantLock`可以选择公平锁跟非公平锁，而`synchronized`只能是非公平锁

  ```
  public ReentrantLock() {
      sync = new NonfairSync();
  }
  
  public ReentrantLock(boolean fair) {
      sync = fair ? new FairSync() : new NonfairSync();
  }
  
  ```

  公平锁是多个线程申请同一把锁时，必须按照申请时间来依次获得锁，而非公平锁则是谁抢到锁归谁。

  `ReentrantLock`默认情况下为非公平锁，可以通过构造方法传入一个`true`设置为公平锁。

  正常情况下，非公平锁的效率要高于公平锁。

- 使用`synchronized`获取锁的时，除非获取到锁，否则将会一直等待下去。使用`ReentrantLock`实现锁时，可以使用`lockInterruptibly()`方法 响应中断的获取锁。

  ```java
  public class ReentrantLockInterrupt {
      public static void main(String[] args) {
          Lock lock1 = new ReentrantLock();
          Lock lock2 = new ReentrantLock();
  
          Thread t1 = new Thread(() -> {
              try {
                  lock1.lock();
                  System.out.println(Thread.currentThread().getName() + "获取到lock1");
                  TimeUnit.SECONDS.sleep(1);
                  lock2.lockInterruptibly();
                  System.out.println(Thread.currentThread().getName() + "获取到lock2");
              } catch (InterruptedException e) {
                  e.printStackTrace();
              } finally {
                  lock1.unlock();
                  System.out.println(Thread.currentThread().getName() + "解锁lock1");
                  lock2.unlock();
                  System.out.println(Thread.currentThread().getName() + "解锁lock2");
              }
          }, "t1");
  
          Thread t2 = new Thread(() -> {
              try {
                  lock2.lock();
                  System.out.println(Thread.currentThread().getName() + "获取到lock2");
                  TimeUnit.SECONDS.sleep(1);
                  lock1.lockInterruptibly();
                  System.out.println(Thread.currentThread().getName() + "获取到lock1");
              } catch (InterruptedException e) {
                  e.printStackTrace();
              } finally {
                  lock2.unlock();
                  System.out.println(Thread.currentThread().getName() + "解锁lock2");
                  lock1.unlock();
                  System.out.println(Thread.currentThread().getName() + "解锁lock1");
              }
          }, "t2");
  
          t1.start();
          t2.start();
  
          try {
              TimeUnit.SECONDS.sleep(1);
          } catch (InterruptedException e) {
              e.printStackTrace();
          }
  
          t1.interrupt();
      }
  }
  
  ```

  

##### `CountDownLatch`

##### `CyclicBarrier`

##### `Phaser`

##### `ReadWriteLock`

##### `Semaphore`

##### `Exchanger`