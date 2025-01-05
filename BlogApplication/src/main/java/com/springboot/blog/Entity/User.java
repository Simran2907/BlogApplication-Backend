package com.springboot.blog.Entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "User")
@Getter
@Setter
@NoArgsConstructor
public class User implements UserDetails{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id",nullable = false,length = 8)
	private int id;
	
	@Column(name ="name",length = 50)
	private String name;
	
	@Column(name = "email",length = 100)
	private String email;
	
	@Column(name ="password",length = 100)
	private String password;
	
	private String about;
	
	//cascade means if we perform any operation on parent it will performed on child-> remove parent-> child also removed automatically
	//fetch -> if we dont want to fetch posts(child) along with category then -> LAZY otherwise EAGER
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<Post> posts = new ArrayList<>();
	
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
	private Set<Comment> comments = new HashSet<>();
	
	@ManyToMany(cascade = CascadeType.ALL,  fetch = FetchType.EAGER)
	@JoinTable(name ="user_role",
	joinColumns  = @JoinColumn(name="user",referencedColumnName = "user_id"),
	inverseJoinColumns =  @JoinColumn(name="role",referencedColumnName = "id")
			)
	private Set<Role> roles = new HashSet<>();

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities =  this.roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).toList();
		return authorities;
	}

	@Override
	public String getUsername() {
		return this.email;
	}
}
