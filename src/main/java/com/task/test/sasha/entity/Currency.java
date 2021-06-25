package com.task.test.sasha.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@Document
public class Currency {

    @Id
    private int id;

    @Field("name")
    private String currencyName;

    private String usd;

    @Field("price_precision")
    private int pricePrecision;

    private double price;
}
