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
    public String loadExchangeRates(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        if (date.isAfter(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Date cannot be in the future");
        }
        Integer rates = exchangeRateService.fetchAndSaveExchangeRates(date);
        return "Data loaded successfully: " + rates + " rates";
    }

    @GetMapping
    public ResponseEntity<ExchangeRate> getExchangeRate(@RequestParam("currencyCode") String currencyCode, @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        Optional<ExchangeRate> exchangeRate = exchangeRateService.getExchangeRateByDateAndCurrency(date, currencyCode);
        return exchangeRate.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }
}
