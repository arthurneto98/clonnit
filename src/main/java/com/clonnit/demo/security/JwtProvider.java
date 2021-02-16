package com.clonnit.demo.security;

import com.clonnit.demo.exceptions.ClonnitException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;

import static io.jsonwebtoken.Jwts.parser;

@Service
public class JwtProvider {
    private KeyStore keyStore;

    @PostConstruct
    public void init() {
        try {
            keyStore = KeyStore.getInstance("JKS");
            InputStream resourceAsStream = getClass().getResourceAsStream("/clonnitkeystore.jks");
            keyStore.load(resourceAsStream, "clonnitpass".toCharArray());
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
            throw new ClonnitException("Erro carregando chaves");
        }
    }

    public String generateToken(Authentication authentication) {
        User principal = (User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .signWith(getPrivateKey())
                .compact();
    }

    public Boolean validateToken(String jwtToken) {
        if (StringUtils.hasText(jwtToken)) {
            return Boolean.FALSE;
        }

        parser().setSigningKey(getPublicKey()).parseClaimsJwt(jwtToken);
        return Boolean.TRUE;
    }

    public String getUsernameFromJwt(String token) {
        Claims claims = parser()
                .setSigningKey(getPublicKey())
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    private PrivateKey getPrivateKey() {
        try {
            return (PrivateKey) keyStore.getKey("clonnitkeystore", "clonnitpass".toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new ClonnitException("Erro retornando chave pública");
        }
    }

    private PublicKey getPublicKey() {
        try {
            return keyStore.getCertificate("clonnitkeystore").getPublicKey();
        } catch (KeyStoreException e) {
            throw new ClonnitException("Erro carregando chaves");
        }
    }
}