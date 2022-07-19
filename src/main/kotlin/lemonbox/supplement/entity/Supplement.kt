package lemonbox.supplement.entity

import lemonbox.supplement.data.SupplementRequestDto
import javax.persistence.*

@Entity
class Supplement (

    @Column
    var name: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User,

    @Column
    var count: Int,

    @Column
    var repill: Int,

    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "supplement")
    var pillList: MutableList<Pill> = mutableListOf()

): BaseEntity() {
    constructor(requestDto: SupplementRequestDto, user: User): this(
        name = requestDto.name,
        user = user,
        count = requestDto.count,
        repill = 0,
    )

    fun updateCount(count: Int) {
        this.count = count
        this.repill++
    }
}