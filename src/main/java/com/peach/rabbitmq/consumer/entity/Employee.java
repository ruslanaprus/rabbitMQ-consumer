package com.peach.rabbitmq.consumer.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private String id;
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
}
