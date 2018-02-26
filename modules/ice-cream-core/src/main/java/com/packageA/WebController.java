package com.packageA;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * Maps the page requests to relevant pages
 *
 */
@Controller
public class WebController {

    @GetMapping("/")
    public String showMainPage() {
        Model model;
        return "mainPage";
    }


}
