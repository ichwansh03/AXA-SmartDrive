package com.smartdrive.userservice.entities;

public class EnumUsers {
  public enum RoleName{
    CU("Customer"), //Customer
    EM("Employee"), //Employee
    PC("Potential Customer"), //Potential Customer
    PR("Partner"), //Partner
    AD("Admin");

    private final String value;
    private RoleName(String value){
      this.value = value;
    }
    public String getValue(){
      return value;
    }
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
