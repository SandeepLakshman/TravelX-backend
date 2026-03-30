package com.travel.travelmanagement.dto;

import java.util.List;

public class FlightResponse {
    private Pagination pagination;
    private List<FlightData> data;

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public List<FlightData> getData() {
        return data;
    }

    public void setData(List<FlightData> data) {
        this.data = data;
    }
}
