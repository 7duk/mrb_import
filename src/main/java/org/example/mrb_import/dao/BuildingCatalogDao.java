package org.example.mrb_import.dao;

import org.example.mrb_import.model.BuildingCatalog;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Component
public class BuildingCatalogDao implements CommonDao<BuildingCatalog> {
    @Override
    public void save(BuildingCatalog buildingCatalog, Connection connection) {
    }

    @Override
    public void saveAll(List<BuildingCatalog> buildingCatalogs, Connection connection) throws SQLException {
        String sql = "INSERT INTO building_catalogs (building_id, category_code, inner_code, created_at, created_by) " +
                "VALUES (?, ?, ?, ?, ?)";
        for(BuildingCatalog buildingCatalog : buildingCatalogs) {
            PreparedStatement query =  connection.prepareStatement(sql);
            query.setObject(1,buildingCatalog.getBuildingId());
            query.setObject(2,buildingCatalog.getCategoryCode());
            query.setObject(3,buildingCatalog.getInnerCode());
            query.setObject(4,buildingCatalog.getCreatedAt());
            query.setObject(5,buildingCatalog.getCreatedBy());
            query.executeUpdate();
        }
    }

}
