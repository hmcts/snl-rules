package simulations.snl

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class listingToSessionAlt extends Simulation {

	val httpProtocol = http
		.baseURL("https://snl-api-snlperf.service.core-compute-snlperf.internal")
		.proxy(Proxy("proxyout.reform.hmcts.net", 8080).httpsPort(8080))
		.acceptHeader("*/*")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("en,en-GB;q=0.9")
		.doNotTrackHeader("1")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36")

	val headers_0 = Map(
		"Access-Control-Request-Headers" -> "authorization,x-requested-with",
		"Access-Control-Request-Method" -> "GET",
		"Origin" -> "https://snl-frontend-snlperf.service.core-compute-snlperf.internal")

	val headers_1 = Map(
		"Accept" -> "application/json, text/plain, */*",
		"Authorization" -> "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJvZmZpY2VyMSIsImlhdCI6MTUzMjY4MjMwMCwiZXhwIjoxNTMyNjg0MTAwfQ.LHp2HsJNjbqJOSoAxkJQkPQ8Ae0BmBfuWpiomeiLGilz_zRaYqJrtYOGwihUYl-2IyAkXkmEFn_UqGqYQBYEtQ",
		"Origin" -> "https://snl-frontend-snlperf.service.core-compute-snlperf.internal",
		"X-Requested-With" -> "XMLHttpRequest")

	val headers_2 = Map(
		"Access-Control-Request-Headers" -> "authorization,content-type,x-requested-with",
		"Access-Control-Request-Method" -> "PUT",
		"Origin" -> "https://snl-frontend-snlperf.service.core-compute-snlperf.internal")

	val headers_3 = Map(
		"Accept" -> "application/json, text/plain, */*",
		"Authorization" -> "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJvZmZpY2VyMSIsImlhdCI6MTUzMjY4MjMwMCwiZXhwIjoxNTMyNjg0MTAwfQ.LHp2HsJNjbqJOSoAxkJQkPQ8Ae0BmBfuWpiomeiLGilz_zRaYqJrtYOGwihUYl-2IyAkXkmEFn_UqGqYQBYEtQ",
		"Content-Type" -> "application/json",
		"Origin" -> "https://snl-frontend-snlperf.service.core-compute-snlperf.internal",
		"X-Requested-With" -> "XMLHttpRequest")

	val headers_6 = Map(
		"Access-Control-Request-Headers" -> "authorization,content-type,x-requested-with",
		"Access-Control-Request-Method" -> "POST",
		"Origin" -> "https://snl-frontend-snlperf.service.core-compute-snlperf.internal")



	val scn = scenario("listingToSessionAlt")

		.exec(http("request_0")
			.options("/sessions?startDate=01-08-2018&endDate=08-08-2023")
			.headers(headers_0))

		.pause(737 milliseconds)

		.exec(http("request_1")
			.get("/sessions?startDate=01-08-2018&endDate=08-08-2023")
			.headers(headers_1))

		.pause(32)

		.exec(http("request_2")
			.options("/hearing-part/d8ff84a4-aec1-4f04-a2de-ecdf7f1f9c92")
			.headers(headers_2))

		.pause(208 milliseconds)

		.exec(http("request_3")
			.put("/hearing-part/d8ff84a4-aec1-4f04-a2de-ecdf7f1f9c92")
			.headers(headers_3)
			.body(RawFileBody("listingToSessionAlt_0003_request.txt")))

		.pause(638 milliseconds)

		.exec(http("request_4")
			.options("/problems/by-user-transaction-id?id=13826fdf-6f00-4fa6-b8d5-c006fa8ebb31")
			.headers(headers_0))

		.exec(http("request_5")
			.get("/problems/by-user-transaction-id?id=13826fdf-6f00-4fa6-b8d5-c006fa8ebb31")
			.headers(headers_1))

		.pause(4)

		.exec(http("request_6")
			.options("/user-transaction/13826fdf-6f00-4fa6-b8d5-c006fa8ebb31/commit")
			.headers(headers_6))

		.pause(210 milliseconds)

		.exec(http("request_7")
			.post("/user-transaction/13826fdf-6f00-4fa6-b8d5-c006fa8ebb31/commit")
			.headers(headers_3)
			.body(RawFileBody("listingToSessionAlt_0007_request.txt")))

		.pause(468 milliseconds)

		.exec(http("request_8")
			.options("/sessions/2b96f85f-f30b-45d3-9b7f-9cb454bc4479")
			.headers(headers_0))

		.exec(http("request_9")
			.get("/sessions/2b96f85f-f30b-45d3-9b7f-9cb454bc4479")
			.headers(headers_1))

		.exec(http("request_10")
			.options("/hearing-part")
			.headers(headers_0))

		.pause(818 milliseconds)

		.exec(http("request_11")
			.get("/hearing-part")
			.headers(headers_1))

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}