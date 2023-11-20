package com.app.smartdrive.api.entities.service_orders.enumerated;

import lombok.RequiredArgsConstructor;

public class EnumModuleServiceOrders {
    public enum SeroStatus {
        OPEN,
        CANCELLED,
        REJECT,
        CLOSED
    }

    public enum SeroOrdtType {
        CREATE,
        MODIFY,
        CLOSE
    }

    public enum SeotStatus {
        INPORGRESS,
        CANCELLED,
        COMPLETED
    }

    @RequiredArgsConstructor
    public enum ServType {
        OPEN("OPEN POLIS"),
        CLAIM("CLAIM POLIS"),
        CLOSE("CLOSE POLIS"),
        FEASIBILITY("FEASIBILITY POLIS");

        public final String label;
    }
}
