package kr.go.mapo.mpyouth.service;

import kr.go.mapo.mpyouth.common.ApiException;
import kr.go.mapo.mpyouth.domain.AuthEmail;
import kr.go.mapo.mpyouth.domain.AuthKey;
import kr.go.mapo.mpyouth.domain.User;
import kr.go.mapo.mpyouth.payload.request.*;
import kr.go.mapo.mpyouth.repository.AuthEmailRepository;
import kr.go.mapo.mpyouth.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.aspectj.weaver.ast.Test;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import static kr.go.mapo.mpyouth.common.ExceptionEnum.*;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthEmailRepository authEmailRepository;
    private final JavaMailSender mailSender;

    public void sendSearchId(SearchIdRequest searchIdRequest) throws MessagingException {

        String email = searchIdRequest.getEmail();
        System.out.println("email:"+email);
        User user =userRepository.findByEmail(email)
                .orElseThrow(() ->  new ApiException(NOT_FOUND_USER_WITH_EMAIL));

        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(msg, true, "UTF-8");

        messageHelper.setSubject("마포유스 아이디 찾기 메일입니다.");
        messageHelper.setText("아이디는 "+user.getAdminLoginId()+" 입니다.");
        messageHelper.setTo(user.getEmail());
        msg.setRecipients(MimeMessage.RecipientType.TO , InternetAddress.parse(user.getEmail()));
        mailSender.send(msg);
    }



    public void sendAuthKey(SearchPasswordRequest searchPasswordRequest) throws MessagingException {
        String adminLoginId = searchPasswordRequest.getAdminLoginId();
        String email  = searchPasswordRequest.getEmail();

        User user =  userRepository.findByAdminLoginIdAndEmail(adminLoginId,email)
                .orElseThrow(() -> new ApiException(NOT_FOUND_USER_WITH_EMAIL_OR_ADMIN_LOGIN_ID));

        AuthKey authKey = new AuthKey();
        AuthEmail authEmail = new AuthEmail();
        authEmail.setUser(user);
        authEmail.setAuthKey(authKey.getKey(6,false));

        authEmailRepository.save(authEmail);

        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(msg, true, "UTF-8");

        messageHelper.setSubject("마포유스 비밀번호 찾기 메일입니다.");
        messageHelper.setText(adminLoginId +"님의 비밀번호 인증코드는 "+authEmail.getAuthKey()+" 입니다.");
        messageHelper.setTo(email);
        msg.setRecipients(MimeMessage.RecipientType.TO , InternetAddress.parse(email));
        mailSender.send(msg);
    }

    public void initPassword(InitPasswordRequest initPasswordRequest) throws MessagingException {
        String requestAuthKey = initPasswordRequest.getAuthKey();
        String adminLoginId = initPasswordRequest.getAdminLoginId();
        String email = initPasswordRequest.getEmail();

        System.out.println("initPasswordRequest"+initPasswordRequest.getAdminLoginId());
        System.out.println("initPasswordRequest"+initPasswordRequest.getEmail());
        System.out.println("initPasswordReqeust"+initPasswordRequest.getAuthKey());

        User user =  userRepository.findByAdminLoginIdAndEmail(adminLoginId,email)
                .orElseThrow(() -> new ApiException(NOT_FOUND_USER_WITH_EMAIL_OR_ADMIN_LOGIN_ID));

        System.out.println("user:"+user.getUsername());


        AuthEmail authEmail = authEmailRepository.findByUser(user)
                .orElseThrow(() ->
                        new ApiException(NOT_FOUND_USER_WITH_EMAIL_OR_ADMIN_LOGIN_ID));

        System.out.println("authKey:"+authEmail.getAuthKey());

        String authKey = authEmail.getAuthKey();

        System.out.println("authKey:"+authKey);
        System.out.println("requestAuthKey:"+requestAuthKey);

        if(requestAuthKey.equals(authKey)){
            AuthKey password = new AuthKey();
            String initPassword = password.getKey(10, false);
            user.setPassword(initPassword);
            userRepository.save(user);
            authEmailRepository.delete(authEmail);

            MimeMessage msg = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(msg, true, "UTF-8");

            messageHelper.setSubject("마포유스 임시 비밀번호 발급 메일입니다.");
            messageHelper.setText(adminLoginId +"님의 임시 비밀번호는 " +initPassword+" 입니다.");
            messageHelper.setTo(email);
            msg.setRecipients(MimeMessage.RecipientType.TO , InternetAddress.parse(email));
            mailSender.send(msg);

        }else{
            authEmailRepository.delete(authEmail);
            throw new ApiException(BAD_AUTH_KEY_ERROR);
        }
    }



}
