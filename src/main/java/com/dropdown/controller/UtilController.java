package com.dropdown.controller;

import com.dropdown.APICalls.Coordinates;
import com.dropdown.APICalls.H3UberGridService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/util")
@RequiredArgsConstructor
public class UtilController {

    private final H3UberGridService h3UberGridService;

    @GetMapping
    public String getCellAddress(@RequestParam Double lat, @RequestParam Double lon, @RequestHeader("Authorization")String token) {
        return h3UberGridService.getGridAddress(new Coordinates(lat, lon));
    }

}
