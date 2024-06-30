package com.exchangerateservice.controller;

import com.exchangerateservice.model.ExchangeRate;
import com.exchangerateservice.service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/exchange-rate")
@RequiredArgsConstructor
public class ExchangeRateController {
    private final ExchangeRateService exchangeRateService;

    @GetMapping("/load")
    public String loadExchangeRates(@RequestParam("date") LocalDate date) {
        try {
            Integer rates = exchangeRateService.fetchAndSaveExchangeRates(date);
            return "Data loaded successfully: " + rates + " rates";
        } catch (Exception e) {
            //return HttpStatus.REQUEST_TIMEOUT + "Error loading data: " + e;
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Error loading data: ", e);
        }
    }

    @GetMapping
    public ResponseEntity<ExchangeRate> getExchangeRate(@RequestParam("currencyCode") String currencyCode, @RequestParam("date") LocalDate date) {
        Optional<ExchangeRate> exchangeRate = exchangeRateService.getExchangeRateByDateAndCurrency(date, currencyCode);
        return exchangeRate.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }
}
