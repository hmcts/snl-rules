package uk.gov.hmcts.reform.sandl.snlrules.performance

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class NewSessionSimulation extends Simulation {
  val httpConf = http
    .baseURL(Environments.baseUrl)
    .acceptHeader("application/json,text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .doNotTrackHeader("1")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")

  val headers_json = Map("Content-Type" -> "application/json")

  val scn = scenario("Insert Session")
    .exec(session => session.set("sessionId", java.util.UUID.randomUUID()))
    .exec(http("insert session")
    .post("/msg?rulesDefinition=Listings")
    .headers(headers_json)
    .body(ElFileBody("data/insert-session.json")).asJSON
    .check(status.is(session => 200)))

  setUp(
    scn.inject(
      rampUsers(10) over(2 seconds),
    )
  )
    .maxDuration(15 seconds)
    .protocols(httpConf)
    .assertions(
      global.failedRequests.count.is(0)
    )

}
