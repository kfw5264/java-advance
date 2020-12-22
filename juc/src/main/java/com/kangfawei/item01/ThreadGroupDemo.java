package com.kangfawei.item01;

/**
 * @author muaishenqiu
 */
public class ThreadGroupDemo implements Runnable {

    public static void main(String[] args) {
        ThreadGroup group = new ThreadGroup("printGroup");

        Thread t1 = new Thread(group, new ThreadGroupDemo(), "T1");
        Thread t2 = new Thread(group, new ThreadGroupDemo(), "T2");

        t1.start();
        t2.start();

        System.out.println(group.activeCount());
        group.list();
    }

    @Override
    public void run() {
        String groupAndName = Thread.currentThread().getThreadGroup().getName() + "--"
            + Thread.currentThread().getName();

        while(true) {
            System.out.println(groupAndName);

            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
