package kr.go.mapo.mpyouth.security.jwt;

import java.rmi.AccessException;

public class CustomExpiredJwtException extends AccessException {
    public CustomExpiredJwtException(String message){
        super(message);
    }
}
