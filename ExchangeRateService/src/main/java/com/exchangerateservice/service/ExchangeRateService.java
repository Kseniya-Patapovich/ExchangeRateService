package com.exchangerateservice.service;

import com.exchangerateservice.model.ExchangeRate;
import com.exchangerateservice.model.dto.ExchangeRateResponse;
import com.exchangerateservice.repository.ExchangeRateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExchangeRateService {
    private final RestTemplate restTemplate;
    private final ExchangeRateRepository exchangeRateRepository;

    public Integer fetchAndSaveExchangeRates(LocalDate date) {
        String url = String.format("https://www.nbrb.by/api/exrates/rates?ondate=%s&periodicity=0", date);
        ResponseEntity<ExchangeRateResponse[]> response = restTemplate.getForEntity(url, ExchangeRateResponse[].class);
        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            List<ExchangeRate> rates = Arrays.stream(response.getBody())
                    .map(this::mapToExchangeRate)
                    .collect(Collectors.toList());
            exchangeRateRepository.saveAll(rates);
            return rates.size();
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Failed to fetch data from NBRB");
        }
    }

    public Optional<ExchangeRate> getExchangeRateByDateAndCurrency(LocalDate date, String currencyCode) {
        return Optional.ofNullable(exchangeRateRepository.findByDateAndCurrencyCode(date, currencyCode)
                .orElseThrow(() -> new RuntimeException("Exchange rate not found")));
    }

    private ExchangeRate mapToExchangeRate(ExchangeRateResponse response) {
        ExchangeRate rate = new ExchangeRate();
        rate.setCurrencyCode(response.getCurAbbreviation());
        rate.setDate(response.getDate());
        rate.setRate(response.getCurOfficialRate());
        return rate;
    }
}
