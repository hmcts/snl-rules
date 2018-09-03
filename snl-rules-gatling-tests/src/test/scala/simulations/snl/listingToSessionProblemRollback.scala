package simulations.snl

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class listingToSessionProblemRollback extends Simulation {

	val httpProtocol = http
		.baseURL("https://snl-api-snlperf.service.core-compute-snlperf.internal")
		.proxy(Proxy("proxyout.reform.hmcts.net", 8080).httpsPort(8080))
		.acceptHeader("*/*")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("en,en-GB;q=0.9")
		.doNotTrackHeader("1")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36")

	val headers_0 = Map(
		"Access-Control-Request-Headers" -> "authorization,content-type,x-requested-with",
		"Access-Control-Request-Method" -> "PUT",
		"Origin" -> "https://snl-frontend-snlperf.service.core-compute-snlperf.internal")

	val headers_1 = Map(
		"Accept" -> "application/json, text/plain, */*",
		"Authorization" -> "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJvZmZpY2VyMSIsImlhdCI6MTUzMjY4MjMwMCwiZXhwIjoxNTMyNjg0MTAwfQ.LHp2HsJNjbqJOSoAxkJQkPQ8Ae0BmBfuWpiomeiLGilz_zRaYqJrtYOGwihUYl-2IyAkXkmEFn_UqGqYQBYEtQ",
		"Content-Type" -> "application/json",
		"Origin" -> "https://snl-frontend-snlperf.service.core-compute-snlperf.internal",
		"X-Requested-With" -> "XMLHttpRequest")

	val headers_2 = Map(
		"Access-Control-Request-Headers" -> "authorization,x-requested-with",
		"Access-Control-Request-Method" -> "GET",
		"Origin" -> "https://snl-frontend-snlperf.service.core-compute-snlperf.internal")

	val headers_3 = Map(
		"Accept" -> "application/json, text/plain, */*",
		"Authorization" -> "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJvZmZpY2VyMSIsImlhdCI6MTUzMjY4MjMwMCwiZXhwIjoxNTMyNjg0MTAwfQ.LHp2HsJNjbqJOSoAxkJQkPQ8Ae0BmBfuWpiomeiLGilz_zRaYqJrtYOGwihUYl-2IyAkXkmEFn_UqGqYQBYEtQ",
		"Origin" -> "https://snl-frontend-snlperf.service.core-compute-snlperf.internal",
		"X-Requested-With" -> "XMLHttpRequest")



	val scn = scenario("listingToSessionProblemRollback")
		.exec(http("request_0")
			.options("/hearing-part/cf3a7605-5d27-45ef-a1a3-6424fe0a7376")
			.headers(headers_0))
		.pause(232 milliseconds)
		.exec(http("request_1")
			.put("/hearing-part/cf3a7605-5d27-45ef-a1a3-6424fe0a7376")
			.headers(headers_1)
			.body(RawFileBody("listingToSessionProblemRollback_0001_request.txt")))
		.pause(823 milliseconds)
		.exec(http("request_2")
			.options("/problems/by-user-transaction-id?id=9daaa642-0563-4e8c-ae4d-dbf18d1bed87")
			.headers(headers_2))
		.exec(http("request_3")
			.get("/problems/by-user-transaction-id?id=9daaa642-0563-4e8c-ae4d-dbf18d1bed87")
			.headers(headers_3))

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}