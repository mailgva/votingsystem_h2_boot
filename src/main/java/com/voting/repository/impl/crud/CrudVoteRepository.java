package com.voting.repository.impl.crud;

import com.voting.model.Vote;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


@Transactional(readOnly = true)
public interface CrudVoteRepository extends JpaRepository<Vote, Integer> {
    @Override
    @Transactional
    Vote save(Vote vote);

    @Modifying
    @Transactional
    @Query("DELETE FROM Vote v WHERE v.id=:id")
    int delete(@Param("id") int id);


    // null if meal do not belong to userId
    @EntityGraph(attributePaths = {"resto" , "user"})
    @Query("SELECT v FROM Vote v WHERE v.id=:id")
    Vote get(@Param("id") int id);


    // ORDERED dateTime desc
    @EntityGraph(attributePaths = {"resto" , "user"})
    List<Vote> getAll();

    // ORDERED dateTime desc
    //List<Vote> getBetween(Date startDate, Date endDate, int userId);

    /*@Query("SELECT v FROM Vote v JOIN FETCH v.resto " +
            "JOIN FETCH v.user WHERE v.date=:date and v.user.id=:userId")*/
    @EntityGraph(attributePaths = {"resto" , "user"})
    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId AND v.date=:date")
    Vote getByDate(@Param("date") LocalDate date, @Param("userId") int userId);

    List<Vote> getAllByDate(@Param("date") LocalDate date);
}
