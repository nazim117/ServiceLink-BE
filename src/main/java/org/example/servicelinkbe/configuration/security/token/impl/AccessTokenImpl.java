package org.example.servicelinkbe.configuration.security.token.impl;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.example.servicelinkbe.configuration.security.token.AccessToken;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@EqualsAndHashCode
@Getter
public class AccessTokenImpl implements AccessToken {
    private final String subject;
    private final Integer userId;
    private final Set<String> roles;

    public AccessTokenImpl(String subject, Integer userId, Collection<String> roles) {
        this.subject = subject;
        this.userId = userId;
        this.roles = roles != null ? Set.copyOf(roles) : Collections.emptySet();
    }

    @Override
    public boolean hasRole(String roleName) {return this.roles.contains(roleName);}
}
