package com.app.smartdrive.api.entities.master;

public enum EnumModuleMaster {
    CHECK("FEASIBILITY"),
    SERVICE("POLIS", "CLAIM"),
    ORDER("CREATE", "MODIFY"),
    STATUS("OPEN", "PENDING", "CANCELLED", "CLOSED");

    private String[] descriptions;

    EnumModuleMaster(String... descriptions) {
        this.descriptions = descriptions;
    }

    public String[] getDescriptions() {
        return descriptions;
    }

    public enum TetyGroup {
        SERVICE,
        ORDER,
        STATUS
    }
}
