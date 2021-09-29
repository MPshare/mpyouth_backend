package kr.go.mapo.mpyouth.security.jwt;

public class UsernameFromTokenException extends RuntimeException{
    public UsernameFromTokenException(String message){
        super(message);
    }
}