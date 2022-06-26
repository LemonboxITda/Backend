package lemonbox.supplement.config.jwt

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import lemonbox.supplement.config.property.JwtProperty
import org.apache.tomcat.util.net.openssl.ciphers.Authentication
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import java.util.*
import javax.annotation.PostConstruct

@Component
class JwtTokenProvider(
    private val jwtProperty: JwtProperty,
    private val userDetailsService: UserDetailsService,
) {
    private var secretKey = ""
    private var refreshKey = ""

    private val ONE_DAY: Long = 1000 * 60 * 60 * 24
    private val ONE_WEEK: Long = ONE_DAY * 7

    private val LOG = LoggerFactory.getLogger(JwtTokenProvider::class.java)

    @PostConstruct
    fun init() {
        secretKey = Base64.getEncoder().encodeToString(jwtProperty.secret.toByteArray())
        refreshKey = Base64.getEncoder().encodeToString(jwtProperty.refresh.toByteArray())
    }

    fun getAccessToken(username: String, roles: Array<String>): String {
        return generate(username, ONE_DAY * 180, roles, secretKey)
    }

    fun decodeAccessToken(accessToken: String): String {
        return decode(secretKey, accessToken)
    }

    fun getRefreshToken(username: String, roles: Array<String>): String {
        return generate(username, ONE_DAY * 180, roles, refreshKey)
    }

    fun decodeRefreshToken(refreshToken: String): String {
        return decode(refreshKey, refreshToken)
    }

    private fun generate(username: String, expirationInMillis: Long, roles: Array<String>, signature: String): String {
        val claims = Jwts.claims().setSubject(username)
        claims["roles"] = roles

        val now = Date()
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(Date(now.time + expirationInMillis))
            .signWith(SignatureAlgorithm.HS256, signature)
            .compact()
    }

    private fun decode(signature: String, token: String) {

    }

    fun getAuthentication(accessToken: String): Authentication {
        val userDetails = userDetailsService.loadUserByUsername(getUsername(accessToken))
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    private fun getUsername(accessToken: String): String {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(accessToken).body.subject
    }

}