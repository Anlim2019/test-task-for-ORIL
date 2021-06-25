package com.task.test.sasha.repository;

import com.task.test.sasha.entity.Currency;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository
        extends MongoRepository<Currency, Integer> {
}
