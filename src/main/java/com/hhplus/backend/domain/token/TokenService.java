package com.hhplus.backend.domain.token;

import com.hhplus.backend.domain.payment.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    public TokenService (TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

}
