package com.baizhi.controller;

import com.baizhi.entity.User;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/hello")
public class AuthorizeMethodController {

    // or hasAuthority('READ_INFO')
    @PreAuthorize("hasRole('ADMIN') and authentication.name == 'root'")
    @GetMapping()
    public String hello() {
        return "hello";
    }

    @PreAuthorize("authentication.name == #name")
    @RequestMapping("name")
    public String helloName(String name) {
        return "hello " + name;
    }

    @PreFilter(value = "filterObject.id%2 != 0", filterTarget = "users") //filterTarget必须是数组集合类型
    @PostMapping("users")
    public void addUser(@RequestBody List<User> users) {
        System.out.println("users = " + users);
    }

    @PostAuthorize("returnObject.id == 1")
    @GetMapping("userId")
    public User getUserById(Integer id) {
        return new User(id, "name:" + id);
    }

    @PostFilter(value = "filterObject.id%2 == 0")
    @GetMapping("/lists")
    public List<User> getAll() {
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            userList.add(new User(i, "name:" + i));
        }
        return userList;
    }

    @Secured({"ROLE_USER"}) // 只能判断角色
    @GetMapping("/secured")
    public User getUserByUserName() {
        return new User(99, "secured");
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/username")
    public User getUserByUserName2(String username) {
        return new User(99, username);
    }

    @PermitAll
    @GetMapping("permitAll")
    public String permitAll() {
        return "PremitAll";
    }

    @DenyAll
    @GetMapping("denyAll")
    public String denyAll() {
        return "DenyAll";
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"}) // 具有其中一个角色即可
    @GetMapping("/rolesAllowed")
    public String rolesAllowed() {
        return "RolesAllowed";
    }
}
