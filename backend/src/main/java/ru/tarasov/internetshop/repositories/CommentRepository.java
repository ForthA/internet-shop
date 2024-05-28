package ru.tarasov.internetshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tarasov.internetshop.models.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {



}
