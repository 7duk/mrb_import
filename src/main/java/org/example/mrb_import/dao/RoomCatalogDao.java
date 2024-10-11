package org.example.mrb_import.dao;

import org.example.mrb_import.model.RoomCatalog;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Component
public class RoomCatalogDao implements CommonDao<RoomCatalog> {
    @Override
    public void save(RoomCatalog roomCatalog, Connection connection) {
    }

    @Override
    public void saveAll(List<RoomCatalog> roomCatalogs, Connection connection) throws SQLException {
        String sql = "INSERT INTO room_catalogs (room_id, category_code, inner_code, created_at, created_by) " +
                "VALUES (?, ?, ?, ?, ?)";
        for(RoomCatalog roomCatalog : roomCatalogs) {
            PreparedStatement query =  connection.prepareStatement(sql);
            query.setObject(1,roomCatalog.getRoomId());
            query.setObject(2,roomCatalog.getCategoryCode());
            query.setObject(3,roomCatalog.getInnerCode());
            query.setObject(4,roomCatalog.getCreatedAt());
            query.setObject(5,roomCatalog.getCreatedBy());
            query.executeUpdate();
        }
    }

}
