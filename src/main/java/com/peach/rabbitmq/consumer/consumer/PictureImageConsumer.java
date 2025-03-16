package com.peach.rabbitmq.consumer.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.peach.rabbitmq.consumer.entity.Picture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class PictureImageConsumer {

    private static final Logger log = LoggerFactory.getLogger(PictureImageConsumer.class);
    private ObjectMapper objectMapper;

    public PictureImageConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "q.picture.image")
    public void listen(String message){
        try {
            var picture = objectMapper.readValue(message, Picture.class);
            log.info("On image: {}", picture);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}