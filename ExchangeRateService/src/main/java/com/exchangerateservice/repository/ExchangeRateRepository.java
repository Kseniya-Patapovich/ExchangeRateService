package com.exchangerateservice.repository;

import com.exchangerateservice.model.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {
    Optional<ExchangeRate> findByDateAndCurrencyCode(LocalDate date, String currencyCode);

    List<ExchangeRate> findAllByDate(LocalDate date);
}
