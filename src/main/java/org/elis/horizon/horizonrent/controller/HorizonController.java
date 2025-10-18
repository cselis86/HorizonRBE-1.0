package org.elis.horizon.horizonrent.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HorizonController {

    @GetMapping("/qa/rent")
    public String rentQa(){
        return "Horizon Rent QA";
    }

    @GetMapping("/dev/rent")
    public String rentDev(){
        return "Horizon Rent Dev";
    }

    @GetMapping("/guest/rent")
    public String rentGuest(){
        return "Horizon Rent Guest";
    }

}
