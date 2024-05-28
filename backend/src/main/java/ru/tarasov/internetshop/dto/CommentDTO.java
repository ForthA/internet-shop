package ru.tarasov.internetshop.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class CommentDTO {

    private double rating;

    private String commentary;

}
