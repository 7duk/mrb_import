package org.example.mrb_import.dao;

import org.example.mrb_import.model.BasePriceOfRoom;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Component
public class BasePriceOfRoomDao implements CommonDao<BasePriceOfRoom> {
    @Override
    public void save(BasePriceOfRoom basePriceOfRoom, Connection Connection) {

    }

    @Override
    public void saveAll(List<BasePriceOfRoom> basePriceOfRooms, Connection connection) throws SQLException {
        String sql = "INSERT INTO base_price_of_rooms (room_management_id, base_price_id, rent_fee, light_and_heat_fee, cleaning_fee, created_at, created_by) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        for(BasePriceOfRoom basePriceOfRoom : basePriceOfRooms){
            PreparedStatement query = connection.prepareStatement(sql);
            query.setObject(1,basePriceOfRoom.getRoomManagementId());
            query.setObject(2,basePriceOfRoom.getBasePriceId());
            query.setObject(3,basePriceOfRoom.getRentFee());
            query.setObject(4,basePriceOfRoom.getLightAndHeatFee());
            query.setObject(5,basePriceOfRoom.getCleaningFee());
            query.setObject(6,basePriceOfRoom.getCreatedAt());
            query.setObject(7,basePriceOfRoom.getCreatedBy());
            query.executeUpdate();
        }
    }

}
