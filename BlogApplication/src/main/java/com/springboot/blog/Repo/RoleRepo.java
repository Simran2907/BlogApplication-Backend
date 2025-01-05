package com.springboot.blog.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.blog.Entity.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {

}
