package org.example.mrb_import.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

@Data
public class RoomImageManagementCsv {
    private String id;
    private String roomManagementId;
    private String description;
    private String roomType;
    private String imagePath;
    private String status;
    private String arrange;
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
