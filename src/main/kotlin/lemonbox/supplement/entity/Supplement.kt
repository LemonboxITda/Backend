package lemonbox.supplement.entity

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

    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "supplement")
    var pillList: MutableList<Pill> = mutableListOf()

): BaseEntity()