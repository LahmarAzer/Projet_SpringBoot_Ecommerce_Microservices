package tn.esprit.claimfacturesservice.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.claimfacturesservice.Service.EmailService;
import tn.esprit.claimfacturesservice.dtoEntities.MailRequest;
import tn.esprit.claimfacturesservice.dtoEntities.MailResponse;

import java.util.HashMap;
import java.util.Map;


@SpringBootApplication
@RestController
@RequestMapping("/claimFacture/mail")
public class SendingMailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/sendingEmail")
    public MailResponse sendEmail(@RequestBody MailRequest request) {
        Map<String, Object> model = new HashMap<>();
        model.put("Name", request.getName());
        model.put("location", "Tunis,Tunisie");
        return emailService.sendEmail(request, model);

    }

    public static void main(String[] args) {
        SpringApplication.run(SendingMailController.class, args);
    }
}