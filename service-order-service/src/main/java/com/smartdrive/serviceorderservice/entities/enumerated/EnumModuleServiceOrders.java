package com.smartdrive.serviceorderservice.entities.enumerated;

public class EnumModuleServiceOrders {

    public enum CreqType{
        POLIS,
        CLAIM,
        CLOSE,
        FEASIBLITY
    }

    public enum SeroStatus {
        OPEN,
        CANCELLED,
        REJECT,
        CLOSED
    }

    public enum SeroOrdtType {
        ALL,
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
