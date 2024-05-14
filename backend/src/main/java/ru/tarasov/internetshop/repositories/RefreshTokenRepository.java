package ru.tarasov.internetshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.tarasov.internetshop.models.Person;
import ru.tarasov.internetshop.models.RefreshToken;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
    Optional<RefreshToken> findByToken(String token);

    @Query(value = """
        delete from refresh_token
        where user_id in (select user_id from refresh_token where token = ?1)
        """, nativeQuery = true)
    void deleteAllPersonTokensByToken(String token);

    @Modifying
    int deleteByPerson(Person person);
}
