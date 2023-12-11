package com.smartdrive.masterservice.entities.Enum;

public enum EnumModuleMaster {
    CHECK,
    SERVICES,
    ORDER,
    STATUS;

    public enum TetyGroup {
        SERVICES,
        ORDER,
        STATUS
    }

    public enum Tety {
        CHECK_FEASIBILITY(EnumModuleMaster.CHECK, "FEASIBILITY"),
        SERVICE_POLIS(EnumModuleMaster.SERVICES, "CREATE_POLIS"),
        SERVICE_CLAIM(EnumModuleMaster.SERVICES, "CLAIM"),
        ORDER_CREATE(EnumModuleMaster.ORDER, "CREATE"),
        ORDER_MODIFY(EnumModuleMaster.ORDER, "MODIFY"),
        STATUS_OPEN(EnumModuleMaster.STATUS, "OPEN"),
        STATUS_PENDING(EnumModuleMaster.STATUS, "PENDING"),
        STATUS_CANCELLED(EnumModuleMaster.STATUS, "CANCELLED"),
        STATUS_CLOSED(EnumModuleMaster.STATUS, "CLOSED");

        private final EnumModuleMaster module;
        private final String description;

        Tety(EnumModuleMaster module, String description) {
            this.module = module;
            this.description = description;
        }
    }
}
