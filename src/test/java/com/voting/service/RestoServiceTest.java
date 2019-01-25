package com.voting.service;

import com.voting.TestUtil;
import com.voting.model.Resto;
import com.voting.testdata.RestoTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RestoServiceTest  extends AbstractServiceTest {

    @Autowired
    private RestoService service;

    @Autowired
    private CacheManager cacheManager;

    @BeforeEach
    void setUp() throws Exception {
        cacheManager.getCache("restaurants").clear();
    }


    @Test
    public void create() {
        service.create(new Resto(null, "Ит квартал"));
        assertEquals(service.getAll().size(),6);
    }

    @Test
    public void delete() {
        service.delete(100004);
        assertEquals(service.getAll().size(), RestoTestData.restos.size()-1);
    }

    @Test
    public void get() {
        assertEquals(service.get(100003), TestUtil.getById(RestoTestData.restos, 100003));
    }

    @Test
    public void getByName() {
        String name = "Ресторан 3";
        assertEquals(service.getByName(name), TestUtil.getByName(RestoTestData.restos, name));
    }

    @Test
    public void update() {
        Resto resto = service.get(100002);
        resto.setAddress("Дерибон");
        service.update(resto);
        assertEquals(service.get(100002), resto);
    }

    @Test
    public void getAll() {
        assertEquals(service.getAll().size(), RestoTestData.restos.size());
    }
}