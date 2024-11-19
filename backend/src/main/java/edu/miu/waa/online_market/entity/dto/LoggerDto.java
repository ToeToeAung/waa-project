package edu.miu.waa.online_market.entity.dto;

import lombok.Data;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class LoggerDto {
    private Long transactionId;
    private LocalDate date;
    private LocalTime time;
    private String principle;
    private String operation;
}
