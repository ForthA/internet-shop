package ru.tarasov.internetshop.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.tarasov.internetshop.models.Cart;
import ru.tarasov.internetshop.models.EmailDetails;
import ru.tarasov.internetshop.models.Person;
import ru.tarasov.internetshop.models.Product;

import java.util.List;

@Service
public class EmailService {

    Logger logger = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    private final CartService cartService;

    @Autowired
    public EmailService(JavaMailSender javaMailSender, CartService cartService) {
        this.javaMailSender = javaMailSender;
        this.cartService = cartService;
    }

    public void sendMail(Person person){
        try{
            EmailDetails details = formDetails(person);
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());

            javaMailSender.send(mailMessage);
        }

        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private EmailDetails formDetails(Person person){
        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setRecipient(person.getEmail());
        emailDetails.setMsgBody(attachMessage(person));
        emailDetails.setSubject("Интернет-заказ");
        return emailDetails;
    }

    private String attachMessage(Person person){
        StringBuilder stringBuilder = new StringBuilder();
        List<Cart> productList = cartService.findByPersonName(person.getName());
        stringBuilder.append("Чек: \n");
        if (productList.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        for (Cart cart : productList){
            stringBuilder.append(cart.getProduct().getTitle());
            stringBuilder.append("\n Количество: ");
            stringBuilder.append(cart.getAmount());
            stringBuilder.append("\n Цена: ");
            stringBuilder.append(cart.getProduct().getPrice() * cart.getAmount());
            stringBuilder.append("\n");
        }
        stringBuilder.append("\n Общая цена: ");
        stringBuilder.append(cartService.getPriceCart(person.getName()).getPrice());

        return stringBuilder.toString();
    }

}