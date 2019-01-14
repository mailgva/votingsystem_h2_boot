package com.voting.testdata;

import com.voting.model.Resto;

import java.util.List;

import static com.voting.model.AbstractBaseEntity.START_SEQ;

public class RestoTestData {
    public static int restoId = START_SEQ + 2; // 2 - users

    public static final List<Resto> restos = List.of(
            new Resto(restoId++, "Ресторан 1"),
            new Resto(restoId++, "Ресторан 2"),
            new Resto(restoId++, "Ресторан 3"),
            new Resto(restoId++, "Ресторан 4"),
            new Resto(restoId++, "Ресторан 5")
    );

    private RestoTestData(){}

}
