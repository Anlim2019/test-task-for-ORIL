package com.task.test.sasha.service;

import com.task.test.sasha.entity.Currency;
import com.task.test.sasha.exception.NoMatchesException;
import com.task.test.sasha.repository.CurrencyPageRepository;
import com.task.test.sasha.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class CurrencyService {
    @Autowired
    private CurrencyRepository repository;

    @Autowired
    private CurrencyPageRepository pageRepository;

    @Autowired
    private MongoTemplate mongoTemplate;


    public List<Currency> save(List<Currency> currency) {
        return repository.saveAll(currency);
    }
    public Currency findLowestPriceByName(String name) {
        if(repository.findAll().stream().noneMatch(Predicate.isEqual(name))) {
            throw new NoMatchesException("Currency with name: " + name + " doesn't exist");
        }
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name).
                and("price").is(repository.findAll().
                stream().min
                (Comparator.comparing(Currency::getPrice))));

        return mongoTemplate.findOne(query, Currency.class);
    }

    public Currency findHighestPriceByName(String name) {
        if(repository.findAll().stream().noneMatch(Predicate.isEqual(name))) {
            throw new NoMatchesException("Currency with name: " + name + " doesn't exist");
        }
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name).
                and("price").is(repository.findAll().
                        stream().max
                        (Comparator.comparing(Currency::getPrice))));

        return mongoTemplate.findOne(query, Currency.class);
    }

    public List<Currency> findSelectedPageWithSelectedNumbers(int page, int size) {
        return pageRepository.findAll(PageRequest.of(page, size)).
                stream().sorted(Comparator.comparingDouble(Currency::getPrice)).
                collect(Collectors.toList());
    }

    public void exportToCSV(HttpServletResponse response) {
        response.setContentType("csv");
        String headerKey = "headerKey";
        String headerValue = "export.csv";
        response.setHeader(headerKey, headerValue);

        try {
            ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

            String[] currencyExport = {"name", "min_price", "max_price"};

            for(Currency currency : repository.findAll()) {
                csvWriter.write(currency, currencyExport);
            }
        } catch (IOException exception) {
            exception.getStackTrace();
        }
    }

}
