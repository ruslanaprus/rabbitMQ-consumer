package com.peach.rabbitmq.consumer.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Service
public class RabbitConsumer {
    private static final Logger log = LoggerFactory.getLogger(RabbitConsumer.class);

    @RabbitListener(queues = "course.fixedrate", concurrency = "3-7")
    public void consume(String message) throws InterruptedException {
        log.info("Thread {} consuming :{}", Thread.currentThread().getName(), message);
        TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextLong(2000));
    }
}
