package com.springboot.blog.controller;

import com.springboot.blog.entity.Blog;
import com.springboot.blog.entity.Type;
import com.springboot.blog.repository.BlogRepository;
import com.springboot.blog.service.impl.BlogServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/blog")
public class BlogHandler {

    @Autowired
    private BlogServiceImp blogServiceImp;

    @GetMapping("/findAll")
    public List<Blog> findAll() {
        return blogServiceImp.findAll();
    }

    @GetMapping("/findAll/{page}/{size}")
    public Page<Blog> findAll(@PathVariable("page") Integer page,
                              @PathVariable("size") Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return blogServiceImp.findAll(pageRequest);
    }

    @PostMapping("/save")
    public String save(@RequestBody  Blog blog){
        System.out.println(blog);
        List<Type> types = blog.getTypes();
        for(Type type: types){
            System.out.println(type);
        }
        Blog res = blogServiceImp.save(blog);
        if(res != null){
            return "success";
        } else {
            return "error";
        }
    }

    @DeleteMapping("/deleteById/{id}")
    public void deleteById(@PathVariable("id")Integer id) {
        blogServiceImp.deleteById(id);
    }

    @PutMapping("/update")
    public String update(@RequestBody Blog blog){
        Blog res = blogServiceImp.save(blog);
        if(res != null) {
            return "success";
        } else {
            return "error";
        }
    }

}
