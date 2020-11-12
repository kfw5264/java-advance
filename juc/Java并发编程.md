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

>  `CountDownLatch`相当与门闩的作用，相当于等门口聚集一定数量的人开门放行，一个线程等待其他线程完成之后才能向下执行。一般是用于主线程等待其他线程执行完成之后再执行。例如：一项检查需要采集100个样本，如果不够就需要等待，只要100个样本采集够了才能进行检测。

- `CountDownLatch`主要方法：

  1. `countDown()`：顾名思义，这个方法是一个计数器，每次执行计数器减1，计数器的值到0的时候放行。（同一个线程可以多次执行，每次执行计数器减1）
  2. `await()`：该方法使得线程进入等待状态，等待计数器为0时放行。（多个线程可以同时执行，如果同时执行，哪个这几个线程进入等待状态， 并且以共享的形式享有同一把锁。）

  ```java
  public class CountDownLatchDemo {
      public static void main(String[] args) {
          CountDownLatch latch = new CountDownLatch(100000);
          MyThread myThread = new MyThread(latch);
  
          for (int i = 0; i < 100000; i++) {
              new Thread(myThread::serviceWithLatch, "Thread" + i).start();
          }
  
          try {
              System.out.println("main await");
              latch.await();
              System.out.println("main finally");
              System.out.println("Latch--" + myThread.count);
          } catch (InterruptedException e) {
              e.printStackTrace();
          }
  
          MyThread myThread2 = new MyThread(latch);
          for (int i = 0; i < 100000; i++) {
              new Thread(myThread2::serviceNoLatch, "Thread" + i).start();
          }
          System.out.println("NoLatch--" + myThread2.count);
      }
  }
  
  class MyThread {
      private final CountDownLatch latch;
      public int count;
  
      public MyThread (CountDownLatch latch) {
          this.latch = latch;
      }
  
      public void serviceWithLatch() {
          try {
              synchronized (this) {
                  count++;
              }
          } finally {
              latch.countDown();
          }
      }
  
      public void serviceNoLatch() {
          synchronized (this) {
              count++;
          }
      }
  }
  
  
  /*
   *output:
   *  main await
   *  main finally
   *  Latch--100000
   *  NoLatch--99881
   */
  ```


##### `CyclicBarrier`

> 回环屏障。一组线程执行的时候，会被堵在某一个屏障前面，当最后一个线程到达这个屏障的时候，屏障放开，所有阻塞的线程才会执行。多个线程互相等待，知道所有线程完成才继续向下执行。该屏障可以循环使用。例如：英雄联盟一场比赛开始时，先加载完成的玩家需要等待所有玩家加载完成才可以进入游戏。

```java
public class CyclicBarrierForLOL {
    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(10);
        Random random = new Random();

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                JUCUtil.sleep(TimeUnit.MILLISECONDS, random.nextInt(5000));
                System.out.println("红方选手————" + Thread.currentThread().getName() + "加载完成");

                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("红方选手————" + Thread.currentThread().getName() + "欢迎来到英雄联盟，敌军还有30s进入战场。");
                }
            }, "t" + i).start();
        }

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                JUCUtil.sleep(TimeUnit.MILLISECONDS, random.nextInt(5000));
                System.out.println("蓝方选手————" + Thread.currentThread().getName() + "加载完成");

                try { 
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("蓝方选手————" + Thread.currentThread().getName() + "欢迎来到英雄联盟，敌军还有30s进入战场。");
                }
            }, "t" + (i +5)).start();
        }
    }
}
```



##### `Phaser` 

> `Phaser`是JDK 7新增的一个同步辅助类,可以实现类似`CyclicBarrier`和`CountDownLatch`类似的功能。
>
> `Phaser`支持任务动态调整,支持分层结构达到更大的吞吐量。
>
> `Phaser`类似`CyclicBarrier`和`CountDownLatch`，通过计数器来控制程序的顺序执行。`Phaser`中计数器叫做`parties`，可以通过`Phaser`的构造函数或者`register()`方法来注册。如果要取消注册，则需要调用`arriveAndDeregister()`方法。

