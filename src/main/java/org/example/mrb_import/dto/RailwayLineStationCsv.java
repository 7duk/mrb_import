package org.example.mrb_import.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

@Data
public class RailwayLineStationCsv {
    private String buildingId;
    private String lineId;
    private String stationId;
    private String railwayName;
    private String stationName;
    private String walkingTime;
    private String otherTransportation;
    private String sort;
    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch ( JsonProcessingException e) {
            e.printStackTrace();
            return "{}"; // Trả về chuỗi JSON rỗng nếu có lỗi
        }
    }
}
