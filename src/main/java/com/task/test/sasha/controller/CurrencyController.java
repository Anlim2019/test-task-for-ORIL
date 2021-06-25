package com.task.test.sasha.controller;

import com.task.test.sasha.entity.Currency;
import com.task.test.sasha.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


@RestController
public class CurrencyController {
    @Autowired
    private CurrencyService service;

    @GetMapping("/cryptocurrencies/minprice")
    public Currency minCurrency(@RequestParam String name) {
        return service.findLowestPriceByName(name);
    }

    @GetMapping("/cryptocurrencies/maxprice")
    public Currency maxPrice(@RequestParam String name) {
        return service.findHighestPriceByName(name);
    }

    @GetMapping("/cryptocurrencies")
    public List<Currency> findCurrenciesByPageAndSize (@RequestParam(required = false, defaultValue = "0") int page,
                                                       @RequestParam(required = false, defaultValue = "10") int size) {
        return service.findSelectedPageWithSelectedNumbers(page, size);
    }

    @GetMapping("/cryptocurrencies/csv")
    public void exportToCsv(HttpServletResponse response) {
        service.exportToCSV(response);
    }

}
