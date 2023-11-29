package com.app.smartdrive.api.entities.users;

public class EnumUsers {
  public enum roleName{
    CU("Customer"), //Customer
    EM("Employee"), //Employee
    PC("Potential Customer"), //Potential Customer
    PR("Partner"); //Partner

    private final String value;
    private roleName(String value){
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
