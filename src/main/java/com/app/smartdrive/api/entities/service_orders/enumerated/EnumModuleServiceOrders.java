package com.app.smartdrive.api.entities.service_orders.enumerated;

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

    public enum ServType {
        POLIS,
        CLAIM
    }
}
