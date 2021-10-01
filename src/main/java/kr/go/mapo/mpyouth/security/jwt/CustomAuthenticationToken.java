package kr.go.mapo.mpyouth.security.jwt;


import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class CustomAuthenticationToken extends AbstractAuthenticationToken {
    private Object principal;
    private Object  credentials;



    public CustomAuthenticationToken(Object principal, Object  credentials) {
        super((Collection) null);
        this.principal = principal;
        this.credentials = credentials;
        this.setAuthenticated(false);
    }
    //인증 후의 토큰
    public CustomAuthenticationToken(Object principal, Object  credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        //인증 후에는 비밀번호를 웬만하면 제거하기 때문에 null로 들어온다. super.setAuthenticated(true);//인증 완료했다고 넣어주는 게 보인다.
    }


    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }
}