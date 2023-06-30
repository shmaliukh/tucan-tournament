package xyz.vshmaliukh.tucantournament.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/**")
public class HomeController {

    @GetMapping
    public ModelAndView doGetHomePage(ModelMap modelMap) {
        return new ModelAndView("uploadForm", modelMap);
    }

}
