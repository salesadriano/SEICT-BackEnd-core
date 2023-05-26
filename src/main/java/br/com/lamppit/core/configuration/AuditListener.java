package br.com.lamppit.core.configuration;

import br.com.lamppit.core.entity.EntityBase;
import br.com.lamppit.core.entity.User;
import br.com.lamppit.core.util.JwtUtilities;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuditListener {

    @Autowired
    private JwtUtilities jwtUtilities;

    @PreUpdate
    private void beforeUpdate(EntityBase entity) throws SignatureException, ExpiredJwtException,
            UnsupportedJwtException, MalformedJwtException, IllegalArgumentException, Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = (String) authentication.getCredentials();
        User u = jwtUtilities.extractUser(token);

        entity.setModifiedByUser(u);
    }

    @PreRemove
    private void beforeDelete(EntityBase entity) throws SignatureException, ExpiredJwtException,
            UnsupportedJwtException, MalformedJwtException, IllegalArgumentException, Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = (String) authentication.getCredentials();

        entity.setDeletedByUser(jwtUtilities.extractUser(token));
    }

    @PrePersist
    private void beforeInsert(EntityBase entity) throws SignatureException, ExpiredJwtException,
            UnsupportedJwtException, MalformedJwtException, IllegalArgumentException, Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = (String) authentication.getCredentials();

        entity.setCreatedByUser(jwtUtilities.extractUser(token));
    }

}
