package lemonbox.supplement.repository.post

import com.querydsl.core.types.dsl.BooleanExpression
import lemonbox.supplement.entity.Post
import lemonbox.supplement.entity.QPost
import lemonbox.supplement.repository.QuerydslCustomRepositorySupport
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.support.PageableExecutionUtils
import org.springframework.stereotype.Repository

@Repository
class PostDslRepositoryImpl: QuerydslCustomRepositorySupport(Post::class.java), PostDslRepository {
    private val post: QPost = QPost("post")

    override fun findAllPostBySearch(pageable: Pageable, queryParams: MutableMap<String, String>): Page<Post> {
        val posts = selectFrom(post)
            .where(keywordMatch(queryParams["keyword"]))
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .orderBy(post.id.desc())
            .fetch()

        val countQuery = selectFrom(post)
            .where(keywordMatch(queryParams["keyword"]))

        return PageableExecutionUtils.getPage(posts, pageable, countQuery::fetchCount)
    }

    private fun keywordMatch(keyword: String?): BooleanExpression? {
        return if (!keyword.isNullOrEmpty()) {
            post.title.like("%$keyword%").or(post.content.like("%$keyword%"))
        }
        else null
    }
}