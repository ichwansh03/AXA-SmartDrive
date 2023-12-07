package com.app.smartdrive.api.controllers.payment;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.smartdrive.api.dto.payment.Request.UserAccounts.UserAccountsDtoRequests;
import com.app.smartdrive.api.dto.payment.Response.UserAccounts.UserAccountsDtoResponse;
import com.app.smartdrive.api.entities.payment.UserAccounts;
import com.app.smartdrive.api.entities.payment.Enumerated.EnumClassPayment.EnumPaymentType;
import com.app.smartdrive.api.services.payment.UserAccountsService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class UserAccountsController {
    private final UserAccountsService service;

    @GetMapping("/userAccounts/all")
    public ResponseEntity<?> userAccountsAll(){
        List<UserAccountsDtoResponse> listDto = service.listDtoResponses();
        return new ResponseEntity<>(listDto, HttpStatus.OK);
    }

    @GetMapping("/userAccounts/{usac_id}")
    public ResponseEntity<?> userAccountsById(@Valid @PathVariable("usac_id") Long usac_id){
        UserAccountsDtoResponse userAccountsDtos = service.getIdUser(usac_id);
        return new ResponseEntity<>(userAccountsDtos, HttpStatus.OK);
    }

    @PostMapping("/userAccounts/{usac_id}/add")
    public ResponseEntity<?> addDebitCredit(@Valid @PathVariable("usac_id") Long usac_id,
    @ModelAttribute UserAccountsDtoRequests userAccountsDtoRequests){
        
        Boolean addDebitCredit = service.addDebitCredit(usac_id, userAccountsDtoRequests);
       
        return new ResponseEntity<>(addDebitCredit, HttpStatus.OK);
       
         
    }


    @PutMapping("/userAccounts/{usac_id}/update")
    public ResponseEntity<?> updateDebitCredit(@Valid @PathVariable("usac_id") Long usac_id,
    @ModelAttribute UserAccountsDtoRequests userAccountsDto){
        Boolean updateDebitCredits = service.updateDebitCredit(usac_id,userAccountsDto);
    
        return new ResponseEntity<>(updateDebitCredits, HttpStatus.OK);
    }
    
    @DeleteMapping("/userAccounts/{usac_id}/delete")
    public ResponseEntity<?> deleteUserAccounts(@Valid @PathVariable("usac_id") Long usac_id){
        Boolean deleteUser = service.deleteUAById(usac_id);
        return new ResponseEntity<>(deleteUser, HttpStatus.OK);
    }
    
}
