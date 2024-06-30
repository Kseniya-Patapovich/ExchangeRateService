package com.exchangerateservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "exchange-rate")
public class ExchangeRate {
    @Id
    @SequenceGenerator(name = "rateSeqGen", sequenceName = "exchange_rate_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "rateSeqGen")
    private Long id;

    @Column(name = "currency_code")
    private String currencyCode;

    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private LocalDate date;

    @Column(name = "rate")
    private Double rate;
}
