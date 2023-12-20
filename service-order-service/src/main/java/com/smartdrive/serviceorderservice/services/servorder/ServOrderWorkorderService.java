package com.smartdrive.serviceorderservice.services.servorder;


import com.smartdrive.serviceorderservice.entities.ServiceOrderTasks;
import com.smartdrive.serviceorderservice.entities.ServiceOrderWorkorder;

import java.util.List;

public interface ServOrderWorkorderService {

    List<ServiceOrderWorkorder> addSowoList(List<ServiceOrderTasks> seotList);

    List<ServiceOrderWorkorder> findSowoBySeotId(Long seotId);

    int updateSowoStatus(Boolean sowoStatus, Long sowoId);

    boolean checkAllWorkComplete(List<ServiceOrderWorkorder> sowoList);

    void createWorkorderTask(String sowoName, Long seotId);
}
