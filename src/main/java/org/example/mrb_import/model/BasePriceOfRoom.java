package org.example.mrb_import.model;


//import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
//@Entity
//@Table(name = "base_price_of_rooms")
//@IdClass(BasePriceOfRoomId.class) // Sử dụng IdClass để chỉ định composite key
public class BasePriceOfRoom {

    //@Id
    //@Column(name = "room_management_id")
    private Long roomManagementId;

    //@Id
    //@Column(name = "base_price_id")
    private Long basePriceId;

    //@Column(name = "rent_fee")
    private Integer rentFee;

    //@Column(name = "light_and_heat_fee")
    private Integer lightAndHeatFee;

    //@Column(name = "cleaning_fee")
    private Integer cleaningFee;

    //@Column(name = "created_at")
    private Timestamp createdAt;

    //@Column(name = "updated_at")
    private Timestamp updatedAt;

    //@Column(name = "created_by")
    private Long createdBy;

    //@Column(name = "updated_by")
    private Long updatedBy;

}