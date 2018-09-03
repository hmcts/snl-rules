package simulations.snl

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class listingToSessionTransactionConflict extends Simulation {

	val httpProtocol = http
		.baseURL("https://snl-api-snlperf.service.core-compute-snlperf.internal")
		.proxy(Proxy("proxyout.reform.hmcts.net", 8080).httpsPort(8080))
		.acceptHeader("application/json, text/plain, */*")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("en,en-GB;q=0.9")
		.doNotTrackHeader("1")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36")

	val headers_0 = Map(
		"Authorization" -> "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJvZmZpY2VyMSIsImlhdCI6MTUzMjY4MjMwMCwiZXhwIjoxNTMyNjg0MTAwfQ.LHp2HsJNjbqJOSoAxkJQkPQ8Ae0BmBfuWpiomeiLGilz_zRaYqJrtYOGwihUYl-2IyAkXkmEFn_UqGqYQBYEtQ",
		"X-Requested-With" -> "XMLHttpRequest")

	val headers_1 = Map(
		"Accept" -> "*/*",
		"Access-Control-Request-Headers" -> "authorization,x-requested-with",
		"Access-Control-Request-Method" -> "GET",
		"Origin" -> "https://snl-frontend-snlperf.service.core-compute-snlperf.internal")

	val headers_5 = Map(
		"Authorization" -> "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJvZmZpY2VyMSIsImlhdCI6MTUzMjY4MjMwMCwiZXhwIjoxNTMyNjg0MTAwfQ.LHp2HsJNjbqJOSoAxkJQkPQ8Ae0BmBfuWpiomeiLGilz_zRaYqJrtYOGwihUYl-2IyAkXkmEFn_UqGqYQBYEtQ",
		"Origin" -> "https://snl-frontend-snlperf.service.core-compute-snlperf.internal",
		"X-Requested-With" -> "XMLHttpRequest")

	val headers_9 = Map(
		"Accept" -> "*/*",
		"Access-Control-Request-Headers" -> "authorization,content-type,x-requested-with",
		"Access-Control-Request-Method" -> "PUT",
		"Origin" -> "https://snl-frontend-snlperf.service.core-compute-snlperf.internal")

	val headers_10 = Map(
		"Authorization" -> "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJvZmZpY2VyMSIsImlhdCI6MTUzMjY4MjMwMCwiZXhwIjoxNTMyNjg0MTAwfQ.LHp2HsJNjbqJOSoAxkJQkPQ8Ae0BmBfuWpiomeiLGilz_zRaYqJrtYOGwihUYl-2IyAkXkmEFn_UqGqYQBYEtQ",
		"Content-Type" -> "application/json",
		"Origin" -> "https://snl-frontend-snlperf.service.core-compute-snlperf.internal",
		"X-Requested-With" -> "XMLHttpRequest")

    val uri2 = "https://snl-frontend-snlperf.service.core-compute-snlperf.internal/cfg"

	val scn = scenario("listingToSessionTransactionConflict")
		.exec(http("request_0")
			.get(uri2 + "")
			.headers(headers_0))
		.pause(169 milliseconds)
		.exec(http("request_1")
			.options("/sessions?startDate=27-07-2018&endDate=27-07-2023")
			.headers(headers_1))
		.exec(http("request_2")
			.options("/hearing-part")
			.headers(headers_1))
		.exec(http("request_3")
			.options("/room")
			.headers(headers_1))
		.exec(http("request_4")
			.options("/person?personType=judge")
			.headers(headers_1))
		.pause(424 milliseconds)
		.exec(http("request_5")
			.get("/sessions?startDate=27-07-2018&endDate=27-07-2023")
			.headers(headers_5))
		.exec(http("request_6")
			.get("/hearing-part")
			.headers(headers_5))
		.pause(204 milliseconds)
		.exec(http("request_7")
			.get("/room")
			.headers(headers_5))
		.exec(http("request_8")
			.get("/person?personType=judge")
			.headers(headers_5))
		.pause(21)
		.exec(http("request_9")
			.options("/hearing-part/b262fc78-dc98-484b-849b-7b96d7753918")
			.headers(headers_9))
		.pause(203 milliseconds)
		.exec(http("request_10")
			.put("/hearing-part/b262fc78-dc98-484b-849b-7b96d7753918")
			.headers(headers_10)
			.body(RawFileBody("listingToSessionTransactionConflict_0010_request.txt")))
		.pause(349 milliseconds)
		.exec(http("request_11")
			.options("/sessions?startDate=26-07-2018&endDate=27-07-2018")
			.headers(headers_1))
		.exec(http("request_12")
			.get("/sessions?startDate=26-07-2018&endDate=27-07-2018")
			.headers(headers_5))

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}