```java
public class PhaserDemo {

    public static void main(String[] args) {
        Phaser phaser = new MyPhaser(7);
//        phaser.bulkRegister(7);
        for (int i = 0; i < 5; i++) {
            new Thread(new Person("p" + i, phaser)).start();
        }
        new Thread(new Person("新郎", phaser)).start();
        new Thread(new Person("新娘", phaser)).start();
    }

    static void milliSleep(int milli) {
        try {
            TimeUnit.MILLISECONDS.sleep(milli);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class MyPhaser extends Phaser {
        protected MyPhaser(int parties) {
            super(parties);
        }

        @Override
        protected boolean onAdvance(int phase, int registeredParties) {
            switch (phase) {
                case 0:
                    System.out.println("所有人都到齐了" + registeredParties);
                    System.out.println("--------------------------------------------");
                    return false;
                case 1:
                    System.out.println("所有人都吃完了" + registeredParties);
                    System.out.println("--------------------------------------------");
                    return false;
                case 2:
                    System.out.println("所有人都离开了" + registeredParties);
                    System.out.println("--------------------------------------------");
                    return false;
                case 3:
                    System.out.println("婚礼结束， 新郎新娘入洞房！" + registeredParties);
                    return false;
                default:
                    throw new IllegalStateException("Unexpected value: " + phase);
            }
        }
    }

    static class Person implements Runnable {
        private String name;
        private Phaser phaser;

        public Person(String name, Phaser phaser) {
            this.name = name;
            this.phaser = phaser;
        }

        private void arrive() {
            milliSleep(100);
            System.out.println(name + "到达现场");
            phaser.arriveAndAwaitAdvance();
        }

        private void eat() {
            milliSleep(100);
            System.out.println(name + "吃完了");
            phaser.arriveAndAwaitAdvance();
        }

        private void leave() {
            milliSleep(100);
            System.out.println(name + "酒足饭饱，离开了婚礼现场！");
            phaser.arriveAndAwaitAdvance();
        }

        private void hug() {
            milliSleep(100);
            if(name.equals("新郎") || name.equals("新娘")) {
                System.out.println(name + "入洞房");
            } else {
                phaser.arriveAndDeregister();
            }
        }

        @Override
        public void run() {
            arrive();
            eat();
            leave();
            hug();
        }
    }



}

```



##### `ReadWriteLock`

在实际使用过程中，由于读的过程不会更改临界区的数据，所以往往可以允许多个线程同时访问。写过程由于更改临界区数据，多个线程同时访问会导致最终数据不一致。所以实际使用中，我们往往需要允许多个线程同时读，但只要有一个线程写其他线程必须等待，使用`ReadWriteLock`就可以解决这个问题。

- 使用`ReadWriteLock`可以提高效率
- `ReadWriteLock`只允许一个线程写入
- `ReadWriteLock`允许多个线程读取
- `ReadWriteLock`适合读多写少的场景 

```java
public class ReadWriteLockDemo {
    public static void main(String[] args) {
        Counter counter = new Counter();
        for (int i = 0; i < 10; i++) {
            new Thread(counter :: increase).start();
        }
        for (int i = 0; i < 100; i++) {
            new Thread(counter :: get).start();
        }

    }



    private static final class Counter {
        private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
        private final Lock rLock = rwLock.readLock();
        private final Lock wLock = rwLock.writeLock();
        private int count = 0;

        public void increase() {
            wLock.lock();
            try {
                count++;
                System.out.println("count增加");
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } finally {
                wLock.unlock();

            }


        }

        public void get() {
            rLock.lock();
            try{
                System.out.println(count);
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } 
            } finally {
              rLock.unlock();
            }
        }
    }
}

```



##### `Semaphore`

> `Semaphore`字面意思是信号量，信号标。在Java中主要是控制同一时间对于资源的访问次数。
>
> `Semaphore`在构造器中传入一个`int`类型的整数，表示同一时间最多访问资源的线程。
>
> `acquire()`：一个线程调用该方法的时候，如果成功则信号量-1，否则需要等待有线程释放该资源
>
> `release()`：线程调用该方法释放资源，释放后信号量+1。释放后会唤醒一个等待的线程。

