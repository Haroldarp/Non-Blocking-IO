package com.api.api.services;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class DelayService {

    @Async
    public CompletableFuture<String> asyncDelay(int seconds) throws InterruptedException
    {
        Thread.sleep(seconds*100);
        return CompletableFuture.completedFuture("Done");
    }

    public String delay(int seconds) throws InterruptedException
    {
        Thread.sleep(seconds*100);
        return "Done";
    }

}
