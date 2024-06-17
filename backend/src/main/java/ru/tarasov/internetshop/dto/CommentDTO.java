package ru.tarasov.internetshop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO {

    private double rating;

    private String commentary;

    private int productId;

}
