package com.voting.web.dish;

import com.voting.model.Dish;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/ajax/admin/dishes")
public class DishAjaxController extends AbstractDishController {
    private final String UPLOADED_DIR_PATH = System.getenv("VOTING_ROOT") + "/images/dishes/";
    private final String DB_DIR_PATH = "pictures/dishes/";

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Dish> getAll() {
        return super.getAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Dish get(@PathVariable("id") Dish dish) {
        return dish;
    }


    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @PostMapping(headers = "Content-Type= multipart/form-data")
    public void createOrUpdate(@Valid Dish dish, @RequestParam(required = false, name = "img_file") MultipartFile file) throws IOException {
        String imgFilePath = null;

        if ((file != null) && (!file.isEmpty())) {
            String fileDetail[] = file.getOriginalFilename().split("\\.");
            String extention = "." + fileDetail[fileDetail.length - 1];
            String newFileName = UUID.randomUUID().toString() + extention;
            imgFilePath = DB_DIR_PATH + newFileName;
            Path path = Paths.get(UPLOADED_DIR_PATH + newFileName);
            byte[] bytes = file.getBytes();
            Files.write(path, bytes);
        }

        dish.setImgFilePath(imgFilePath);

        if (dish.isNew()) {
            super.create(dish);
        } else {
            super.update(dish, dish.getId());
        }
    }

}
