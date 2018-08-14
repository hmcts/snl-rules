package uk.gov.hmcts.reform.sandl.snlrules.performance.utils

import io.jsonwebtoken.{ Jwts, SignatureAlgorithm }
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.Date

object JwtTokenHelper {
  def createRulesAuthenticationHeader() = {
    println("lalalala")
    var bearer = "Bearer: " + createToken("FakeTestSecret", 5000, "snl-events")
    Map[String, String]("Authentication" -> bearer)
  }

  def createToken(secret: String, expiryInMs: Long, serviceName: String): String = {
    val now = Instant.now
    val expiryDate = now.plus(expiryInMs, ChronoUnit.MILLIS)
    Jwts.builder.claim("service", serviceName)
      .setIssuedAt(new Date(now.toEpochMilli))
      .setExpiration(new Date(expiryDate.toEpochMilli))
      .signWith(SignatureAlgorithm.HS512, secret)
      .compact
  }
}

class JwtTokenHelper private () {
}
