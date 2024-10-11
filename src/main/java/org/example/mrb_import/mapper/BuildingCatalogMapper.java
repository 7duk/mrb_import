package org.example.mrb_import.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.mrb_import.dto.BuildingCatalogCsv;
import org.example.mrb_import.model.BuildingCatalog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class BuildingCatalogMapper {
    private ObjectMapper mapper;

    @Autowired
    public BuildingCatalogMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public List<BuildingCatalog> MapperToBuildingCatalog(String buildingCatalogStr, Integer buildingId, Integer partnerId, LocalDateTime now) throws JsonProcessingException {
        List<BuildingCatalogCsv> buildingCatalogCsvList = mapper.readValue(buildingCatalogStr,
                mapper.getTypeFactory().constructCollectionType(List.class, BuildingCatalogCsv.class));
        List<BuildingCatalog> buildingCatalogList = buildingCatalogCsvList.stream().filter(buildingCatalogCsv -> buildingCatalogCsv.getCategoryCode() != null || buildingCatalogCsv.getInnerCode() != null)
                .map(buildingCatalogCsv -> {
                    BuildingCatalog buildingCatalog = new BuildingCatalog();
                    buildingCatalog.setBuildingId(Long.valueOf(buildingId));
                    buildingCatalog.setCategoryCode(Long.valueOf(buildingCatalogCsv.getCategoryCode()));
                    buildingCatalog.setInnerCode(Long.valueOf(buildingCatalogCsv.getInnerCode()));
                    buildingCatalog.setCreatedAt(Timestamp.valueOf(now));
//        roomCatalog.setUpdatedAt(roomCatalogCsv.getUpdatedAt());
                    buildingCatalog.setCreatedBy(Long.valueOf(partnerId));
//        roomCatalog.setUpdatedBy(roomCatalogCsv.getUpdatedBy());

                    return buildingCatalog;
                }).toList();
        return buildingCatalogList;
    }

}
