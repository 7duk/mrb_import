package org.example.mrb_import.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.mrb_import.dto.BasePriceOfRoomCsv;
import org.example.mrb_import.dto.RailwayLineStationCsv;
import org.example.mrb_import.model.RailwayLineStation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class RailwayLineStationMapper {
    private ObjectMapper mapper;

    @Autowired
    public RailwayLineStationMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public List<RailwayLineStation> MapperToRailwayLineStation(String railwayLineStationJson, Integer buildingId, Integer partnerId, LocalDateTime now) throws JsonProcessingException {
        List<RailwayLineStationCsv> railwayLineStationCsvs = mapper.readValue(railwayLineStationJson,
                mapper.getTypeFactory().constructCollectionType(List.class, RailwayLineStationCsv.class));
        List<RailwayLineStation> railwayLineStations = railwayLineStationCsvs.stream().map(railwayLineStationCsv -> {
            RailwayLineStation railwayLineStation = new RailwayLineStation();
            railwayLineStation.setBuildingId(Long.valueOf(buildingId));
            railwayLineStation.setLineId(Long.valueOf(railwayLineStationCsv.getLineId()));
            railwayLineStation.setStationId(Long.valueOf(railwayLineStationCsv.getStationId()));
            railwayLineStation.setRailwayName(railwayLineStationCsv.getRailwayName());
            railwayLineStation.setStationName(railwayLineStationCsv.getStationName());
            railwayLineStation.setOtherTransportation(railwayLineStationCsv.getOtherTransportation());
            railwayLineStation.setWalkingTime(Integer.valueOf(railwayLineStationCsv.getWalkingTime()));
            railwayLineStation.setSort(Integer.valueOf(railwayLineStationCsv.getSort()));
            railwayLineStation.setCreatedAt(Timestamp.valueOf(now)); // Set current time or from CSV if available
//        railwayLineStation.setUpdatedAt(new Timestamp(System.currentTimeMillis())); // Set current time or from CSV if available
            railwayLineStation.setCreatedBy(Long.valueOf(partnerId));
//        railwayLineStation.setUpdatedBy(null);
            return railwayLineStation;
        }).toList();
        return railwayLineStations;

    }

}
