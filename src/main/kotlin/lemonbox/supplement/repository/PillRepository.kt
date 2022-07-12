package lemonbox.supplement.repository

import lemonbox.supplement.data.type.PillStatus
import lemonbox.supplement.entity.Pill
import lemonbox.supplement.entity.Supplement
import lemonbox.supplement.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface PillRepository: JpaRepository<Pill, Long> {
    @Query("SELECT * FROM pill p\n" +
           "LEFT JOIN supplement s ON p.supplement_id = s.id\n" +
           "LEFT JOIN user u ON s.user_id = u.id\n" +
           "WHERE u.id = :#{#user.id} AND p.date = :date", nativeQuery = true)
    fun selectByUserAndDate(@Param(value = "user") user: User, @Param(value = "date") date: LocalDate): List<Pill>
    fun findBySupplementAndDate(supplement: Supplement, date: LocalDate): Pill?

    @Query("SELECT * FROM pill p\n" +
           "LEFT JOIN supplement s ON p.supplement_id = s.id\n" +
           "LEFT JOIN user u ON s.user_id = u.id\n" +
           "WHERE u.id = :#{#user.id} AND p.date = :date AND p.status = :#{#status.status}", nativeQuery = true)
    fun findByUserAndDateAndStatus(@Param(value = "user") user: User, @Param(value = "date")date: LocalDate, @Param(value = "status") status: PillStatus): List<Pill>
}