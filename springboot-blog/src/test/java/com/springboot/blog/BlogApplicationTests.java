package com.springboot.blog;

import com.springboot.blog.entity.Blog;
import com.springboot.blog.entity.Type;
import com.springboot.blog.repository.BlogRepository;
import com.springboot.blog.repository.TypeRepository;
import com.springboot.blog.service.impl.BlogServiceImp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.EntityManagerFactoryAccessor;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@SpringBootTest
class BlogApplicationTests {

    @Autowired
    private BlogServiceImp blogServiceImp;

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private TypeRepository typeRepository;

    @Test
    @Rollback(false)
    @Transactional
    public void save(){
        Blog blog = new Blog();
        blog.setName("java实战");
        blog.setAuthor("xiaoming");
        blog.setContent("java");

        Type type = new Type();

        type = typeRepository.findByTypename("node.js");
        System.out.println(type);
        //type.setTypename("node.js");
//        type = typeRepository.findById(1).orElse(null);
//        type.setId(1);
        blog.getTypes().add(type);

//        type.getBlogs().add(blog);

        blogServiceImp.save(blog);
//        typeRepository.save(type);
    }


    @Test
    public void delete() {
        blogRepository.deleteById(11);
    }
}
