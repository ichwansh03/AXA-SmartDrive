package com.app.smartdrive.api.entities.master;

public enum EnumModuleMaster {
    CHECK,
    SERVICE,
    ORDER,
    STATUS;

    public enum TetyGroup {
        SERVICE,
        ORDER,
        STATUS
    }

    public enum Tety {
        CHECK_FEASIBILITY(EnumModuleMaster.CHECK, "FEASIBILITY"),
        SERVICE_POLIS(EnumModuleMaster.SERVICE, "POLIS"),
        SERVICE_CLAIM(EnumModuleMaster.SERVICE, "CLAIM"),
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
