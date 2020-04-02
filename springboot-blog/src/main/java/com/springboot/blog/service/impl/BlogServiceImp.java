package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Blog;
import com.springboot.blog.entity.Type;
import com.springboot.blog.repository.BlogRepository;
import com.springboot.blog.repository.TypeRepository;
import com.springboot.blog.service.IBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlogServiceImp implements IBlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private TypeRepository typeRepository;

    public List<Blog> findAll() {
        return blogRepository.findAll();
    }

    public Page<Blog> findAll(PageRequest pageRequest) {
        return blogRepository.findAll(pageRequest);
    }


    @Override
    public Blog save(Blog blog) {
        List<Type>  types = blog.getTypes();
        List<Type>  tmpTypes = new ArrayList<>();
        for(Type type : types) {
            Type tmpType = null;
            tmpType = typeRepository.findByTypename(type.getTypename());
            if(tmpType != null) {
                tmpTypes.add(tmpType);
            } else {
                tmpTypes.add(type);
            }
        }
        blog.setTypes(tmpTypes);

        return blogRepository.save(blog);
    }

    @Override
    public void deleteById(Integer id) {
        blogRepository.deleteById(id);
    }

    @Override
    public Blog update(Blog blog) {
        return blogRepository.save(blog);
    }

}
