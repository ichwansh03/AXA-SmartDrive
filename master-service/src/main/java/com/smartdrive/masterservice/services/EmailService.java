package com.smartdrive.masterservice.services;

import com.smartdrive.masterservice.dto.request.EmailReq;

public interface EmailService {
    void sendMail(EmailReq emailReq);
}
