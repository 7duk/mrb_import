package org.example.mrb_import.model;

//import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
//@Entity
//@Table(name = "room_catalogs")
//@IdClass(RoomCatalogId.class) // Sử dụng IdClass để chỉ định composite key
public class RoomCatalog {

    //@Id
    //@Column(name = "room_id")
    private Long roomId;

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
