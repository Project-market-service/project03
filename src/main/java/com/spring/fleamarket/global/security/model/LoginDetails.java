package com.spring.fleamarket.global.security.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.spring.fleamarket.domain.model.Account;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.log4j.Log4j;

public class LoginDetails implements UserDetails {

	private int id;
	private String username;
	private String password;
	
	public LoginDetails(Account account) {
		this.id = account.getId();
		this.username = account.getName();
		this.password = account.getPassword();
	}
	
	public LoginDetails(int id, String username) {
		this.id = id;
		this.username = username;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_USER"));
	}
	
	@Override
	public String getUsername() {
		return this.username;
	}
	
	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public int getId() {
		return this.id;
	}

}