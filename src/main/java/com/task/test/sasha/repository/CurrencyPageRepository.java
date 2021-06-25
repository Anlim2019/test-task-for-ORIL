package com.task.test.sasha.repository;

import org.springframework.stereotype.Repository;
import com.task.test.sasha.entity.Currency;
import org.springframework.data.repository.PagingAndSortingRepository;

@Repository
public interface CurrencyPageRepository extends PagingAndSortingRepository<Currency, Integer> {
}
