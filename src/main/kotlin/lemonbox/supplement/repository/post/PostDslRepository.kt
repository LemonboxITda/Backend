package lemonbox.supplement.repository.post

import lemonbox.supplement.entity.Post
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface PostDslRepository {
    fun findAllPostBySearch(pageable: Pageable, queryParams: MutableMap<String, String>): Page<Post>
}