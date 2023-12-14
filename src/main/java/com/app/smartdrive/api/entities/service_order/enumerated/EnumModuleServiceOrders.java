package com.app.smartdrive.api.entities.service_order.enumerated;

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
        INPROGRESS,
        CANCELLED,
        COMPLETED
    }

    public enum ServStatus {
        ACTIVE,
        INACTIVE
    }

    public enum SemiStatus {
        PAID,
        UNPAID,
        INACTIVE
    }
}
