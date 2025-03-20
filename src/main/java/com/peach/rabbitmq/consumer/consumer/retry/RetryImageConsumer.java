package com.peach.rabbitmq.consumer.consumer.retry;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.peach.rabbitmq.consumer.entity.Picture;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RetryImageConsumer {

    private static final Logger log = LoggerFactory.getLogger(RetryImageConsumer.class);
    private static final String DEAD_EXCHANGE_NAME = "x.guideline.dead";
    private DlxProcessingErrorHandler dlxProcessingErrorHandler;
    private ObjectMapper objectMapper;

    public RetryImageConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.dlxProcessingErrorHandler = new DlxProcessingErrorHandler(DEAD_EXCHANGE_NAME);
    }

    @RabbitListener(queues = "q.guideline.image.work")
    public void listen(Message message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag)
            throws InterruptedException, IOException, JsonParseException, JsonMappingException {
        try {
            var picture = objectMapper.readValue(message.getBody(), Picture.class);
            // process the image
            if (picture.getSize() > 9000) {
                // throw exception, we will use DLX handler for retry mechanism
                throw new IOException("Size too large");
            } else {
                log.info("Creating thumbnail & publishing : " + picture);
                // must acknowledge that message already processed
                channel.basicAck(deliveryTag, false);
            }
        } catch (IOException e) {
            log.warn("Error processing message : " + new String(message.getBody()) + " : " + e.getMessage());
            dlxProcessingErrorHandler.handleErrorProcessingMessage(message, channel, deliveryTag);
        }
    }
}