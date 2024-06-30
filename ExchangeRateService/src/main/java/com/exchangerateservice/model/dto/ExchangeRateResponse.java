package com.exchangerateservice.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ExchangeRateResponse {
    @JsonProperty("Cur_ID")
    private int curId;

    @JsonProperty("Date")
    private LocalDate date;

    @JsonProperty("Cur_Abbreviation")
    private String curAbbreviation;

    @JsonProperty("Cur_Scale")
    private int curScale;

    @JsonProperty("Cur_Name")
    private String curName;

    @JsonProperty("Cur_OfficialRate")
    private double curOfficialRate;
}
