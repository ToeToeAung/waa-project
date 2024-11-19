package edu.miu.waa.online_market.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
@Setter
@Getter
@Entity
public class Logger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;
    private LocalDate date;
    private LocalTime time;
    private String principle;
    private String operation;

    public Logger(LocalDate date, LocalTime time, String principle, String operation) {
     this.date = date;
     this.time = time;
     this.principle = principle;
     this.operation = operation;
    }

    public Logger() {

    }


}
