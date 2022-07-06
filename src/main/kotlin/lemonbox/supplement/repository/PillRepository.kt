package lemonbox.supplement.repository

import lemonbox.supplement.entity.Pill
import lemonbox.supplement.entity.Supplement
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface PillRepository: JpaRepository<Pill, Long> {
    fun findBySupplementAndDate(supplement: Supplement, date: LocalDateTime): Pill?

    fun existsBySupplementAndDate(supplement: Supplement, date: LocalDateTime): Boolean
}