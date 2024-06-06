package com.curso.springsecurity.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("denyAll()")
public class HelloWordController {

    @GetMapping("/helloNoSecured")
    @PreAuthorize("permitAll()")
    public String helloNoSecured() {
        return "Hello World No Secured";
    }

    @GetMapping("/helloSecured")
    @PreAuthorize("hasAuthority('READ')")
    public String helloSecured() {
        return "Hello World Secured";
    }
}
