package com.github.lan1tian.eslearn.cluster.multicast;


import java.util.concurrent.TimeUnit;

public class MulticastHostTest {

    public static void main(String[] args) throws InterruptedException {
        new MulticastHost(1,"230.0.0.1",30001).start();
        TimeUnit.SECONDS.sleep(100000);
    }

}