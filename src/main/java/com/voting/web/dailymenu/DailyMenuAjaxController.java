package com.voting.web.dailymenu;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping(DailyMenuAjaxController.REST_URL)
public class DailyMenuAjaxController extends AbstractDailyMenuController {
    static final String REST_URL = "/ajax/admin/dailymenu";

    @PostMapping
    public void generateDailyMenu(@RequestParam(value = "date") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate date) {
        super.generateDailyMenu(date);
    }
}
