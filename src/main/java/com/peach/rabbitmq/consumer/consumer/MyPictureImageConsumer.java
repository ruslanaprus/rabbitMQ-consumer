package com.peach.rabbitmq.consumer.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.peach.rabbitmq.consumer.entity.Picture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MyPictureImageConsumer {

    private static final Logger log = LoggerFactory.getLogger(MyPictureImageConsumer.class);
    private ObjectMapper objectMapper;

    public MyPictureImageConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "q.mypicture.image")
    public void listen(String message){
        try {
            var picture = objectMapper.readValue(message, Picture.class);
            if (picture.getSize() > 9000) {
                throw new AmqpRejectAndDontRequeueException("Picture size too large: " + picture);
            }
            log.info("On image: {}", picture);
        } catch (JsonProcessingException | AmqpRejectAndDontRequeueException e) {
            throw new RuntimeException(e);
        }
    }
}