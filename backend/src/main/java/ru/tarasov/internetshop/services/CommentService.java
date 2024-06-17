package ru.tarasov.internetshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import ru.tarasov.internetshop.dto.CommentDTO;
import ru.tarasov.internetshop.models.Comment;
import ru.tarasov.internetshop.models.Person;
import ru.tarasov.internetshop.models.Product;
import ru.tarasov.internetshop.repositories.CommentRepository;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    private final PersonService personService;

    private final ProductService productsService;
    @Autowired
    public CommentService(CommentRepository commentRepository, PersonService personService, ProductService productsService) {
        this.commentRepository = commentRepository;
        this.personService = personService;
        this.productsService = productsService;
    }

    public List<Comment> findAllComments(){
        return commentRepository.findAll(Sort.by("rating").descending());
    }

    public void saveComment(CommentDTO commentDTO, MultipartFile multipartFile, String username){
        String uploadDir = "backend/src/main/resources/images/" + multipartFile.getOriginalFilename();

        Path uploadPath = Paths.get(uploadDir);

        Person person = personService.loadUserByUsername(username).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "При сохранение комментария пользователь не был найден")
        );

        Product product = productsService.findProductById(commentDTO.getProductId());

        try{
            Comment comment = new Comment();
            comment.setCommentary(commentDTO.getCommentary());
            comment.setRating(commentDTO.getRating());
            comment.setImagePath(uploadDir);
            comment.setPerson(person);
            comment.setProduct(product);
            commentRepository.save(comment);
            multipartFile.transferTo(uploadPath);

        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Комментарий не был создан");
        }
    }

}
