package com.agencia.agencia.security;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.stereotype.Service;
import com.agencia.agencia.service.UsuarioService;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UsuarioService usuarioService;

    public CustomOAuth2UserService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest req) {
        OAuth2User oauth2User = super.loadUser(req);
        usuarioService.loginOrRegisterOAuth(oauth2User);
        return oauth2User;
    }
}