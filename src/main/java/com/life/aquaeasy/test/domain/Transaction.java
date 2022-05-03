package com.life.aquaeasy.test.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@Builder
@Document(collection = "transaction")
public class Transaction implements Serializable {

    @Id
    private String id;

    private LocalDate date;

    private BigDecimal amount;

    private Category category;
}
