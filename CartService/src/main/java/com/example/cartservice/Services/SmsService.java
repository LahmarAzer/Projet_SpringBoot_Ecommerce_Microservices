package com.example.cartservice.Services;

import com.twilio.http.TwilioRestClient;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SmsService {

    private final TwilioRestClient twilioRestClient;
    private final PhoneNumber fromPhoneNumber;

    public SmsService(TwilioRestClient twilioRestClient, PhoneNumber fromPhoneNumber) {
        this.twilioRestClient = twilioRestClient;
        this.fromPhoneNumber = fromPhoneNumber;
    }

    public void sendSms(Long toNumber, String message) {
        try {
            Message.creator(
                            new PhoneNumber(toNumber.toString()),
                            fromPhoneNumber,
                            message)
                    .create(twilioRestClient);
            log.info("SMS message sent to {}: {}", toNumber, message);
        } catch (Exception ex) {
            log.error("Failed to send SMS message to {}: {}", toNumber, message, ex);
        }
    }
}

