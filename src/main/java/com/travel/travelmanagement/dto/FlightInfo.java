package com.travel.travelmanagement.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FlightInfo {
    private String number;
    private String iata;
    private String icao;
    @JsonProperty("codeshared")
    private Object codeshared;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getIata() {
        return iata;
    }

    public void setIata(String iata) {
        this.iata = iata;
    }

    public String getIcao() {
        return icao;
    }

    public void setIcao(String icao) {
        this.icao = icao;
    }

    public Object getCodeshared() {
        return codeshared;
    }

    public void setCodeshared(Object codeshared) {
        this.codeshared = codeshared;
    }
}
