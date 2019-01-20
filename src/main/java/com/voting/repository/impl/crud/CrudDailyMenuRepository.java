package com.voting.repository.impl.crud;

import com.voting.model.DailyMenu;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudDailyMenuRepository extends JpaRepository<DailyMenu, Integer> {
    @Transactional
    DailyMenu save(DailyMenu dailyMenu);

    @Transactional
    @Modifying
    @Query("DELETE FROM DailyMenu dm WHERE dm.id=:id")
    int delete(@Param("id") int id);


    @Query("SELECT distinct dm FROM DailyMenu dm JOIN FETCH dm.resto LEFT JOIN FETCH dm.dmDishes WHERE dm.date=:date ORDER BY dm.resto.name ASC")
    /*@EntityGraph(attributePaths = {"dmDishes"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT dm FROM DailyMenu dm WHERE dm.date=:date ORDER BY dm.resto.name ASC")*/
    List<DailyMenu> getByDate(@Param("date") LocalDate date);

    DailyMenu findById(int id);

    @Override
    List<DailyMenu> findAll();

    @Transactional
    @Procedure(name = DailyMenu.GENERATE_DAILY_MENU)
    void generateDailyMenu(@Param("fromdate") LocalDate fromdate, @Param("todate") LocalDate todate);

    /*@Transactional
    @Procedure(name = DailyMenu.GENERATE_STR_DAILY_MENU)
    void generateDailyMenu(@Param("fromdate") String fromdate, @Param("todate") String todate);*/


    @Transactional
    @Modifying
    @Query("DELETE FROM DailyMenu dm WHERE dm.date=:date")
    void deleteByDate(@Param("date") LocalDate date);
}
