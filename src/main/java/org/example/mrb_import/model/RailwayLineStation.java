package org.example.mrb_import.model;

//import jakarta.persistence.*;
import lombok.Data;
import java.sql.Timestamp;

@Data
//@Entity
//@Table(name = "railway_line_stations")
//@IdClass(RailwayLineStationId.class)
public class RailwayLineStation {

    //@Id
    //@Column(name = "building_id")
    private Long buildingId;

    //@Id
    //@Column(name = "line_id")
    private Long lineId;

    //@Id
    //@Column(name = "station_id")
    private Long stationId;

    //@Column(name = "railway_name", length = 255)
    private String railwayName;

    //@Column(name = "station_name", length = 255)
    private String stationName;

    //@Column(name = "other_transportation", length = 255)
    private String otherTransportation;

    //@Column(name = "walking_time")
    private Integer walkingTime;

    //@Column(name = "sort")
    private Integer sort;

    //@Column(name = "created_at")
    private Timestamp createdAt;

    //@Column(name = "updated_at")
    private Timestamp updatedAt;

    //@Column(name = "created_by")
    private Long createdBy;

    //@Column(name = "updated_by")
    private Long updatedBy;
}
