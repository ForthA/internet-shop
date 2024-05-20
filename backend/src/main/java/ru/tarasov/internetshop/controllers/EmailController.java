package ru.tarasov.internetshop.controllers;


import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tarasov.internetshop.services.EmailService;
import ru.tarasov.internetshop.services.PersonService;

@RestController
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;

    Logger logger = LoggerFactory.getLogger(EmailController.class);

    private final PersonService personService;

    @Autowired
    public EmailController(EmailService emailService, PersonService personService) {
        this.emailService = emailService;
        this.personService = personService;
    }


    @Operation(summary = "Отправить сообщение на mail пользователя", tags = {"send"})
    @PostMapping("/send_mail")
    public ResponseEntity<HttpStatus> sendEmail(UsernamePasswordAuthenticationToken auth){
        try {
            emailService.sendMail(personService.loadUserByUsername(auth.getName()).get());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
