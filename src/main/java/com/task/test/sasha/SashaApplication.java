package com.task.test.sasha;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.test.sasha.entity.Currency;
import com.task.test.sasha.service.CurrencyService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@SpringBootApplication
public class SashaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SashaApplication.class, args);
    }


    @Bean
    @Scheduled(cron = "*/10 * * * *")
    public void runner(CurrencyService service) {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Currency>> typeReference = new TypeReference<>() {};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/json/currencyPair.json");
        try {
            List<Currency> currencies =
                    mapper.readValue(inputStream, typeReference).
                            stream().limit(10).toList();
            service.save(currencies);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
