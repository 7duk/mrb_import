package org.example.mrb_import.model;

import java.io.Serializable;

public class BasePriceOfRoomId implements Serializable {
    private Long roomManagementId;
    private Long basePriceId;

    // Constructors, equals(), and hashCode() methods
    public BasePriceOfRoomId() {}

    public BasePriceOfRoomId(Long roomManagementId, Long basePriceId) {
        this.roomManagementId = roomManagementId;
        this.basePriceId = basePriceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BasePriceOfRoomId)) return false;

        BasePriceOfRoomId that = (BasePriceOfRoomId) o;

        if (!roomManagementId.equals(that.roomManagementId)) return false;
        return basePriceId.equals(that.basePriceId);
    }

    @Override
    public int hashCode() {
        int result = roomManagementId.hashCode();
        result = 31 * result + basePriceId.hashCode();
        return result;
    }
}