package org.example.servicelinkbe.configuration.security.token;

public interface AccessTokenEncoder {
    String encode(AccessToken accessToken);
}
