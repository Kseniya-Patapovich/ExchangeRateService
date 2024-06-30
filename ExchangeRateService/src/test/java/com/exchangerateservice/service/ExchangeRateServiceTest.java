package com.exchangerateservice.service;

import com.exchangerateservice.model.ExchangeRate;
import com.exchangerateservice.repository.ExchangeRateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

public class ExchangeRateServiceTest {
    @Mock
    private ExchangeRateRepository exchangeRateRepository;

    @InjectMocks
    private ExchangeRateService exchangeRateService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetExchangeRateByDateAndCurrency() {
        LocalDate date = LocalDate.of(2023, 6, 28);
        String currencyCode = "USD";
        ExchangeRate rate = new ExchangeRate(1L, currencyCode, date, 2.5);
        when(exchangeRateRepository.findByDateAndCurrencyCode(date, currencyCode)).thenReturn(Optional.of(rate));

        Optional<ExchangeRate> foundRateOptional = exchangeRateService.getExchangeRateByDateAndCurrency(date, currencyCode);

        if (foundRateOptional.isPresent()) {
            ExchangeRate foundRate = foundRateOptional.get();
            assertThat(foundRate).isNotNull();
            assertThat(foundRate.getCurrencyCode()).isEqualTo(currencyCode);
            assertThat(foundRate.getDate()).isEqualTo(date);
            assertThat(foundRate.getRate()).isEqualTo(2.5);
            verify(exchangeRateRepository, times(1)).findByDateAndCurrencyCode(date, currencyCode);
        }
    }

    @Test
    public void testGetExchangeRateByDateAndCurrencyNotFound() {
        LocalDate date = LocalDate.of(2023, 6, 28);
        String currencyCode = "USD";
        when(exchangeRateRepository.findByDateAndCurrencyCode(date, currencyCode)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            exchangeRateService.getExchangeRateByDateAndCurrency(date, currencyCode);
        });

        assertThat(exception.getMessage()).isEqualTo("Exchange rate not found");
        verify(exchangeRateRepository, times(1)).findByDateAndCurrencyCode(date, currencyCode);
    }
}
