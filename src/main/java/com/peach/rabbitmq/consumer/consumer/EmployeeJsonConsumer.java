package com.peach.rabbitmq.consumer.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.peach.rabbitmq.consumer.entity.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class EmployeeJsonConsumer {

    private static final Logger log = LoggerFactory.getLogger(EmployeeJsonConsumer.class);
    private ObjectMapper objectMapper;

    public EmployeeJsonConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "course.employee")
    public void listen(String message){
        try {
            var employee = objectMapper.readValue(message, Employee.class);
            log.info("Employee is {}", employee);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}