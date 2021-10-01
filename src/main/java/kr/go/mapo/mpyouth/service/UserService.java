package kr.go.mapo.mpyouth.service;

import kr.go.mapo.mpyouth.common.ApiException;
import kr.go.mapo.mpyouth.domain.*;
import kr.go.mapo.mpyouth.payload.request.*;
import kr.go.mapo.mpyouth.payload.response.UserInfoResponse;
import kr.go.mapo.mpyouth.repository.AuthEmailRepository;
import kr.go.mapo.mpyouth.repository.OrganizationRepository;
import kr.go.mapo.mpyouth.repository.RoleRepository;
import kr.go.mapo.mpyouth.repository.UserRepository;
import kr.go.mapo.mpyouth.security.utils.JwtUtils;
import kr.go.mapo.mpyouth.security.utils.RedisUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static kr.go.mapo.mpyouth.common.ExceptionEnum.*;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthEmailRepository authEmailRepository;
    private final JavaMailSender mailSender;
    private final JwtUtils jwtUtils;
    private final RoleRepository roleRepository;
    private final OrganizationRepository organizationRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisUtils redisUtils;

    public void sendSearchId(SearchIdRequest searchIdRequest) throws MessagingException {

        String email = searchIdRequest.getEmail();

        if(email==null){
            System.out.println("이거 실행");
            throw new ApiException(NULL_EMAIL);
        }


        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException(NOT_FOUND_USER_WITH_EMAIL));

        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(msg, true, "UTF-8");

        messageHelper.setSubject("마포유스 아이디 찾기 메일입니다.");
        messageHelper.setText("아이디는 " + user.getAdminLoginId() + " 입니다.");
        messageHelper.setTo(user.getEmail());
        msg.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(user.getEmail()));
        mailSender.send(msg);
    }


    public void sendAuthKey(SearchPasswordRequest searchPasswordRequest) throws MessagingException {
        String adminLoginId = searchPasswordRequest.getAdminLoginId();
        String email = searchPasswordRequest.getEmail();

        User user = userRepository.findByAdminLoginIdAndEmail(adminLoginId, email)
                .orElseThrow(() -> new ApiException(NOT_FOUND_USER_WITH_EMAIL_OR_ADMIN_LOGIN_ID));

        AuthKey authKey = new AuthKey();
        AuthEmail authEmail = new AuthEmail();
        authEmail.setUser(user);
        authEmail.setAuthKey(authKey.getKey(6, false));

        authEmailRepository.save(authEmail);

        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(msg, true, "UTF-8");

        messageHelper.setSubject("마포유스 비밀번호 찾기 메일입니다.");
        messageHelper.setText(adminLoginId + "님의 비밀번호 인증코드는 " + authEmail.getAuthKey() + " 입니다.");
        messageHelper.setTo(email);
        msg.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(email));
        mailSender.send(msg);
    }

    public void initPassword(InitPasswordRequest initPasswordRequest) throws MessagingException {
        String requestAuthKey = initPasswordRequest.getAuthKey();
        String adminLoginId = initPasswordRequest.getAdminLoginId();
        String email = initPasswordRequest.getEmail();

        User user = userRepository.findByAdminLoginIdAndEmail(adminLoginId, email)
                .orElseThrow(() -> new ApiException(NOT_FOUND_USER_WITH_EMAIL_OR_ADMIN_LOGIN_ID));


        AuthEmail authEmail = authEmailRepository.findByUser(user)
                .orElseThrow(() ->
                        new ApiException(NOT_FOUND_USER_WITH_EMAIL_OR_ADMIN_LOGIN_ID));

        String authKey = authEmail.getAuthKey();

        if (requestAuthKey.equals(authKey)) {
            AuthKey password = new AuthKey();
            String initPassword = password.getKey(10, false);
            user.setPassword(initPassword);
            userRepository.save(user);
            authEmailRepository.delete(authEmail);

            MimeMessage msg = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(msg, true, "UTF-8");

            messageHelper.setSubject("마포유스 임시 비밀번호 발급 메일입니다.");
            messageHelper.setText(adminLoginId + "님의 임시 비밀번호는 " + initPassword + " 입니다.");
            messageHelper.setTo(email);
            msg.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(email));
            mailSender.send(msg);

        } else {
            authEmailRepository.delete(authEmail);
            throw new ApiException(BAD_AUTH_KEY_ERROR);
        }
    }


    @Transactional
    public void updateUser(HttpServletRequest request, ModifyUserInfoRequest modifyUserInfoRequest) {

        String jwt = jwtUtils.parseJwt(request);
        String adminLoginId = jwtUtils.getUserNameFromJwtToken(jwt);
        Organization organization = null;

        User user = userRepository.findByAdminLoginId(adminLoginId)
                .orElseThrow(() ->
                        new ApiException(NOT_FOUND_USER));

        if (modifyUserInfoRequest.getOrganizationId() != null) {
            organization = organizationRepository.findById(modifyUserInfoRequest.getOrganizationId())
                    .orElseThrow(() ->
                            new ApiException(NOT_FOUND_ORGANIZATION_WITH_ORGANIZATION_ID));
            user.setOrganization(organization);
        }

        if (modifyUserInfoRequest.getUsername() != null)
            user.setUsername(modifyUserInfoRequest.getUsername());
        if (modifyUserInfoRequest.getEmail() != null)
            user.setEmail(modifyUserInfoRequest.getEmail());
        if (modifyUserInfoRequest.getPhone() !=null)
            user.setPhone(modifyUserInfoRequest.getPhone());
        if (modifyUserInfoRequest.getRoles() != null) {
            Set<String> strRoles = modifyUserInfoRequest.getRoles();
            Set<Role> roles = new HashSet<>();

            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;


                    default:
                        Role managerRole = roleRepository.findByName(ERole.ROLE_MANAGER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        user.setRoles(roles);
                        userRepository.save(user);
                        roles.add(managerRole);
                }
            });

            user.setRoles(roles);

        }
        userRepository.save(user);
    }

    @Transactional
    public void updateUserPassword(HttpServletRequest request, ModifyUserPasswordRequest modifyUserPasswordRequest) {

        String jwt = jwtUtils.parseJwt(request);
        String adminLoginId = jwtUtils.getUserNameFromJwtToken(jwt);
        String currentPassword = modifyUserPasswordRequest.getCurrentPassword();
        String newPassword = modifyUserPasswordRequest.getNewPassword();
        String checkNewPassword = modifyUserPasswordRequest.getCheckNewPassword();

        User user = userRepository.findByAdminLoginId(adminLoginId)
                .orElseThrow(() ->
                        new ApiException(NOT_FOUND_USER));


        
        if (passwordEncoder.matches(currentPassword,user.getPassword())) {
            if(newPassword.equals(checkNewPassword)) {
                user.setPassword(passwordEncoder.encode(newPassword));
                userRepository.save(user);
            }
            else{
               throw new ApiException(NEW_CREDENTIAL_AND_CHECK_NEW_CREDENTIAL_SAME_ERROR);
            }
        } else {
            throw new ApiException(CURRENT_BAD_CREDENTIAL_ERROR);
        }
    }

    public List<UserInfoResponse> findUsers(){
        List<User> users = userRepository.findAll();
        return users.stream().map(UserInfoResponse::new)
                .collect(Collectors.toList());
    }
    public  UserInfoResponse findOne(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ApiException(NOT_FOUND_USER));
        return  new UserInfoResponse(user);
    }
    public UserInfoResponse findMe(HttpServletRequest request){
        String jwt = jwtUtils.parseJwt(request);
        String adminLoginId = jwtUtils.getUserNameFromJwtToken(jwt);
        User user = userRepository.findByAdminLoginId(adminLoginId)
                .orElseThrow(() ->
                        new ApiException(NOT_FOUND_USER));
        return  new UserInfoResponse(user);
    }

    public void deleteUser(Long userId){
        User user =userRepository.findById(userId)
                .orElseThrow(() ->
                        new ApiException(NOT_FOUND_USER));
        redisUtils.deleteData(user.getAdminLoginId());
        userRepository.deleteById(userId);
    }

}

