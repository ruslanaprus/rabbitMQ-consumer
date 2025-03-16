package com.peach.rabbitmq.consumer.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.peach.rabbitmq.consumer.entity.Picture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class PictureVectorConsumer {

    private static final Logger log = LoggerFactory.getLogger(PictureVectorConsumer.class);
    private ObjectMapper objectMapper;

    public PictureVectorConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "q.picture.vector")
    public void listen(String message){
        try {
            var picture = objectMapper.readValue(message, Picture.class);
            log.info("On vector: {}", picture);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}