package com.hust.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hust.dto.UserInfo;
import com.hust.repository.UserInfoJpaRepository;
@Service
public class UserInfoDetailsService implements UserDetailsService {
	@Autowired
	private UserInfoJpaRepository userInfoJpaRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		UserInfo user=this.userInfoJpaRepository.findByUsername(username);
		if (user==null) {
			throw new UsernameNotFoundException(
					"Opps! user not found with user-name: " + username);
		}
		return new User(user.getUsername(), user.getPassword(), getAuthorities(user));
	}
	private Collection<GrantedAuthority> getAuthorities(UserInfo user) {
		List<GrantedAuthority> authorities=new ArrayList<>();
		authorities=AuthorityUtils.createAuthorityList(user.getRole());
		return authorities;
	}
	

}
