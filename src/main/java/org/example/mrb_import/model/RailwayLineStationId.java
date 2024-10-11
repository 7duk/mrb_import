package org.example.mrb_import.model;

import java.io.Serializable;

public class RailwayLineStationId implements Serializable {
    private Long buildingId;
    private Long lineId;
    private Long stationId;

    // Constructors, equals(), and hashCode() methods
    public RailwayLineStationId() {}

    public RailwayLineStationId(Long buildingId, Long lineId, Long stationId) {
        this.buildingId = buildingId;
        this.lineId = lineId;
        this.stationId = stationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RailwayLineStationId)) return false;

        RailwayLineStationId that = (RailwayLineStationId) o;

        if (!buildingId.equals(that.buildingId)) return false;
        if (!lineId.equals(that.lineId)) return false;
        return stationId.equals(that.stationId);
    }

    @Override
    public int hashCode() {
        int result = buildingId.hashCode();
        result = 31 * result + lineId.hashCode();
        result = 31 * result + stationId.hashCode();
        return result;
    }
}