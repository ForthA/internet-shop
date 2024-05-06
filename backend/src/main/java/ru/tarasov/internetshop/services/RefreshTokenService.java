package ru.tarasov.internetshop.services;

import com.auth0.jwt.exceptions.TokenExpiredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tarasov.internetshop.exceptions.TokenRefreshException;
import ru.tarasov.internetshop.models.RefreshToken;
import ru.tarasov.internetshop.repositories.PersonRepository;
import ru.tarasov.internetshop.repositories.RefreshTokenRepository;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    private final PersonRepository personRepository;

    @Autowired
    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, PersonRepository personRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.personRepository = personRepository;
    }

    public Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(int userId){
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setPerson(personRepository.findById(userId).get());
        refreshToken.setExpiryDay(ZonedDateTime.now().plusMinutes(3600).toInstant());
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshTokenRepository.save(refreshToken);

        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token){
        if (token.getExpiryDay().compareTo(Instant.now()) < 0){
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(), "БАХ jwt испарился...");
        }
        return token;
    }

    public void deleteByToken(String token){
        refreshTokenRepository.deleteAllPersonTokensByToken(token);
    }

    @Transactional
    public int deleteByPersonId(int userId){
        return refreshTokenRepository.deleteByPerson(personRepository.findById(userId).get());
    }
}
