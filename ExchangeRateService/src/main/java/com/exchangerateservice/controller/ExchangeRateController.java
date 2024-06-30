package com.exchangerateservice.controller;

import com.exchangerateservice.model.ExchangeRate;
import com.exchangerateservice.repository.ExchangeRateRepository;
import com.exchangerateservice.service.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/exchange-rate")
public class ExchangeRateController {
    private final ExchangeRateService exchangeRateService;

    @Autowired
    public ExchangeRateController(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @GetMapping("/load")
    public ResponseEntity<String> loadExchangeRates(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            List<ExchangeRate> rates = exchangeRateService.fetchAndSaveExchangeRates(date);
            return ResponseEntity.ok("Data loaded successfully: " + rates.size() + " rates");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error loading data: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<ExchangeRate> getExchangeRate(@RequestParam("currencyCode") String currencyCode, @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        Optional<ExchangeRate> exchangeRate = exchangeRateService.getExchangeRateByDateAndCurrency(date, currencyCode);
        return exchangeRate.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }
}
