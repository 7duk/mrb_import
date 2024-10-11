package org.example.mrb_import.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.mrb_import.dto.RoomImageManagementCsv;
import org.example.mrb_import.model.RoomImageManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class RoomImageMapper {
    private ObjectMapper mapper;

    @Autowired
    public RoomImageMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public List<RoomImageManagement> MapperToRoomImageManagement(String roomImageManagementStr, Integer roomId, Integer partnerId, LocalDateTime now) throws JsonProcessingException {
        List<RoomImageManagementCsv> roomImageManagementCsvs = mapper.readValue(roomImageManagementStr,
                mapper.getTypeFactory().constructCollectionType(List.class, RoomImageManagementCsv.class));

        List<RoomImageManagement> roomImageManagements = roomImageManagementCsvs.stream().map(roomImageManagementCsv -> {
            RoomImageManagement roomImageManagement = new RoomImageManagement();
            roomImageManagement.setRoomManagementId(Long.valueOf(roomId));
            roomImageManagement.setDescription(roomImageManagementCsv.getDescription());
            roomImageManagement.setRoomType(!roomImageManagementCsv.getRoomType().isEmpty() ? Integer.valueOf(roomImageManagementCsv.getRoomType()) : null);
            roomImageManagement.setImagePath(roomImageManagementCsv.getImagePath());
            roomImageManagement.setStatus(Integer.valueOf(roomImageManagementCsv.getStatus()));
            roomImageManagement.setArrange(Integer.valueOf(roomImageManagementCsv.getArrange()));
            roomImageManagement.setCreatedAt(Timestamp.valueOf(now));
//        roomImageManagement.setUpdatedAt(null);
            roomImageManagement.setCreatedBy(Long.valueOf(partnerId));
//        roomImageManagement.setUpdatedBy(null);
            return roomImageManagement;
        }).toList();
        return roomImageManagements;

    }

}
