package org.example.mrb_import.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.csv.CSVRecord;
import org.example.mrb_import.model.BuildingManagement;
import org.example.mrb_import.utils.DateUtils;
import org.example.mrb_import.utils.StringUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Component
public class BuildingManagementMapper {
    public BuildingManagement MapperToBuildingManagement(CSVRecord record, Integer partnerId, LocalDateTime now) {
        BuildingManagement buildingManagement = new BuildingManagement();
        buildingManagement.setPartnerId(Long.valueOf(partnerId));
        buildingManagement.setWebsitePropertyName(record.get("website_property_name"));
        buildingManagement.setBuildingName(record.get("building_name"));
        buildingManagement.setStatusBuildingName(record.get("status_building_name"));
        buildingManagement.setChoAzaCode(record.get("cho_aza_code"));
        buildingManagement.setHouseNumber(record.get("house_number"));
        buildingManagement.setLatitude(new BigDecimal(record.get("latitude")));
        buildingManagement.setLongitude(new BigDecimal(record.get("longitude")));
        buildingManagement.setSurroundingEnvironment(record.get("surrounding_environment"));
        buildingManagement.setOtherTransportation(record.get("other_transportation"));
        buildingManagement.setNumberOfFloors(StringUtils.ConvertToInt(record.get("number_of_floors")));
        buildingManagement.setTotalNumberOfUnits(StringUtils.ConvertToInt(record.get("total_number_of_units")));
        buildingManagement.setBuildingArea(record.get("building_area").isEmpty() ? null : new BigDecimal(record.get("building_area")));
        buildingManagement.setCompletionDate(record.get("completion_date").isEmpty()||record.get("completion_date") == null ? null : Date.valueOf(DateUtils.convertJapaneseDateToVietnameseFormat(record.get("completion_date"))));
        buildingManagement.setOtherFacilities(record.get("other_facilities"));
        buildingManagement.setContractSpecialConditions(record.get("contract_special_conditions"));
        buildingManagement.setNumberOfParkingSpots(StringUtils.ConvertToInt(record.get("number_of_parking_spots")));
        buildingManagement.setParkingNotes(record.get("parking_notes"));
        buildingManagement.setOtherNotes(record.get("other_notes"));

        buildingManagement.setCreatedAt(Timestamp.valueOf(now));
//        buildingManagement.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        buildingManagement.setCreatedBy(Long.valueOf(partnerId));
//        buildingManagement.setUpdatedBy(Long.valueOf(updatedBy));
//        buildingManagement.setDeletedAt(null);
//        buildingManagement.setAddressId(Long.valueOf(addressId));
        buildingManagement.setPrefecture(record.isMapped("prefecture") ? record.get("prefecture") : "");
        buildingManagement.setPostalCode(record.isMapped("postal_code") ? record.get("postal_code") : "");
        return buildingManagement;
    }

}
