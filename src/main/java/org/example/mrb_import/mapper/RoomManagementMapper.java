package org.example.mrb_import.mapper;

import org.apache.commons.csv.CSVRecord;
import org.example.mrb_import.model.RoomManagement;
import org.example.mrb_import.utils.StringUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.example.mrb_import.utils.StringUtils.ConvertToInt;

@Component
public class RoomManagementMapper {
    public RoomManagement MapperToRoomManagement(CSVRecord record, Integer partnerId, Integer buildingId, LocalDateTime now) {
        RoomManagement roomManagement = new RoomManagement();
        roomManagement.setPartnerId(Long.valueOf(partnerId));
        roomManagement.setBuildingId(Long.valueOf(buildingId));
        roomManagement.setRoomNumber(record.isMapped("roomNumber") ? GetRoomNumber(record.get("roomNumber")) : "");
        roomManagement.setStatusRoomNumber(Integer.valueOf(record.get("status_room_number")));
        roomManagement.setRoomType(record.get("room_type"));
        roomManagement.setLicenseNumber(record.get("license_number"));
        roomManagement.setFloorDescription(record.get("floor_description"));
        if (!record.get("floor").isEmpty()) {
            roomManagement.setFloor(Integer.valueOf(StringUtils.ConvertToInt(record.get("floor"))));
        }
        roomManagement.setRoomSize(new BigDecimal(StringUtils.ConvertToDouble(record.get("room_size")))); // Ensure correct format of room size
        roomManagement.setCatchphrase(record.get("catchphrase"));
        roomManagement.setVideoUrl(record.get("video_url"));
        roomManagement.setPanoramaUrl(record.get("panorama_url"));
        roomManagement.setBedDetail(record.get("bed_detail"));
        roomManagement.setFacilityDetail(record.get("facility_detail"));
        roomManagement.setMailboxAccess(record.get("mailbox_access"));
        roomManagement.setOwnerInformation(record.get("owner_information"));
        roomManagement.setBusinessContactMemo(record.get("business_contact_memo"));
        roomManagement.setStatus(Integer.valueOf(record.get("status")));
        roomManagement.setCreatedAt(Timestamp.valueOf(now));
//        roomManagement.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        roomManagement.setCreatedBy(Long.valueOf(partnerId));
//        roomManagement.setUpdatedBy(Long.valueOf(roomManagementCsv.getUpdatedBy()));
        return roomManagement;
    }

    public String GetRoomNumber(String text) {
        Integer number = null;
        if (text.isEmpty() || text == null) {
            return "";
        } else {
            number = ConvertToInt(text);
        }
        return String.valueOf(number == null ? "" : number);
    }
}
