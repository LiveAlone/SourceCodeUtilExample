package org.yqj.boot.demo.alibaba.transmittablethreadlocal;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.TtlRunnable;
import com.alibaba.ttl.threadpool.TtlExecutors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Description:
 *
 * @author yaoqijun
 * @date 2019-03-13
 * Email: yaoqijunmail@foxmail.com
 */
public class TransmittableMain {

    public static void main(String[] args) throws Exception {
        runnableDecorateCondition2();
//        inheritableThreadLocalTest();
//        runnableDecorateCondition();
    }

    public static void runnableDecorateCondition2() throws Exception{

        ThreadLocal<String> parent = new ThreadLocal<>();
        parent.set("value-set-in-parent");

        ExecutorService executorService = TtlExecutors.getTtlExecutorService(Executors.newCachedThreadPool());

        executorService.submit(new PrintThreadInfoRunnable(parent));
    }

    public static void runnableDecorateCondition() throws Exception{
        TransmittableThreadLocal<String> parent = new TransmittableThreadLocal<>();
        parent.set("value-set-in-parent");

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(TtlRunnable.get(new PrintThreadInfoRunnable(parent)));

    }

    public static void inheritableThreadLocalTest() throws Exception{
        TransmittableThreadLocal<String> stringThreadLocal = new TransmittableThreadLocal<>();
        stringThreadLocal.set("hello world");
        Thread thread = new Thread(new PrintThreadInfoRunnable(stringThreadLocal));
        thread.start();
        thread.join();
    }

    public static class PrintThreadInfoRunnable implements Runnable{

        public ThreadLocal<String> stringThreadLocal;

        public PrintThreadInfoRunnable(ThreadLocal<String> stringThreadLocal) {
            this.stringThreadLocal = stringThreadLocal;
        }

        @Override
        public void run() {
            System.out.println("******* start run");

            System.out.println(stringThreadLocal.get());

            System.out.println("******* end run");
        }
    }
}
