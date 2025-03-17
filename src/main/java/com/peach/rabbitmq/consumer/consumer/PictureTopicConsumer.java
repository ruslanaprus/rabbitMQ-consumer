package com.peach.rabbitmq.consumer.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.peach.rabbitmq.consumer.entity.Picture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class PictureTopicConsumer {

    private static final Logger log = LoggerFactory.getLogger(PictureTopicConsumer.class);
    private ObjectMapper objectMapper;

    public PictureTopicConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = {"q.picture.image", "q.picture.vector", "q.picture.filter", "q.picture.log"})
    public void listen(Message message){
        try {
            var jsonString = new String(message.getBody());
            var picture = objectMapper.readValue(jsonString, Picture.class);
            log.info("Consuming: {} with routing key {}", picture, message.getMessageProperties().getReceivedRoutingKey());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}