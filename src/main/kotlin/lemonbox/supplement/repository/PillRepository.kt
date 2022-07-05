package lemonbox.supplement.repository

import lemonbox.supplement.entity.Pill
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PillRepository: JpaRepository<Pill, Long> {
}