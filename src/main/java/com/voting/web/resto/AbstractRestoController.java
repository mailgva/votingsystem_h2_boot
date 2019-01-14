package com.voting.web.resto;

import com.voting.model.Resto;
import com.voting.service.RestoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.voting.util.ValidationUtil.assureIdConsistent;
import static com.voting.util.ValidationUtil.checkNew;

public abstract class AbstractRestoController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestoService service;

    public List<Resto> getAll() {
        log.info("getAll");
        return service.getAll();
    }

    public Resto get(int id) {
        log.info("get {}", id);
        return service.get(id);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id);
    }

    public Resto create(Resto resto) {
        log.info("create {}", resto);
        checkNew(resto);
        return service.create(resto);
    }

    public void update(Resto resto, int id) {
        log.info("update {} with id={}", resto, id);
        assureIdConsistent(resto, id);
        service.update(resto);
    }


}
