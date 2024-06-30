package com.exchangerateservice.controller;

import com.exchangerateservice.model.ExchangeRate;
import com.exchangerateservice.service.ExchangeRateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ExchangeRateController.class)
public class ExchangeRateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExchangeRateService exchangeRateService;

    @Test
    public void testLoadExchangeRatesWithError() throws Exception {
        LocalDate date = LocalDate.of(2023, 6, 28);
        when(exchangeRateService.fetchAndSaveExchangeRates(date)).thenThrow(new RuntimeException("Error"));

        mockMvc.perform(get("/exchange-rate/load")
                        .param("date", "2023-06-28")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Error loading data: Error"));

        verify(exchangeRateService, times(1)).fetchAndSaveExchangeRates(date);
    }

    @Test
    public void testGetExchangeRate() throws Exception {
        LocalDate date = LocalDate.of(2023, 6, 28);
        String currencyCode = "USD";
        ExchangeRate exchangeRate = new ExchangeRate(1L, currencyCode, date, 2.5);
        when(exchangeRateService.getExchangeRateByDateAndCurrency(date, currencyCode)).thenReturn(Optional.of(exchangeRate));

        mockMvc.perform(get("/exchange-rate")
                        .param("date", "2023-06-28")
                        .param("currencyCode", "USD")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currencyCode").value(currencyCode))
                .andExpect(jsonPath("$.date").value("2023-06-28"))
                .andExpect(jsonPath("$.rate").value(2.5));

        verify(exchangeRateService, times(1)).getExchangeRateByDateAndCurrency(date, currencyCode);
    }

    @Test
    public void testGetExchangeRateNotFound() throws Exception {
        LocalDate date = LocalDate.of(2023, 6, 28);
        String currencyCode = "USD";
        when(exchangeRateService.getExchangeRateByDateAndCurrency(date, currencyCode)).thenReturn(Optional.empty());

        mockMvc.perform(get("/exchange-rate")
                        .param("date", "2023-06-28")
                        .param("currencyCode", "USD")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(exchangeRateService, times(1)).getExchangeRateByDateAndCurrency(date, currencyCode);
    }
}
