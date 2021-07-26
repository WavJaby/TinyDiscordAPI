package com.wavjaby.discord.httpsender;

import java.util.LinkedList;
import java.util.Queue;

public class HTTPSender {
    Queue<Runnable> queue = new LinkedList<>();

    public void queue(){
        queue.offer(()->{});
        queue.poll().run();
    }
}
