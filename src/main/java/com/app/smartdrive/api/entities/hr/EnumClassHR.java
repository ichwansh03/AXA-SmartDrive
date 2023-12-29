package com.app.smartdrive.api.entities.hr;

public class EnumClassHR {
    
    public enum status {
        ACTIVE,
        INACTIVE
    }

    public enum emp_graduate {
        SMA("SMA"),
        SMK("SMK"),
        D1("D1"),
        D2("D2"),
        D3("D3"),

        S1("S1"),
        S2("S2");

        private final String value;
        private emp_graduate(String value){
            this.value = value;
        }
        public String getValue(){
            return value;
        }

    }



    public enum emp_type{
        CONTRACT,
        PERMANENT
    }
    
}
