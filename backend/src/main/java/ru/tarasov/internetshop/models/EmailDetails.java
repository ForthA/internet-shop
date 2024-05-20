package ru.tarasov.internetshop.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailDetails {

    private String recipient;

    private String msgBody;

    private String subject;

    private String attachment;


}
