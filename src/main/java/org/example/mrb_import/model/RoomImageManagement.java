package org.example.mrb_import.model;

//import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
//@Entity
////@Table(name = "room_image_managements")
public class RoomImageManagement {

    //@Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY) // Assuming you're using an auto-increment strategy
    private Long id;

    //@Column(name = "room_management_id")
    private Long roomManagementId;

    //@Column(name = "description", length = 255)
    private String description;

    //@Column(name = "room_type")
    private Integer roomType;

    //@Column(name = "image_path", length = 255)
    private String imagePath;

    //@Column(name = "status")
    private Integer status;

    //@Column(name = "arrange")
    private Integer arrange;

    //@Column(name = "created_at")
    private Timestamp createdAt;

    //@Column(name = "updated_at")
    private Timestamp updatedAt;

    //@Column(name = "created_by")
    private Long createdBy;

    //@Column(name = "updated_by")
    private Long updatedBy;
}
