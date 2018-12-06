package com.cff.web;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebPageController {
	@RequestMapping("/")
	public String index(Model model) {
		Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		for(GrantedAuthority item : authorities){
			if(item.getAuthority().equals("ROLE_ADMIN"))return "admin.html";
		}
		
		return "index.html";
	}
	
	@RequestMapping("/control")
	public String control(Model model) {
		Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		for(GrantedAuthority item : authorities){
			if(item.getAuthority().equals("ROLE_ADMIN"))return "admin.html";
		}
		
		return "index.html";
	}
}
