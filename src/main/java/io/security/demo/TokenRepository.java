package io.security.demo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    public Token findBySeries(String series);

    void deleteByEmail(String username);
}
