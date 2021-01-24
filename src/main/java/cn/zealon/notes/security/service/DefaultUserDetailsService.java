package cn.zealon.notes.security.service;

import cn.zealon.notes.domain.UserInfo;
import cn.zealon.notes.security.domain.LoginUserBean;
import cn.zealon.notes.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * 用户登录处理
 * @author: zealon
 * @since: 2020/11/16
 */
@Component
@Slf4j
public class DefaultUserDetailsService implements UserDetailsService {

    @Resource
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isBlank(username)) {
            log.info("用户[{}]登录失败，用户名不存在", username);
            throw new UsernameNotFoundException("登录用户：" + username + " 不存在");
        }

        // 查出用户信息
        UserInfo userVO = userService.getUserByUserId(username);
        if (userVO == null) {
            log.info("用户[{}]登录失败，用户名不存在", username);
            throw new UsernameNotFoundException("登录用户：" + username + " 不存在");
        }
        return new LoginUserBean(userVO, "", LocalDateTime.now());
    }
}
