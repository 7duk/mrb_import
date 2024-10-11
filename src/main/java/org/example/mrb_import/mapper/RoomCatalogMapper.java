package org.example.mrb_import.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.mrb_import.dto.RoomCatalogCsv;
import org.example.mrb_import.model.RoomCatalog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class RoomCatalogMapper {
    private ObjectMapper mapper;

    @Autowired
    public RoomCatalogMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public List<RoomCatalog> MapperToRoomCatalog(String roomCatalogStr, Integer roomId, Integer partnerId, LocalDateTime now) throws JsonProcessingException {
        List<RoomCatalogCsv> roomCatalogCsvs = mapper.readValue(roomCatalogStr,
                mapper.getTypeFactory().constructCollectionType(List.class, RoomCatalogCsv.class));

        List<RoomCatalog> roomCatalogs = roomCatalogCsvs.stream().filter(roomCatalogCsv -> roomCatalogCsv.getCategoryCode() != null || roomCatalogCsv.getInnerCode() != null).map(roomCatalogCsv -> {
            RoomCatalog roomCatalog = new RoomCatalog();
            roomCatalog.setRoomId(Long.valueOf(roomId));
            roomCatalog.setCategoryCode(Long.valueOf(roomCatalogCsv.getCategoryCode()));
            roomCatalog.setInnerCode(Long.valueOf(roomCatalogCsv.getInnerCode()));
            roomCatalog.setCreatedAt(Timestamp.valueOf(now));
//        roomCatalog.setUpdatedAt(roomCatalogCsv.getUpdatedAt());
            roomCatalog.setCreatedBy(Long.valueOf(partnerId));
//        roomCatalog.setUpdatedBy(roomCatalogCsv.getUpdatedBy());
            return roomCatalog;
        }).toList();
        return roomCatalogs;

    }

}
