package org.example.mrb_import.model;

//import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
////@Entity
////@Table(name = "building_managements")
public class BuildingManagement {

//    //@Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY) // Assuming you're using an auto-increment strategy
    private Long id;

//    //@Column(name = "partner_id")
    private Long partnerId;

//    //@Column(name = "website_property_name")
    private String websitePropertyName;

//    //@Column(name = "building_name")
    private String buildingName;

//    //@Column(name = "status_building_name")
    private String statusBuildingName;

//    //@Column(name = "cho_aza_code")
    private String choAzaCode;

    //@Column(name = "house_number")
    private String houseNumber;

    //@Column(name = "latitude")
    private BigDecimal latitude;

    //@Column(name = "longitude")
    private BigDecimal longitude;

    //@Column(name = "surrounding_environment", columnDefinition = "text")
    private String surroundingEnvironment;

    //@Column(name = "other_transportation")
    private String otherTransportation;

    //@Column(name = "number_of_floors")
    private Integer numberOfFloors;

    //@Column(name = "total_number_of_units")
    private Integer totalNumberOfUnits;

    //@Column(name = "building_area")
    private BigDecimal buildingArea;

    //@Column(name = "completion_date")
    private java.sql.Date completionDate;

    //@Column(name = "other_facilities", columnDefinition = "text")
    private String otherFacilities;

    //@Column(name = "contract_special_conditions", columnDefinition = "text")
    private String contractSpecialConditions;

    //@Column(name = "number_of_parking_spots")
    private Integer numberOfParkingSpots;

    //@Column(name = "parking_notes", columnDefinition = "text")
    private String parkingNotes;

    //@Column(name = "other_notes", columnDefinition = "text")
    private String otherNotes;

    //@Column(name = "created_at")
    private Timestamp createdAt;

    //@Column(name = "updated_at")
    private Timestamp updatedAt;

    //@Column(name = "created_by")
    private Long createdBy;

    //@Column(name = "updated_by")
    private Long updatedBy;

    //@Column(name = "deleted_at")
    private Timestamp deletedAt;

    //@Column(name = "address_id")
    private Long addressId;

    //@Column(name = "prefecture")
    private String prefecture;

    //@Column(name = "postal_code")
    private String postalCode;

}
