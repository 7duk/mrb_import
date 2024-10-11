package org.example.mrb_import.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

@Data
public class BasePriceOfRoomCsv {
    private String roomManagementId;
    private String basePriceId;
    private String rentFee;
    private String lightAndHeatFee;
    private String cleaningFee;
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
