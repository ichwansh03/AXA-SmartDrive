package com.app.smartdrive.api.controllers.payment;

import java.util.List;

import com.app.smartdrive.api.dto.payment.Response.UserAccounts.UserAccountsDtoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.smartdrive.api.dto.payment.Request.UserAccounts.UserAccountsDtoRequests;
import com.app.smartdrive.api.services.payment.UserAccountsService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class UserAccountsController {
    private final UserAccountsService service;

//    @GetMapping("/userAccounts/all")
//    public ResponseEntity<?> userAccountsAll(){
//        List<UserAccountsListDtoResponse> listDto = service.listDtoResponses();
//        return new ResponseEntity<>(listDto, HttpStatus.OK);
//    }
//
//    @GetMapping("/userAccounts/{usac_id}")
//    public ResponseEntity<?> userAccountsById(@Valid @PathVariable("usac_id") Long usac_id){
//        UserAccountsListDtoResponse userAccountsDtos = service.getIdUser(usac_id);
//        return new ResponseEntity<>(userAccountsDtos, HttpStatus.OK);
//    }

    @PostMapping("/userAccounts/add")
    public ResponseEntity<?> addDebit(@Valid @RequestBody UserAccountsDtoRequests requests) {

        UserAccountsDtoResponse addDebit = service.addDebet(requests);

        return new ResponseEntity<>(addDebit, HttpStatus.OK);


    }


//    @DeleteMapping("/userAccounts/delete")
//    public ResponseEntity<?> deleteUserAccounts(@Valid @RequestBody UserAccountsDtoRequestsFirst request){
//        Boolean deleteUser = service.deleteUserAccountsByNoRek(request);
//        return new ResponseEntity<>(deleteUser, HttpStatus.OK);
//    }
//
//}
}