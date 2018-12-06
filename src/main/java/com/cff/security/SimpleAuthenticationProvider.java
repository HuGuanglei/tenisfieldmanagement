package com.cff.security;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import com.cff.dao.UserDao;
import com.cff.domain.User;

@Component
public class SimpleAuthenticationProvider implements AuthenticationProvider {
	@Autowired
	UserDao userDao;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		AdditionUserDetails additionUserDetails = (AdditionUserDetails) authentication.getDetails();

		String userName = authentication.getPrincipal().toString();
		User user = userDao.findByUserName(userName);
		if (user == null && additionUserDetails.getAdminVal().equals("1")) {
			List<User> users = userDao.findByRole("1");
			if(users == null || users.size() < 1){
				user = new User();
				user.setUserName(userName);
				user.setPasswd(authentication.getCredentials().toString());
				user.setRole("1");
				userDao.save(user);
			}
		}
		if (user == null)
			throw new BadCredentialsException("用户名或密码错误。");
		if (user.getPasswd() != null && user.getPasswd().equals(authentication.getCredentials())) {
			Collection<? extends GrantedAuthority> authorities = null;
			if (user.getRole() != null && user.getRole().equals("1")) {
				authorities = AuthorityUtils.createAuthorityList("ROLE_ADMIN");
			} else {
				authorities = AuthorityUtils.createAuthorityList("ROLE_USER");
			}

			return new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPasswd(), authorities);
		} else {
			throw new BadCredentialsException("用户名或密码错误。");
		}
	}

	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}

}
