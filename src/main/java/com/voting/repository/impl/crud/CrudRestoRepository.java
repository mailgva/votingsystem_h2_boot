package com.voting.repository.impl.crud;

import com.voting.model.Resto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface CrudRestoRepository extends JpaRepository<Resto, Integer> {
    Resto save(Resto resto);

    @Transactional
    @Modifying
    @Query("DELETE FROM Resto r WHERE r.id=:id")
    int delete(@Param("id") int id);

    Resto findById(int id);

    // null if not found
    @Query("SELECT r FROM Resto r WHERE r.name=:name")
    Resto getByName(@Param("name") String name);

    List<Resto> getAll();
}
