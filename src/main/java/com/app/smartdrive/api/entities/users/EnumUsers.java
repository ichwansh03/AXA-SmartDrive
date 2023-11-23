package com.app.smartdrive.api.entities.users;

public class EnumUsers {
  public enum roleName{
    CU, //Customer
    EM, //Employee
    PC, //Potential Customer
    PR, //Partner
  }

  public enum usmiPaidType{
    CASH,
    CREDIT,
  }

  public enum usmiStatus{
    NORMAL,
    PENDING,
    DUEDATE,
    CLOSED
  }
}
