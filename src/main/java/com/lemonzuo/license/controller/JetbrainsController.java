package com.lemonzuo.license.controller;

import com.lemonzuo.license.service.JetbrainsService;
import com.lemonzuo.license.vo.License;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LemonZuo
 * @create 2024-02-20 23:13
 */
@Slf4j
@RestController
@RequestMapping("/jetbrains")
public class JetbrainsController {
    @Resource
    private JetbrainsService jetbrainsService;

    @PostMapping("/generate")
    public License generate(@RequestParam(required = false) String licenseeName) throws Exception {
        return jetbrainsService.generateLicense(licenseeName);
    }

}
