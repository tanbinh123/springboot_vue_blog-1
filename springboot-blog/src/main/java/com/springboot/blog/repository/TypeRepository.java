package com.springboot.blog.repository;

import com.springboot.blog.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TypeRepository extends JpaRepository<Type, Integer> {

   public Type findByTypename(String name);
}
