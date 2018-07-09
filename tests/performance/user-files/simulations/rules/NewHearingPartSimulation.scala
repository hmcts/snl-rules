package rules

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class NewHearingPartSimulation extends Simulation {
  val httpConf = http
    .baseURL("http://localhost:8091") // Here is the root for all relative URLs
    .acceptHeader("application/json,text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8") // Here are the common headers
    .doNotTrackHeader("1")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")

  val headers_json = Map("Content-Type" -> "application/json") // Note the headers specific to a given request

  val scn = scenario("Insert Hearing part")
    .forever(
      exec(session => session.set("hearingPartId", java.util.UUID.randomUUID()))
      .exec(http("insert hearing")
      .post("/msg?rulesDefinition=Listings")
      .headers(headers_json)
      .body(ElFileBody("data/insert-hearingPartProblem.json")).asJSON
      .check(status.is(session => 200)))
        .pause(1)
  )

  setUp(
      scn.inject(
        rampUsers(500) over(15 seconds),
        nothingFor(60 seconds),

        rampUsers(500) over(15 seconds),
        nothingFor(60 seconds),

        rampUsers(500) over(15 seconds),
        nothingFor(60 seconds),

        rampUsers(500) over(15 seconds),
        nothingFor(60 seconds)
      )
//    .throttle(reachRps(100) in (10 seconds), holdFor(5 minutes))
    //atOnceUsers(1)).protocols(httpConf)

    )
    .maxDuration(30 minutes)
    .protocols(httpConf)


}
