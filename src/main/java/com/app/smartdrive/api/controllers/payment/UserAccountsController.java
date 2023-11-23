package com.app.smartdrive.api.controllers.payment;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.smartdrive.api.dto.payment.UserAccountsDto;
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
    public ResponseEntity<?> findAllUserAccounts(){
        List<UserAccounts> steam = service.findAllUserAccounts();

        List<UserAccountsDto> listDto = steam.stream().map(user->{
            UserAccountsDto dto = new UserAccountsDto();
            dto.setUsac_accountno(user.getUsac_accountno());
            dto.setUsac_debet(user.getUsac_debet());
            dto.setUsac_credit(user.getUsac_credit());
            dto.setEnumPaymentType(user.getEnumPaymentType());
            return dto;
        }).collect(Collectors.toList());
        return new ResponseEntity<>(listDto,HttpStatus.OK);

    }

    @GetMapping("/userAccounts/{usac_id}")
    public ResponseEntity<?> findUserAccountsById(@Valid @PathVariable("usac_id")Long usac_id){
        List<UserAccounts> listUa = service.findAllUserAccounts();
        Optional<UserAccounts> uAId = service.findByIdUserAcc(usac_id);
        UserAccountsDto dto = new UserAccountsDto();

        if(uAId.isPresent()){
            for (UserAccounts ua : listUa) {
                if(usac_id.equals(ua.getUsac_id())){
                    dto.setUsac_accountno(ua.getUsac_accountno());
                    dto.setUsac_debet(ua.getUsac_debet());
                    dto.setUsac_credit(ua.getUsac_credit());
                    dto.setEnumPaymentType(ua.getEnumPaymentType());
                }
            }
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Id tidak ditemukan", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/userAccounts/{usac_id}/update")
    public ResponseEntity<?> updateUserAccounts(@Valid @PathVariable("usac_id")Long usac_id,
    @RequestParam(name = "usac_accountno" ,required= true) String usac_accountno,
    @RequestParam(name = "usac_debet", required = true) Double usac_debet,
    @RequestParam(name = "usac_credit", required = true) Double usac_credit,
    @RequestParam(name = "EnumPaymentType", required = true) EnumPaymentType enumPaymentType)
    {
        Optional<UserAccounts> idUA = service.findByIdUserAcc(usac_id);
        UserAccountsDto dto = new UserAccountsDto();
        if(idUA.isPresent()){
            UserAccounts userAccounts = idUA.get();
            userAccounts.setUsac_accountno(usac_accountno);
            userAccounts.setUsac_debet(usac_debet);
            userAccounts.setUsac_credit(usac_credit);
            userAccounts.setEnumPaymentType(enumPaymentType);
            service.updateUserAcc(userAccounts);

            dto.setUsac_accountno(userAccounts.getUsac_accountno());
            dto.setUsac_debet(userAccounts.getUsac_debet());
            dto.setUsac_credit(userAccounts.getUsac_credit());
            dto.setEnumPaymentType(userAccounts.getEnumPaymentType());
            return new ResponseEntity<>(dto, HttpStatus.OK);

        }else{
        return new ResponseEntity<>("Update Gagal", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    

    
}
