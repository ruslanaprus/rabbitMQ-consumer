package com.peach.rabbitmq.consumer.consumer.retry.rabbitmq;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * Represents RabbitMQ Header, part x-death
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RabbitmqHeaderXDeath {
    private int count;
    private String exchange;
    private String queue;
    private String reason;
    private List<String> routingKeys;
    private Date time;
}
