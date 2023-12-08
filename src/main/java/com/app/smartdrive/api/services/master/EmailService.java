package com.app.smartdrive.api.services.master;

import com.app.smartdrive.api.dto.EmailReq;

public interface EmailService {
    void sendMail(EmailReq emailReq);
}
