package ru.tarasov.internetshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.tarasov.internetshop.dto.CommentDTO;
import ru.tarasov.internetshop.models.Comment;
import ru.tarasov.internetshop.models.Person;
import ru.tarasov.internetshop.repositories.CommentRepository;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    private final PersonService personService;
    @Autowired
    public CommentService(CommentRepository commentRepository, PersonService personService) {
        this.commentRepository = commentRepository;
        this.personService = personService;
    }

    public void saveComment(CommentDTO commentDTO, MultipartFile multipartFile, String username){
        String uploadDir = "backend/src/main/resources/images/" + multipartFile.getOriginalFilename();

        Path uploadPath = Paths.get(uploadDir);

        Person person = personService.loadUserByUsername(username).get();

        try{
            Comment comment = new Comment();
            comment.setCommentary(commentDTO.getCommentary());
            comment.setRating(commentDTO.getRating());
            comment.setImagePath(uploadDir);
            comment.setPerson(person);
            commentRepository.save(comment);
            multipartFile.transferTo(uploadPath);
            //Path filePath = uploadPath.resolve(multipartFile.getOriginalFilename());
            //Files.copy(inputStream, uploadPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
