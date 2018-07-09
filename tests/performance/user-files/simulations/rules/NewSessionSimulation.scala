
package rules

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class NewSessionSimulation extends Simulation {
  val httpConf = http
    .baseURL("http://localhost:8091") // Here is the root for all relative URLs
    .acceptHeader("application/json,text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8") // Here are the common headers
    .doNotTrackHeader("1")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")

  val headers_json = Map("Content-Type" -> "application/json") // Note the headers specific to a given request

  val scn = scenario("Insert Session") // A scenario is a chain of requests and pauses
    .pause(10) // Note that Gatling has recorded real time pauses
    .exec(session => session.set("sessionId", java.util.UUID.randomUUID()))
    .exec(http("insert session") // Here's an example of a POST request
    .post("/msg?rulesDefinition=Listings")
    .headers(headers_json)
    .body(ElFileBody("data/insert-session.json")).asJSON
    .check(status.is(session => 200)))

  setUp(
    scn.inject(atOnceUsers(1))
    .protocols(httpConf)
  )



}
