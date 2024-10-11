package org.example.mrb_import.dao;

import org.example.mrb_import.model.BuildingManagement;
import org.example.mrb_import.model.RoomManagement;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.List;

@Component
public class RoomManagementDao implements CommonDao<RoomManagement> {
    @Override
    public void save(RoomManagement roomManagement, Connection connection) throws SQLException {

    }

    public Long saveRoom(RoomManagement roomManagement, Connection connection) throws SQLException {
        String sql = "INSERT INTO room_managements (partner_id, building_id, room_number, " +
                "status_room_number, room_type, license_number, floor_description, floor, room_size, catchphrase, " +
                "video_url, panorama_url, bed_detail, facility_detail, mailbox_access, owner_information, " +
                "business_contact_memo, status, created_at, created_by) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement query = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        query.setObject(1, roomManagement.getPartnerId());
        query.setObject(2, roomManagement.getBuildingId());
        query.setObject(3, roomManagement.getRoomNumber());
        query.setObject(4, roomManagement.getStatusRoomNumber());
        query.setObject(5, roomManagement.getRoomType());
        query.setObject(6, roomManagement.getLicenseNumber());
        query.setObject(7, roomManagement.getFloorDescription());
        query.setObject(8, roomManagement.getFloor());
        query.setObject(9, roomManagement.getRoomSize());
        query.setObject(10, roomManagement.getCatchphrase());
        query.setObject(11, roomManagement.getVideoUrl());
        query.setObject(12, roomManagement.getPanoramaUrl());
        query.setObject(13, roomManagement.getBedDetail());
        query.setObject(14, roomManagement.getFacilityDetail());
        query.setObject(15, roomManagement.getMailboxAccess());
        query.setObject(16, roomManagement.getOwnerInformation());
        query.setObject(17, roomManagement.getBusinessContactMemo());
        query.setObject(18, roomManagement.getStatus());
        query.setObject(19, roomManagement.getCreatedAt());
        query.setObject(20, roomManagement.getCreatedBy());

        int affectedRows = query.executeUpdate();
        if (affectedRows > 0) {
            ResultSet generatedKeys = query.getGeneratedKeys();
            if (generatedKeys.next()) {
                long generatedId = generatedKeys.getLong(1); // Lấy giá trị khóa sinh ra
                System.out.println("Generated ID: " + generatedId);
                return generatedId;

            }
        }
        return null;
    }

    @Override
    public void saveAll(List<RoomManagement> t, Connection connection) {

    }

    public  Integer getNextAutoIncrement(Connection connection) throws SQLException {
        String sql = "SELECT AUTO_INCREMENT\n" +
                "FROM information_schema.TABLES\n" +
                "WHERE TABLE_SCHEMA = 'mrb'\n" +
                "AND TABLE_NAME = 'room_managements'";
        PreparedStatement statement = connection.prepareStatement(sql);
        Integer lastId = null;
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            lastId = resultSet.getInt("AUTO_INCREMENT");
        }
        return lastId;
    }

}
