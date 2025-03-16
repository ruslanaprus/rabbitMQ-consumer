package com.peach.rabbitmq.consumer.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Picture {
    private String name;
    private String type;
    private String source;
    private Long size;
}
