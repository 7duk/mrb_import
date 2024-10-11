package org.example.mrb_import.model;

import java.io.Serializable;

public class BuildingCatalogId implements Serializable {
    private Long buildingId;
    private Long categoryCode;
    private Long innerCode;

    // Constructors, equals(), and hashCode() methods
    public BuildingCatalogId() {}

    public BuildingCatalogId(Long buildingId, Long categoryCode, Long innerCode) {
        this.buildingId = buildingId;
        this.categoryCode = categoryCode;
        this.innerCode = innerCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BuildingCatalogId)) return false;

        BuildingCatalogId that = (BuildingCatalogId) o;

        if (!buildingId.equals(that.buildingId)) return false;
        if (!categoryCode.equals(that.categoryCode)) return false;
        return innerCode.equals(that.innerCode);
    }

    @Override
    public int hashCode() {
        int result = buildingId.hashCode();
        result = 31 * result + categoryCode.hashCode();
        result = 31 * result + innerCode.hashCode();
        return result;
    }

}