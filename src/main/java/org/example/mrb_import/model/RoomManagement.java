package org.example.mrb_import.model;

//import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
//@Entity
//@Table(name = "room_managements")
public class RoomManagement {

    //@Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY) // Assuming you're using an auto-increment strategy
    private Long id;

    //@Column(name = "partner_id")
    private Long partnerId;

    //@Column(name = "building_id")
    private Long buildingId;

    //@Column(name = "room_number", length = 50)
    private String roomNumber;

    //@Column(name = "status_room_number")
    private Integer statusRoomNumber;

    //@Column(name = "room_type", length = 255)
    private String roomType;

    //@Column(name = "license_number", length = 255)
    private String licenseNumber;

    //@Column(name = "floor_description", length = 100)
    private String floorDescription;

    //@Column(name = "floor")
    private Integer floor;

    //@Column(name = "room_size")
    private BigDecimal roomSize;

    //@Column(name = "catchphrase", columnDefinition = "text")
    private String catchphrase;

    //@Column(name = "video_url", length = 255)
    private String videoUrl;

    //@Column(name = "panorama_url", length = 255)
    private String panoramaUrl;

    //@Column(name = "bed_detail", length = 255)
    private String bedDetail;

    //@Column(name = "facility_detail", columnDefinition = "text")
    private String facilityDetail;

    //@Column(name = "mailbox_access", length = 255)
    private String mailboxAccess;

    //@Column(name = "owner_information", length = 100)
    private String ownerInformation;

    //@Column(name = "business_contact_memo", columnDefinition = "text")
    private String businessContactMemo;

    //@Column(name = "status")
    private Integer status;

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

}