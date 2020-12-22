package com.kangfawei.item01;

/**
 * @author muaishenqiu
 * 使用stop()方法停止线程，导致数据不一致
 */
public class StopThreadUnsafe {
    public static User user = new User();
    private static class User {
        private int id;
        private String name;

        public User() {
            id = 0;
            name = "0";
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    private static class ChangeObjectClass extends Thread {
        @Override
        public void run() {
            while(true) {
                synchronized (user) {
                    int v = (int) System.currentTimeMillis()/1000;
                    user.setId(v);
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    user.setName(String.valueOf(v));
                }
                Thread.yield();
            }

        }
    }

    private static class ReadObjectClass extends Thread {
        @Override
        public void run() {
            while(true) {
                synchronized (user) {
                    if(user.getId()!= Integer.parseInt(user.getName())) {
                        System.out.println(user.toString());
                    }
                }
                Thread.yield();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new ReadObjectClass().start();
        while(true) {
            Thread thread = new ChangeObjectClass();
            thread.start();

            Thread.sleep(100);
            // 使用stop结束线程会导致数据不一致
            thread.stop();
        }
    }
}
