package ru.monsterdev.study.otusnet.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/secured")
    public String getSecured() {
        return "secured";
    }

    @GetMapping("/unsecured")
    public String getUnsecured() {
        return "unsecured";
    }
}
