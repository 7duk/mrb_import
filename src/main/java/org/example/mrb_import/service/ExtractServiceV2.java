package org.example.mrb_import.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.csv.CSVRecord;
import org.example.mrb_import.config.JdbcConnection;
import org.example.mrb_import.dao.*;
import org.example.mrb_import.mapper.*;
import org.example.mrb_import.model.*;
import org.example.mrb_import.utils.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExtractServiceV2 {
    private BuildingManagementMapper buildingManagementMapper;
    private RoomManagementMapper roomManagementMapper;
    private BasePriceOfRoomMapper basePriceOfRoomMapper;
    private RailwayLineStationMapper railwayLineStationMapper;
    private BuildingCatalogMapper buildingCatalogMapper;
    private RoomCatalogMapper roomCatalogMapper;
    private RoomImageMapper roomImageMapper;
    private BuildingManagementDao buildingManagementDao;
    private RoomManagementDao roomManagementDao;
    private BasePriceOfRoomDao basePriceOfRoomDao;
    private RailwayLineStationDao railwayLineStationDao;
    private BuildingCatalogDao buildingCatalogDao;
    private RoomCatalogDao roomCatalogDao;
    private RoomImageManagementDao roomImageManagementDao;

    private String server = "192.168.110.79";
    private int port = 21;
    private String username = "goline";
    private String password = "123456";
    private String remoteFolder = "room_image";

    public ExtractServiceV2(RoomManagementMapper roomManagementMapper,
                            BuildingManagementMapper buildingManagementMapper,
                            BasePriceOfRoomMapper basePriceOfRoomMapper,
                            RailwayLineStationMapper railwayLineStationMapper,
                            BuildingCatalogMapper buildingCatalogMapper,
                            RoomCatalogMapper roomCatalogMapper,
                            RoomImageMapper roomImageMapper,
                            BuildingManagementDao buildingManagementDao,
                            RoomManagementDao roomManagementDao,
                            BasePriceOfRoomDao basePriceOfRoomDao,
                            RailwayLineStationDao railwayLineStationDao,
                            BuildingCatalogDao buildingCatalogDao,
                            RoomCatalogDao roomCatalogDao,
                            RoomImageManagementDao roomImageManagementDao) {
        this.roomManagementMapper = roomManagementMapper;
        this.buildingManagementMapper = buildingManagementMapper;
        this.basePriceOfRoomMapper = basePriceOfRoomMapper;
        this.railwayLineStationMapper = railwayLineStationMapper;
        this.buildingCatalogMapper = buildingCatalogMapper;
        this.roomCatalogMapper = roomCatalogMapper;
        this.roomImageMapper = roomImageMapper;
        this.buildingManagementDao = buildingManagementDao;
        this.roomManagementDao = roomManagementDao;
        this.basePriceOfRoomDao = basePriceOfRoomDao;
        this.railwayLineStationDao = railwayLineStationDao;
        this.buildingCatalogDao = buildingCatalogDao;
        this.roomCatalogDao = roomCatalogDao;
        this.roomImageManagementDao = roomImageManagementDao;

    }

    public void process(String fileName, Integer partnerId, String outputFolder) throws Exception {
        Iterable<CSVRecord> records = FileUtils.extractRecordsFromFile(fileName);
        List<CSVRecord> successfulRecords = new ArrayList<>();
        List<CSVRecord> failedRecords = new ArrayList<>();
        Connection connection = null;
        try {
            connection = JdbcConnection.getConnection();
            connection.setAutoCommit(false);
            System.out.println("Auto-commit set to: " + connection.getAutoCommit());

            if (connection.getAutoCommit()) {
                throw new SQLException("Failed to set autocommit to false");
            }

            for (CSVRecord record : records) {
                Savepoint savepoint = connection.setSavepoint();
                try {
                    processRecord(record, partnerId, LocalDateTime.now(), connection, outputFolder);
                    successfulRecords.add(record);
                    System.out.printf("Successfully processed record: %s%n", record);
                    connection.commit();
                } catch (Exception e) {
                    System.out.printf("Error processing record: %s. Rolling back this record.%n", record);
                    e.printStackTrace();
                    connection.rollback(savepoint);
                    failedRecords.add(record);
                }
            }
            // Commit tất cả các bản ghi thành công sau khi xử lý xong
            System.out.println("All successful records processed and committed.");
            // Báo cáo các bản ghi thành công
            System.out.println("Successfully inserted records:");

            for (CSVRecord successfulRecord : successfulRecords) {
                System.out.println(successfulRecord);
            }
            System.out.println("Total successful records: " + successfulRecords.size());

            // Báo cáo các bản ghi thất bại
            if (!failedRecords.isEmpty()) {
                System.out.println("The following records failed to process:");
                for (CSVRecord failedRecord : failedRecords) {
                    System.out.println(failedRecord);
                }
                System.out.println("Total failed records: " + failedRecords.size());
            }
            FileUtils.pushImagesToFtp(server, port, username, password, outputFolder,remoteFolder );
        } catch (Exception e) {
            System.out.println("Error in database operation: " + e.getMessage());
            if (connection != null) {
                try {
                    connection.rollback();
                    System.out.println("Transaction rolled back due to error.");
                } catch (SQLException ex) {
                    System.out.println("Error rolling back transaction: " + ex.getMessage());
                }
            }
            throw e;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                    System.out.println("Database connection closed.");
                } catch (SQLException e) {
                    System.out.println("Error closing connection: " + e.getMessage());
                }
            }
        }
    }


    private void processRecord(CSVRecord record, Integer partnerId, LocalDateTime now, Connection connection, String outputPath) throws JsonProcessingException, SQLException {
        BuildingManagement buildingManagement = buildingManagementMapper.MapperToBuildingManagement(record, partnerId, now);
        Long buildingId;
        if (!buildingManagementDao.isDuplicate(buildingManagement, connection)) {
            buildingId = buildingManagementDao.saveBuilding(buildingManagement, connection);

            List<BuildingCatalog> buildingCatalogs = buildingCatalogMapper.MapperToBuildingCatalog(record.get("building_catalogs"), Math.toIntExact(buildingId), partnerId, now);
            List<BuildingCatalog> buildingCatalogsWithBuildingId = buildingCatalogs.stream().map(buildingCatalog -> {
                BuildingCatalog newBuildingCatalog = new BuildingCatalog();
                newBuildingCatalog.setBuildingId(buildingId);
                newBuildingCatalog.setCategoryCode(buildingCatalog.getCategoryCode());
                newBuildingCatalog.setInnerCode(buildingCatalog.getInnerCode());
                newBuildingCatalog.setCreatedAt(buildingCatalog.getCreatedAt());
                newBuildingCatalog.setCreatedBy(buildingCatalog.getCreatedBy());
                return newBuildingCatalog;
            }).toList();
            buildingCatalogDao.saveAll(buildingCatalogsWithBuildingId, connection);

            List<RailwayLineStation> railwayLineStations = railwayLineStationMapper.MapperToRailwayLineStation(record.get("railway_line_stations"), Math.toIntExact(buildingId), partnerId, now);
            List<RailwayLineStation> railwayLineStationsWithBuildingId = railwayLineStations.stream().map(railwayLineStation -> {
                RailwayLineStation newRailwayLineStation = new RailwayLineStation();
                newRailwayLineStation.setBuildingId(buildingId);
                newRailwayLineStation.setLineId(railwayLineStation.getLineId());
                newRailwayLineStation.setStationId(railwayLineStation.getStationId());
                newRailwayLineStation.setRailwayName(railwayLineStation.getRailwayName());
                newRailwayLineStation.setStationName(railwayLineStation.getStationName());
                newRailwayLineStation.setOtherTransportation(railwayLineStation.getOtherTransportation());
                newRailwayLineStation.setWalkingTime(railwayLineStation.getWalkingTime());
                newRailwayLineStation.setSort(railwayLineStation.getSort());
                newRailwayLineStation.setCreatedAt(railwayLineStation.getCreatedAt());
                newRailwayLineStation.setCreatedBy(railwayLineStation.getCreatedBy());
                return newRailwayLineStation;
            }).toList();
            railwayLineStationDao.saveAll(railwayLineStationsWithBuildingId, connection);

        } else {
            buildingId = Long.valueOf(buildingManagementDao.lastID(connection));
        }
        Long buildingManagementId = buildingId;

        RoomManagement roomManagement = roomManagementMapper.MapperToRoomManagement(record, partnerId, Math.toIntExact(buildingManagementId), now);
        Long roomManagementId = roomManagementDao.saveRoom(roomManagement, connection);


        List<RoomImageManagement> roomImageManagements = roomImageMapper.MapperToRoomImageManagement(record.get("room_image_managements"), Math.toIntExact(roomManagementId), partnerId, now);

        //Save image
        String path = outputPath + File.separator + roomManagementId;
        List<String> imagesInLocal = new ArrayList<>();
        roomImageManagements.forEach(e -> {
            String fileName = FileUtils.saveImageToFile(e.getImagePath(), path);
            imagesInLocal.add(fileName);
        });

        String dbPath = "upload/list-image-room/";
        for (int i = 0; i < roomImageManagements.size(); i++) {
            roomImageManagements.get(i).setImagePath(dbPath + roomManagementId + "/" + imagesInLocal.get(i));
        }

        List<RoomImageManagement> roomImageManagementsWithRoomId = roomImageManagements.stream().map(roomImageManagement -> {
            RoomImageManagement newRoomImageManagement = new RoomImageManagement();
            newRoomImageManagement.setRoomManagementId(roomManagementId);
            newRoomImageManagement.setDescription(roomImageManagement.getDescription());
            newRoomImageManagement.setRoomType(roomImageManagement.getRoomType());
            newRoomImageManagement.setImagePath(roomImageManagement.getImagePath());
            newRoomImageManagement.setStatus(roomImageManagement.getStatus());
            newRoomImageManagement.setArrange(roomImageManagement.getArrange());
            newRoomImageManagement.setCreatedAt(roomImageManagement.getCreatedAt());
            newRoomImageManagement.setCreatedBy(roomImageManagement.getCreatedBy());
            return newRoomImageManagement;
        }).toList();
        roomImageManagementDao.saveAll(roomImageManagementsWithRoomId, connection);

        List<BasePriceOfRoom> basePriceOfRooms = basePriceOfRoomMapper.MapperToBasePriceOfRoom(record.get("base_price_of_rooms"), Math.toIntExact(roomManagementId), partnerId, now);
        List<BasePriceOfRoom> basePriceOfRoomsWithRoomId = basePriceOfRooms.stream().map(basePriceOfRoom -> {
            BasePriceOfRoom newBasePriceOfRoom = new BasePriceOfRoom();
            newBasePriceOfRoom.setRoomManagementId(roomManagementId);
            newBasePriceOfRoom.setBasePriceId(basePriceOfRoom.getBasePriceId());
            newBasePriceOfRoom.setRentFee(basePriceOfRoom.getRentFee());
            newBasePriceOfRoom.setLightAndHeatFee(basePriceOfRoom.getLightAndHeatFee());
            newBasePriceOfRoom.setCleaningFee(basePriceOfRoom.getCleaningFee());
            newBasePriceOfRoom.setCreatedAt(basePriceOfRoom.getCreatedAt());
            newBasePriceOfRoom.setCreatedBy(basePriceOfRoom.getCreatedBy());
            return newBasePriceOfRoom;
        }).toList();
        basePriceOfRoomDao.saveAll(basePriceOfRoomsWithRoomId, connection);

        List<RoomCatalog> roomCatalogs = roomCatalogMapper.MapperToRoomCatalog(record.get("room_catalogs"), Math.toIntExact(roomManagementId), partnerId, now);
        List<RoomCatalog> roomCatalogsWithRoomId = roomCatalogs.stream().map(roomCatalog -> {
            RoomCatalog newRoomCatalog = new RoomCatalog();
            newRoomCatalog.setRoomId(roomManagementId);
            newRoomCatalog.setCategoryCode(roomCatalog.getCategoryCode());
            newRoomCatalog.setInnerCode(roomCatalog.getInnerCode());
            newRoomCatalog.setCreatedAt(roomCatalog.getCreatedAt());
            newRoomCatalog.setCreatedBy(roomCatalog.getCreatedBy());
            return newRoomCatalog;
        }).toList();
        roomCatalogDao.saveAll(roomCatalogsWithRoomId, connection);

    }


}
