package xyz.vshmaliukh.tucantournament.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HomeControllerTest {

    @Autowired
    HomeController controller;

    @Test
    void doGetHomePageTest() {
        ModelMap modelMap = new ModelMap();
        ModelAndView modelAndView = controller.doGetHomePage(modelMap);

        assertEquals("uploadForm", modelAndView.getViewName());
        assertEquals(modelMap, modelAndView.getModelMap());
    }

    @Test
    void homeControllerContextLoadTest() {
        assertNotNull(controller);
    }

}