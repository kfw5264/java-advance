package com.kangfawei.item02;

import java.util.concurrent.locks.ReentrantLock;

public class ReadResource {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        lock.unlock();
    }
}
