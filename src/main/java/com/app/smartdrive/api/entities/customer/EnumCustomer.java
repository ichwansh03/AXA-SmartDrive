package com.app.smartdrive.api.entities.customer;

public class EnumCustomer {
    public enum CreqStatus{
        OPEN,
        CLOSED,
        CANCELLED,
        REJECT
    }

    public enum CreqType{
        POLIS,
        CLAIM
    }

    public enum CadocCategory{
        KTP,
        SIUP,
        TDP
    }

    public enum CreqPaidType{
        CASH,
        CREDIT
    }
}
