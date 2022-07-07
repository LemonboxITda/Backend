package lemonbox.supplement.entity

import lemonbox.supplement.data.PillStatus
import java.time.LocalDate
import javax.persistence.*

@Entity
class Pill (
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplement_id")
    var supplement: Supplement,

    @Enumerated(EnumType.STRING)
    @Column
    var status: PillStatus,

    @Column
    var date: LocalDate,
): BaseEntity()