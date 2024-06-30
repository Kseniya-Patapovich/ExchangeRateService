package com.exchangerateservice.repository;

import com.exchangerateservice.model.ExchangeRate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
public class ExchangeRateRepositoryTest {

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    @Test
    public void testFindByDateAndCurrencyCode() {
        LocalDate date = LocalDate.of(2023, 6, 28);
        String currencyCode = "USD";
        ExchangeRate rate = new ExchangeRate(null, currencyCode, date, 2.5);
        exchangeRateRepository.save(rate);

        Optional<ExchangeRate> foundRate = exchangeRateRepository.findByDateAndCurrencyCode(date, currencyCode);

        assertThat(foundRate).isPresent();
        assertThat(foundRate.get().getCurrencyCode()).isEqualTo(currencyCode);
        assertThat(foundRate.get().getDate()).isEqualTo(date);
        assertThat(foundRate.get().getRate()).isEqualTo(2.5);
    }

    @Test
    public void testFindAllByDate() {
        LocalDate date = LocalDate.of(2023, 6, 28);
        ExchangeRate rate1 = new ExchangeRate(null, "USD", date, 2.5);
        ExchangeRate rate2 = new ExchangeRate(null, "EUR", date, 3.0);
        exchangeRateRepository.saveAll(List.of(rate1, rate2));

        List<ExchangeRate> rates = exchangeRateRepository.findAllByDate(date);

        assertThat(rates).hasSize(2);
        assertThat(rates).extracting("currencyCode").containsExactlyInAnyOrder("USD", "EUR");
    }

    @Test
    public void testSaveExchangeRate() {
        LocalDate date = LocalDate.of(2023, 6, 28);
        ExchangeRate rate = new ExchangeRate(null, "USD", date, 2.5);

        ExchangeRate savedRate = exchangeRateRepository.save(rate);

        assertThat(savedRate.getId()).isNotNull();
        assertThat(savedRate.getCurrencyCode()).isEqualTo("USD");
        assertThat(savedRate.getDate()).isEqualTo(date);
        assertThat(savedRate.getRate()).isEqualTo(2.5);
    }

    @Test
    public void testDeleteExchangeRate() {
        LocalDate date = LocalDate.of(2023, 6, 28);
        ExchangeRate rate = new ExchangeRate(null, "USD", date, 2.5);
        ExchangeRate savedRate = exchangeRateRepository.save(rate);

        exchangeRateRepository.delete(savedRate);

        Optional<ExchangeRate> foundRate = exchangeRateRepository.findById(savedRate.getId());

        assertThat(foundRate).isEmpty();
    }
}
