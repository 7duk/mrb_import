package org.example.mrb_import.model;

import java.io.Serializable;

public class RoomCatalogId implements Serializable {
    private Long roomId;
    private Long categoryCode;
    private Long innerCode;

    // Constructors, equals(), and hashCode() methods
    public RoomCatalogId() {}

    public RoomCatalogId(Long roomId, Long categoryCode, Long innerCode) {
        this.roomId = roomId;
        this.categoryCode = categoryCode;
        this.innerCode = innerCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoomCatalogId)) return false;

        RoomCatalogId that = (RoomCatalogId) o;

        if (!roomId.equals(that.roomId)) return false;
        if (!categoryCode.equals(that.categoryCode)) return false;
        return innerCode.equals(that.innerCode);
    }

    @Override
    public int hashCode() {
        int result = roomId.hashCode();
        result = 31 * result + categoryCode.hashCode();
        result = 31 * result + innerCode.hashCode();
        return result;
    }
}
