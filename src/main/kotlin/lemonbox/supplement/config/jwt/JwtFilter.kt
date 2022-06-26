package lemonbox.supplement.config.jwt

import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import lemonbox.supplement.utils.exception.ErrorCode
import lemonbox.supplement.utils.exception.ErrorResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import java.security.SignatureException
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtFilter(
    private val jwtTokenProvider: JwtTokenProvider
): OncePerRequestFilter() {
    private val AUTHORIZATION_HEADER = "Authorization"
    private val BEARER_PREFIX = "Bearer "
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val accessToken = resolveToken(request)

        if (StringUtils.hasText(accessToken)) {
            val authentication = accessToken?.let {
                try {
                    jwtTokenProvider.validateAccessToken(it)
                } catch (e: SignatureException) {
                    sendErrorResponse(response, ErrorCode.TOKEN_INVALID_SIGNATURE)
                    return
                } catch (e: MalformedJwtException) {
                    sendErrorResponse(response, ErrorCode.TOKEN_INVALID)
                    return
                } catch (e: ExpiredJwtException) {
                    sendErrorResponse(response, ErrorCode.TOKEN_EXPIRED)
                    return
                } catch (e: UnsupportedJwtException) {
                    sendErrorResponse(response, ErrorCode.TOKEN_UNSUPPORTED)
                    return
                } catch (e: java.lang.IllegalArgumentException) {
                    sendErrorResponse(response, ErrorCode.TOKEN_EMPTY)
                    return
                }

                jwtTokenProvider.getAuthentication(it)
            }
            SecurityContextHolder.getContext().authentication = authentication
        }

        filterChain.doFilter(request, response)
    }

    private fun resolveToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader(AUTHORIZATION_HEADER)
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX))
            return bearerToken.substring(7)
        return null
    }

    private fun sendErrorResponse(response: HttpServletResponse, errorCode: ErrorCode) {
        val objectMapper = ObjectMapper()
        response.contentType = "application/json"

        val jsonString = objectMapper.writeValueAsString(ErrorResponse.toResponseEntity(errorCode))
        response.writer.print(jsonString)
        response.writer.flush()
    }


}