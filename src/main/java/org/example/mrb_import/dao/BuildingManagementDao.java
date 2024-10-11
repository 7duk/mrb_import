package org.example.mrb_import.dao;

import org.example.mrb_import.model.BuildingManagement;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.List;

@Component
public class BuildingManagementDao implements CommonDao<BuildingManagement> {
    @Override
    public void save(BuildingManagement buildingManagement, Connection connection) throws SQLException {

    }

    public Long saveBuilding(BuildingManagement buildingManagement, Connection connection) throws SQLException {
        String sql = "INSERT INTO building_managements (partner_id, website_property_name, building_name, " +
                "status_building_name, cho_aza_code, house_number, latitude, longitude, surrounding_environment, " +
                "other_transportation, number_of_floors, total_number_of_units, building_area, completion_date, " +
                "other_facilities, contract_special_conditions, number_of_parking_spots, parking_notes, other_notes, " +
                "created_at, created_by, prefecture, postal_code) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)";
        System.out.println("Set autocommit is :" + connection.getAutoCommit());
        PreparedStatement query = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        query.setObject(1, buildingManagement.getPartnerId());
        query.setObject(2, buildingManagement.getWebsitePropertyName());
        query.setObject(3, buildingManagement.getBuildingName());
        query.setObject(4, buildingManagement.getStatusBuildingName());
        query.setObject(5, buildingManagement.getChoAzaCode());
        query.setObject(6, buildingManagement.getHouseNumber());
        query.setObject(7, buildingManagement.getLatitude());
        query.setObject(8, buildingManagement.getLongitude());
        query.setObject(9, buildingManagement.getSurroundingEnvironment());
        query.setObject(10, buildingManagement.getOtherTransportation());
        query.setObject(11, buildingManagement.getNumberOfFloors());
        query.setObject(12, buildingManagement.getTotalNumberOfUnits());
        query.setObject(13, buildingManagement.getBuildingArea());
        query.setObject(14, buildingManagement.getCompletionDate());
        query.setObject(15, buildingManagement.getOtherFacilities());
        query.setObject(16, buildingManagement.getContractSpecialConditions());
        query.setObject(17, buildingManagement.getNumberOfParkingSpots());
        query.setObject(18, buildingManagement.getParkingNotes());
        query.setObject(19, buildingManagement.getOtherNotes());
        query.setObject(20, buildingManagement.getCreatedAt());
        query.setObject(21, buildingManagement.getCreatedBy());
        query.setObject(22, buildingManagement.getPrefecture());
        query.setObject(23, buildingManagement.getPostalCode());
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

    public Integer getNextAutoIncrement(Connection connection) throws SQLException {
        String sql = "SELECT AUTO_INCREMENT\n" +
                "FROM information_schema.TABLES\n" +
                "WHERE TABLE_SCHEMA = 'mrb'\n" +
                "AND TABLE_NAME = 'building_managements'";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        Integer lastID = null;
        if (resultSet.next()) {
            lastID = resultSet.getInt("AUTO_INCREMENT");
        }
        return lastID;
    }

    public Integer lastID(Connection connection) throws SQLException {
        String sql = "SELECT id FROM building_managements ORDER BY id DESC LIMIT 1";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        Integer lastID = null;
        if (resultSet.next()) {
            lastID = resultSet.getInt("id");
        }
        return lastID;
    }

    @Override
    public void saveAll(List<BuildingManagement> t, Connection connection) {

    }

    public boolean isDuplicate(BuildingManagement buildingManagement, Connection connection) throws SQLException {
        String sql = "SELECT COUNT(*) FROM building_managements where building_name = ? and partner_id = ? and cho_aza_code = ? and latitude = ? and longitude = ?";
        PreparedStatement query = connection.prepareStatement(sql);
        query.setObject(1, buildingManagement.getBuildingName());
        query.setObject(2, buildingManagement.getPartnerId());
        query.setObject(3, buildingManagement.getChoAzaCode());
        query.setObject(4, buildingManagement.getLatitude());
        query.setObject(5, buildingManagement.getLongitude());
        ResultSet resultSet = query.executeQuery();
        if (resultSet.next()) {
            if (resultSet.getInt(1) < 1) {
                return false;
            }
        }
        return true;
    }
}
