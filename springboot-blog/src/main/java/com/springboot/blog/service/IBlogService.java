package com.springboot.blog.service;

import com.springboot.blog.entity.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface IBlogService {

    List<Blog> findAll();

    Page<Blog> findAll(PageRequest pageRequest);

    Blog save(Blog blog);

    void deleteById(Integer id);

    Blog update(Blog blog);
}
