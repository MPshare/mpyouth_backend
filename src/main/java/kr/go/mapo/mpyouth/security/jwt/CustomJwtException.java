package kr.go.mapo.mpyouth.security.jwt;

import java.rmi.AccessException;

public class CustomJwtException extends AccessException {
    public CustomJwtException(String message){
        super(message);
    }
}
