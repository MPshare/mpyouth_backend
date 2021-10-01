package kr.go.mapo.mpyouth.service;


import kr.go.mapo.mpyouth.common.ApiException;
import kr.go.mapo.mpyouth.common.ExceptionEnum;
import kr.go.mapo.mpyouth.domain.User;
import kr.go.mapo.mpyouth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String adminLoginId) throws UsernameNotFoundException {
        User user = userRepository.findByAdminLoginId(adminLoginId)
                .orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_USER));

        return UserDetailsImpl.build(user);
    }


}
