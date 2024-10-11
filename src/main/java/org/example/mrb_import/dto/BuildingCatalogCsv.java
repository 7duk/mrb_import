package org.example.mrb_import.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

@Data
public class BuildingCatalogCsv {
    private String buildingId;
    private String categoryCode;
    private String innerCode;
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
