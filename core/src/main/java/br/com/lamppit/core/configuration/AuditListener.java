package br.com.lamppit.core.configuration;

import br.com.lamppit.core.model.User;
import br.com.lamppit.core.model.BaseEntity;
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
    private void beforeUpdate(BaseEntity entity) throws SignatureException, ExpiredJwtException,
            UnsupportedJwtException, MalformedJwtException, IllegalArgumentException, Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = (String) authentication.getCredentials();
        User u = jwtUtilities.extractUser(token);

        entity.setModifiedByUser(u.getId());
    }

    @PreRemove
    private void beforeDelete(BaseEntity entity) throws SignatureException, ExpiredJwtException,
            UnsupportedJwtException, MalformedJwtException, IllegalArgumentException, Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = (String) authentication.getCredentials();

        entity.setDeletedByUser(jwtUtilities.extractUser(token).getId());
    }

    @PrePersist
    private void beforeInsert(BaseEntity entity) throws SignatureException, ExpiredJwtException,
            UnsupportedJwtException, MalformedJwtException, IllegalArgumentException, Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = (String) authentication.getCredentials();

        entity.setCreatedByUser(jwtUtilities.extractUser(token).getId());
    }

}
