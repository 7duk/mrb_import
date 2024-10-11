package org.example.mrb_import.dao;

import org.example.mrb_import.model.RailwayLineStation;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Component
public class RailwayLineStationDao implements CommonDao<RailwayLineStation>{
    @Override
    public void save(RailwayLineStation railwayLineStation, Connection connection) {
    }

    @Override
    public void saveAll(List<RailwayLineStation> railwayLineStations, Connection connection) throws SQLException {
        String sql = "INSERT INTO railway_line_station (building_id, line_id, station_id, railway_name, station_name, other_transportation, walking_time, sort, created_at, created_by) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        for(RailwayLineStation railwayLineStation : railwayLineStations) {
            PreparedStatement query =  connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            query.setObject(1, railwayLineStation.getBuildingId());
            query.setObject(2, railwayLineStation.getLineId());
            query.setObject(3, railwayLineStation.getStationId());
            query.setObject(4, railwayLineStation.getRailwayName());
            query.setObject(5, railwayLineStation.getStationName());
            query.setObject(6, railwayLineStation.getOtherTransportation());
            query.setObject(7, railwayLineStation.getWalkingTime());
            query.setObject(8, railwayLineStation.getSort());
            query.setObject(9, railwayLineStation.getCreatedAt());
            query.setObject(10, railwayLineStation.getCreatedBy());
            query.executeUpdate();
        }
    }

}
