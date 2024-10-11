package org.example.mrb_import.dao;

import org.example.mrb_import.model.RoomImageManagement;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Component
public class RoomImageManagementDao implements CommonDao<RoomImageManagement> {
    @Override
    public void save(RoomImageManagement roomImageManagement, Connection connection) {
    }

    @Override
    public void saveAll(List<RoomImageManagement> roomImageManagements, Connection connection) throws SQLException {
        String sql = "INSERT INTO room_image_managements (room_management_id, description, room_type, image_path, status, arrange, created_at, created_by) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        for(RoomImageManagement roomImageManagement : roomImageManagements) {
            PreparedStatement query =  connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            query.setObject(1, roomImageManagement.getRoomManagementId());
            query.setObject(2, roomImageManagement.getDescription());
            query.setObject(3, roomImageManagement.getRoomType());
            query.setObject(4, roomImageManagement.getImagePath());
            query.setObject(5, roomImageManagement.getStatus());
            query.setObject(6, roomImageManagement.getArrange());
            query.setObject(7, roomImageManagement.getCreatedAt());
            query.setObject(8, roomImageManagement.getCreatedBy());
            query.executeUpdate();
        }
    }

}
