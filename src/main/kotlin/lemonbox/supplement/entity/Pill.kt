package lemonbox.supplement.entity

import lemonbox.supplement.data.CheckType
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Pill (

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplement_id")
    var supplement: Supplement,

    @Column
    var status: CheckType,

    @Column
    var date: LocalDateTime,
): BaseEntity()