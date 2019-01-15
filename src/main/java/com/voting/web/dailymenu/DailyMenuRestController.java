package com.voting.web.dailymenu;

import com.voting.View;
import com.voting.model.DailyMenu;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(DailyMenuRestController.REST_URL)
public class DailyMenuRestController extends AbstractDailyMenuController {
    static final String REST_URL = "/rest/admin/dailymenu";

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public DailyMenu get(@PathVariable("id") int id) {
        return super.get(id);
    }



    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DailyMenu> getAll() {
        return super.getAll();
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DailyMenu> createWithLocation(@Validated(View.Web.class) @RequestBody DailyMenu dailyMenu) {
        DailyMenu created = super.create(dailyMenu);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Override
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Validated(View.Web.class) @RequestBody DailyMenu dailyMenu, @PathVariable("id") int id) {
        super.update(dailyMenu, id);
    }

    @Override
    @GetMapping(value = "/bydate/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<DailyMenu> getByDate(@RequestParam(value = "date") @DateTimeFormat(pattern="yyyy-MM-dd") Date date) {
        return super.getByDate(date);
    }

    @Override
    @PostMapping(value = "/generate/")
    public void generateDailyMenu(@RequestParam(value = "date") @DateTimeFormat(pattern="yyyy-MM-dd") Date date) {
        super.generateDailyMenu(date);
    }
}
