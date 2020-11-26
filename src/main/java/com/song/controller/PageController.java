package com.song.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Mr.Song
 */
@Controller
public class PageController {
    @RequestMapping("")
    public String getPageIndex(){
        return "index";
    }
}
