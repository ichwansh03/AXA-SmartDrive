package com.app.smartdrive.entity.master;

public enum ModuleMaster {
    CHECK("FEASIBILITY"),
    SERVICE("POLIS", "CLAIM"),
    ORDER("CREATE", "MODIFY"),
    STATUS("OPEN", "PENDING", "CANCELLED", "CLOSED");

    private String[] descriptions;

    ModuleMaster(String... descriptions) {
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
