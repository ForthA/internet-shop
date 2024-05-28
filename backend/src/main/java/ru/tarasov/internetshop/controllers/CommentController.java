package ru.tarasov.internetshop.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.tarasov.internetshop.dto.CommentDTO;
import ru.tarasov.internetshop.services.CommentService;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping(value = "/add", consumes = {"multipart/form-data"})
    public ResponseEntity<?> addComment(@RequestParam("rating") double rating,
                                        @RequestParam("commentary") String commentary,
                                        @RequestParam("image") MultipartFile multipartFile,
                                        UsernamePasswordAuthenticationToken auth){

        String fileName = multipartFile.getOriginalFilename();
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setCommentary(commentary);
        commentDTO.setRating(rating);
        logger.info(fileName);
        commentService.saveComment(commentDTO, multipartFile, auth.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
