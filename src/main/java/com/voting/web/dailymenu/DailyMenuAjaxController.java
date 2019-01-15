package com.voting.web.dailymenu;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping(DailyMenuAjaxController.REST_URL)
public class DailyMenuAjaxController extends AbstractDailyMenuController {
    static final String REST_URL = "/ajax/admin/dailymenu";

    @PostMapping
    public void generateDailyMenu(@RequestParam(value = "date") @DateTimeFormat(pattern="yyyy-MM-dd") Date date) {
        super.generateDailyMenu(date);
    }
}
