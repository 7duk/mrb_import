package org.example.mrb_import.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.mrb_import.dto.BasePriceOfRoomCsv;
import org.example.mrb_import.model.BasePriceOfRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class BasePriceOfRoomMapper {
    private ObjectMapper mapper;

    @Autowired
    public BasePriceOfRoomMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public List<BasePriceOfRoom> MapperToBasePriceOfRoom(String basePriceOfRoomStr, Integer roomId, Integer partnerId, LocalDateTime now) throws JsonProcessingException {

        List<BasePriceOfRoomCsv> basePriceOfRoomCsvs = mapper.readValue(basePriceOfRoomStr,
                mapper.getTypeFactory().constructCollectionType(List.class, BasePriceOfRoomCsv.class));
        List<BasePriceOfRoom> basePriceOfRooms = basePriceOfRoomCsvs.stream().map(basePriceOfRoomCsv -> {
            BasePriceOfRoom basePriceOfRoom = new BasePriceOfRoom();
            basePriceOfRoom.setRoomManagementId(Long.valueOf(roomId));
            basePriceOfRoom.setBasePriceId(Long.valueOf(basePriceOfRoomCsv.getBasePriceId()));
            basePriceOfRoom.setRentFee(basePriceOfRoomCsv.getRentFee() != null &&  basePriceOfRoomCsv.getRentFee()!= ""? Integer.valueOf(basePriceOfRoomCsv.getRentFee().replace("\n", "").trim().replace(",","")) : 0);
            basePriceOfRoom.setLightAndHeatFee(basePriceOfRoomCsv.getLightAndHeatFee() != null && basePriceOfRoomCsv.getLightAndHeatFee() != "" ? Integer.valueOf(basePriceOfRoomCsv.getLightAndHeatFee().replace("\n", "").trim().replace(",","")) : 0);
            basePriceOfRoom.setCleaningFee(basePriceOfRoomCsv.getCleaningFee() != null && basePriceOfRoomCsv.getCleaningFee() != "" ? Integer.valueOf(basePriceOfRoomCsv.getCleaningFee().replace("\n", "").trim().replace(",","")) : 0);
            basePriceOfRoom.setCreatedAt(Timestamp.valueOf(now));
//        basePriceOfRoom.setUpdatedAt(basePriceOfRoomCsv.getUpdatedAt());
            basePriceOfRoom.setCreatedBy(Long.valueOf(partnerId));
//        basePriceOfRoom.setUpdatedBy(basePriceOfRoomCsv.getUpdatedBy());
            return basePriceOfRoom;
        }).toList();
        return basePriceOfRooms;

    }

}