```java
/**
 * Semaphore示例  抢车位
 * @author kangfawei
 */
public class SemaphoreDemo {
    
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);  // 只有三个车位

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "占用一个车位");

                    TimeUnit.SECONDS.sleep(2);

                    System.out.println(Thread.currentThread().getName() + "离开车位，剩余车位+1");
                    semaphore.release();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "t"+i).start();
        }
    }
}
```



##### `Exchanger`

> `Exchanger`用于两个线程之间交换数据。一个线程调用`exchange()`之后会等待另外一个线程调用`exchange()`方法。

```java
public class ExchangerDemo {
    public static void main(String[] args) {
        Exchanger<String> exchanger = new Exchanger<>();

        new Thread(() -> {
            String str = "This is T1's data";
            System.out.println(Thread.currentThread().getName() + "---" + str);
            try {
                str = exchanger.exchange(str);
                System.out.println(Thread.currentThread().getName() + "---" + str);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1").start();

        new Thread(() -> {
            String str = "This is T2's data";
            System.out.println(Thread.currentThread().getName() + "---" + str);
            try {
                str = exchanger.exchange(str);
                System.out.println(Thread.currentThread().getName() + "---" + str);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t2").start();
    }
}
```



##### `LockSupport`

> `LockSupport`用来创建锁或其他同步类的基本线程阻塞原语。
>
> 调用`park()`方法时，当前线程阻塞，直到获得许可
>
> 调用`unpark()`方法时，给一个线程许可让其继续运行下去
>
> 如果先调用`unpark()`之后再调用`park()`方法，线程将会一直运行下去，不会等待。

```java
public class LockSupportDemo {

    public static void main(String[] args) {
        
        Thread t1 = new Thread(() -> {
            System.out.println("t1锁定.....");
            LockSupport.park();
            System.out.println("t1解锁");
        });

        // 验证先解锁再锁定线程不会等待而是直接往下进行
        Thread t2 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                System.out.println("t2锁定");
                LockSupport.park();
                System.out.println("t2已经解锁");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t3 = new Thread(() -> {
            try {
                LockSupport.unpark(t2);
                System.out.println("已经解锁t2");
                System.out.println("t3两秒后解锁t1");
                TimeUnit.SECONDS.sleep(2);
                LockSupport.unpark(t1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();
        t3.start();
    }
}
```



##### 面试题

1. 实现一个容器，提供两个方法`add()`和`size()`，写两个线程，线程以添加10个元素到容器中，线程2实现监控元素的个数，当格式到5个时，线程2给出提示并结束。
   - 使用`wait()` 和`notify()`实现，详见`com.kangfawei.item02.Q1_waitAndNotify.java`
   - 使用`CountDownLatch`实现，详见`com.kangfawei.item02.Q1_countDownLatch.java`
   - 使用`LockSupport`实现，详见`com.kangfawei.item02.Q1_LockSupport.java`
2. 写一个固定容量的同步容器，拥有`put()`和`get()`方法，以及`getCount()`方法，能够支持两个生产者线程以及10个消费者线程的阻塞使用。
   - 使用`wait()` 和`notify()`实现，详见`com.kangfawei.item02.Q2_waitAndNotify.java`
   - 使用`ReentrantLock`实现，详见`com.kangfawei.item02.Q1_reentrantLock.java`
3. 有两个线程，一个打印1~26的数字，另外一个打印 A-Z的大写字母，实现打印效果1A2B3C......25Y26Z

   - 使用`wait()`和`notify()`实现，详见`com.kangfawei.item02.Q3_waitAndNotify.java`

#### 四、 AQS(`AbstractQueuedSynchronizer`)

> AQS是一个用来构建锁和同步器的框架。

<img src="images\AQS.png" alt="AQS" style="zoom:80%;" />

#### 五、`ThreadLocal`

#### 六、不同引用类型（强软弱虚）在垃圾回收时的表现x

