package com.example.basic.currency;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockProducerConsumerExample {

    private final Queue<Integer> buffer = new LinkedList<>(); // 共享缓冲区
    private final int capacity = 10; // 缓冲区最大容量
    private final Lock lock = new ReentrantLock(); // 锁对象
    private final Condition notFull = lock.newCondition(); // 缓冲区未满的条件
    private final Condition notEmpty = lock.newCondition(); // 缓冲区非空的条件

    // 生产者方法：向缓冲区添加数据
    public void produce(int value) throws InterruptedException {
        lock.lock(); // 获取锁
        try {
            // 如果缓冲区已满，生产者等待
            while (buffer.size() == capacity) {
                System.out.println("缓冲区已满，生产者等待...");
                notFull.await(); // 等待"未满"条件
            }

            buffer.add(value);
            System.out.println("生产数据: " + value + "，缓冲区大小: " + buffer.size());

            // 唤醒等待"非空"条件的消费者
            notEmpty.signalAll();
        } finally {
            lock.unlock(); // 确保释放锁
        }
    }

    // 消费者方法：从缓冲区取出数据
    public int consume() throws InterruptedException {
        lock.lock();
        try {
            // 如果缓冲区为空，消费者等待
            while (buffer.isEmpty()) {
                System.out.println("缓冲区为空，消费者等待...");
                notEmpty.await(); // 等待"非空"条件
            }

            int value = buffer.poll();
            System.out.println("消费数据: " + value + "，缓冲区剩余: " + buffer.size());

            // 唤醒等待"未满"条件的生产者
            notFull.signalAll();
            return value;
        } finally {
            lock.unlock();
        }
    }

    // 测试代码
    public static void main(String[] args) {
        LockProducerConsumerExample example = new LockProducerConsumerExample();

        // 生产者线程
        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 20; i++) {
                    example.produce(i);
                    Thread.sleep(100); // 模拟生产耗时
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // 消费者线程
        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i < 20; i++) {
                    example.consume();
                    Thread.sleep(200); // 模拟消费耗时
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer.start();
    }
}
