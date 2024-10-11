package org.example.mrb_import.model;

//import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
//@Entity
//@Table(name = "building_catalogs")
//@IdClass(BuildingCatalogId.class) // Sử dụng IdClass để chỉ định composite key
public class BuildingCatalog {

    //@Id
    //@Column(name = "building_id")
    private Long buildingId;

    //@Id
    //@Column(name = "category_code")
    private Long categoryCode;

    //@Id
    //@Column(name = "inner_code")
    private Long innerCode;

    //@Column(name = "created_at")
    private Timestamp createdAt;

    //@Column(name = "updated_at")
    private Timestamp updatedAt;

    //@Column(name = "created_by")
    private Long createdBy;

    //@Column(name = "updated_by")
    private Long updatedBy;
}
