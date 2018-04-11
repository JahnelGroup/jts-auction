package com.jahnelgroup.auctionapp;

import com.jahnelgroup.auctionapp.domain.auction.bid.Bid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class PageController {

    @PostMapping("/test")
    public ModelAndView mvc(@Valid @RequestBody Bid bid){
        ModelAndView mvc = new ModelAndView();
        mvc.setViewName("index");
        return mvc;
    }

}
