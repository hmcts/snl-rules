package simulations.snl

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class OfficerJourney extends Simulation {

  val date = java.time.LocalDateTime.now()
  val startDate = java.time.LocalDate.of(2018, 12, 31);
  val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
  val longerTimeFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  val revDateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

  val today: String = date.format(formatter)
  val tomorrow: String = date.plusDays(1).format(formatter)

  //val snlUsers = csv("snl_users.csv").circular
  val snlUsers = for (i <- 1 until 1000) yield {
    Map("username" -> ("officer"+i), "password" -> "asd");
  }

  //val snlSessionData = csv("snl_session_data.csv").queue
  // only one record currently but I plan to remove this and replace with some random strings soon
  //val snlListingData = csv("snl_listing_data.csv").circular

  val jdbcUsername = System.getProperty("jdbc.username");
  val jdbcPassword = System.getProperty("jdbc.password");
  val jdbcUrl = System.getProperty("jdbc.url");

  val roomFeeder = jdbcFeeder(jdbcUrl, jdbcUsername, jdbcPassword, "SELECT id FROM room").circular
  val judgeFeeder = jdbcFeeder(jdbcUrl, jdbcUsername, jdbcPassword, "SELECT id FROM person WHERE person_type='JUDGE'").circular

  val caseTypeFeeder = Array(
    Map("caseType" -> "SCLAIMS"),
    Map("caseType" -> "FTRACK"),
    Map("caseType" -> "MTRACK")
  ).circular
  
//  .feed(snlUsers)
//  .feed(snlSessionData)
//  .feed(snlListingData)

  val httpProtocol = http
    .baseURL("https://snl-api-snlperf.service.core-compute-snlperf.internal")
    .proxy(Proxy("proxyout.reform.hmcts.net", 8080).httpsPort(8080))
    .acceptHeader("application/json, text/plain, */*")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .doNotTrackHeader("1")
    .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:62.0) Gecko/20100101 Firefox/62.0")

  val headers_0 = Map(
    "Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8",
    "Upgrade-Insecure-Requests" -> "1")

  val headers_1 = Map("Accept" -> "text/css,*/*;q=0.1")

  val headers_2 = Map("X-Requested-With" -> "XMLHttpRequest")

  val headers_3 = Map(
    "Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8",
    "Access-Control-Request-Headers" -> "x-requested-with",
    "Access-Control-Request-Method" -> "GET",
    "Origin" -> "https://snl-frontend-snlperf.service.core-compute-snlperf.internal")

  val headers_4 = Map(
    "Origin" -> "https://snl-frontend-snlperf.service.core-compute-snlperf.internal",
    "X-Requested-With" -> "XMLHttpRequest")

  val headers_5 = Map(
    "Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8",
    "Access-Control-Request-Headers" -> "content-type,x-requested-with",
    "Access-Control-Request-Method" -> "POST",
    "Origin" -> "https://snl-frontend-snlperf.service.core-compute-snlperf.internal")

  val headers_6 = Map(
    "Content-Type" -> "application/json",
    "Origin" -> "https://snl-frontend-snlperf.service.core-compute-snlperf.internal",
    "X-Requested-With" -> "XMLHttpRequest")

  val headers_7 = Map(
    "Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8",
    "Access-Control-Request-Headers" -> "authorization,x-requested-with",
    "Access-Control-Request-Method" -> "GET",
    "Origin" -> "https://snl-frontend-snlperf.service.core-compute-snlperf.internal")

  val headers_8 = Map(
    "Authorization" -> "Bearer ${bearer}",
//    "Authorization" -> "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJvZmZpY2VyMSIsImlhdCI6MTUzMjA5NDE2OSwiZXhwIjoxNTMyMDk1OTY5fQ.Wp9jqLTXpVHvdqct65lkWzb9-NoU8Xf94lAD1zXzqYYCVfmDwkOIWNmWJFX3tIO2Q_TodHFG_C9IooM5QFMJJQ",
    "Origin" -> "https://snl-frontend-snlperf.service.core-compute-snlperf.internal",
    "X-Requested-With" -> "XMLHttpRequest")

  val headers_9 = Map(
    "Authorization" -> "Bearer ${bearer}",
//    "Authorization" -> "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJvZmZpY2VyMSIsImlhdCI6MTUzMjA5NDE2OSwiZXhwIjoxNTMyMDk1OTY5fQ.Wp9jqLTXpVHvdqct65lkWzb9-NoU8Xf94lAD1zXzqYYCVfmDwkOIWNmWJFX3tIO2Q_TodHFG_C9IooM5QFMJJQ",
    "X-Requested-With" -> "XMLHttpRequest")

  val headers_25 = Map(
    "Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8",
    "Access-Control-Request-Headers" -> "authorization,content-type,x-requested-with",
    "Access-Control-Request-Method" -> "PUT",
    "Origin" -> "https://snl-frontend-snlperf.service.core-compute-snlperf.internal")

  val headers_26 = Map(
    "Authorization" -> "Bearer ${bearer}",
//    "Authorization" -> "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJvZmZpY2VyMSIsImlhdCI6MTUzMjA5NDE2OSwiZXhwIjoxNTMyMDk1OTY5fQ.Wp9jqLTXpVHvdqct65lkWzb9-NoU8Xf94lAD1zXzqYYCVfmDwkOIWNmWJFX3tIO2Q_TodHFG_C9IooM5QFMJJQ",
    "Content-Type" -> "application/json",
    "Origin" -> "https://snl-frontend-snlperf.service.core-compute-snlperf.internal",
    "X-Requested-With" -> "XMLHttpRequest")

  val headers_29 = Map(
    "Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8",
    "Access-Control-Request-Headers" -> "authorization,content-type,x-requested-with",
    "Access-Control-Request-Method" -> "POST",
    "Origin" -> "https://snl-frontend-snlperf.service.core-compute-snlperf.internal")

  val headers_D0 = Map(
    "Access-Control-Request-Headers" -> "authorization,content-type,x-requested-with",
    "Access-Control-Request-Method" -> "POST",
    "Origin" -> "https://snl-frontend-snlperf.service.core-compute-snlperf.internal")

  val headers_D1 = Map(
    "Accept" -> "application/json, text/plain, */*",
    "Authorization" -> "Bearer ${bearer}",
    "Content-Type" -> "application/json",
    "Origin" -> "https://snl-frontend-snlperf.service.core-compute-snlperf.internal",
    "X-Requested-With" -> "XMLHttpRequest")

  val headers_A0 = Map(
    "Access-Control-Request-Headers" -> "authorization,x-requested-with",
    "Access-Control-Request-Method" -> "GET",
    "Origin" -> "https://snl-frontend-snlperf.service.core-compute-snlperf.internal")

  val headers_A1 = Map(
    "Accept" -> "application/json, text/plain, */*",
    //"Authorization" -> "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJvZmZpY2VyMSIsImlhdCI6MTUzMjY4MjMwMCwiZXhwIjoxNTMyNjg0MTAwfQ.LHp2HsJNjbqJOSoAxkJQkPQ8Ae0BmBfuWpiomeiLGilz_zRaYqJrtYOGwihUYl-2IyAkXkmEFn_UqGqYQBYEtQ",
    "Authorization" -> "Bearer ${bearer}",
    "Origin" -> "https://snl-frontend-snlperf.service.core-compute-snlperf.internal",
    "X-Requested-With" -> "XMLHttpRequest")

  val headers_A2 = Map(
    "Access-Control-Request-Headers" -> "authorization,content-type,x-requested-with",
    "Access-Control-Request-Method" -> "PUT",
    "Origin" -> "https://snl-frontend-snlperf.service.core-compute-snlperf.internal")

  val headers_A3 = Map(
    "Accept" -> "application/json, text/plain, */*",
    //"Authorization" -> "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJvZmZpY2VyMSIsImlhdCI6MTUzMjY4MjMwMCwiZXhwIjoxNTMyNjg0MTAwfQ.LHp2HsJNjbqJOSoAxkJQkPQ8Ae0BmBfuWpiomeiLGilz_zRaYqJrtYOGwihUYl-2IyAkXkmEFn_UqGqYQBYEtQ",
    "Authorization" -> "Bearer ${bearer}",
    "Content-Type" -> "application/json",
    "Origin" -> "https://snl-frontend-snlperf.service.core-compute-snlperf.internal",
    "X-Requested-With" -> "XMLHttpRequest")

  val headers_A6 = Map(
    "Access-Control-Request-Headers" -> "authorization,content-type,x-requested-with",
    "Access-Control-Request-Method" -> "POST",
    "Origin" -> "https://snl-frontend-snlperf.service.core-compute-snlperf.internal")


  val uri1 = "https://snl-api-snlperf.service.core-compute-snlperf.internal:443"
  val uri2 = "https://snl-frontend-snlperf.service.core-compute-snlperf.internal:443"

//  val snlUserFeed = feed(snlUsers)
//  val snlSessionFeed = feed(snlSessionData)
//  val snlListingFeed = feed(snlListingData)

  val pause_factor = 1

  object Home{

    val home =
    // HOME PAGE

      pause(pause_factor * 3)

        .exec(http("SNL_HOME_010_G")
          .get(uri2 + "/")
          .headers(headers_0))

        .exec(http("SNL_HOME_020_G")
          .get(uri2 + "/cfg")
          .headers(headers_2))

        .exec(http("SNL_HOME_030_O")
          .options("/security/user")
          .headers(headers_3))

        .exec(http("SNL_HOME_040_G")
          .get("/security/user")
          .headers(headers_4)
          .check(status.is(401))
          //        .check(currentLocation.is("https://snl-frontend-snlperf.service.core-compute-snlperf.internal/security/user")))
          .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/security/user")))

  }

  object LoginOut {

    val login =

      pause(pause_factor * 10)


        // LOG IN
//        .exec(http("SNL_LOGIN_010_O")
//        .options("/security/signin")
//        .headers(headers_5)
//        .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/security/signin")))

        //  {"username":"officer1","password":"asd"}

        .exec(http("SNL_LOGIN_020_P")
        .post("/security/signin")
        .headers(headers_6)
        .body(StringBody("""{"username":"${username}","password":"${password}"}""")).asJSON
        .check(jsonPath("$..accessToken").saveAs("bearer"))
        .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/security/signin")))

        //  System.out.println("bearer token is = ${bearer}")

//        .exec(http("SNL_LOGIN_030_O")
//        .options("/security/user")
//        .headers(headers_7)
//        .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/security/user")))
//
        .exec(http("SNL_LOGIN_040_G")
          .get("/security/user")
          .headers(headers_8)
          .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/security/user")))

        // LOG IN IS COMPLETE BUT BY DEFAULT THE APP GOES TO THE CALENDAR
        // REMOVE THIS LATE AND CALL IN THE SCENARIO AS FUNCTION CALL
//        .exec(http("SNL_LOGIN_050_G")
//        .get(uri2 + "/cfg")
//        .headers(headers_9)
//        .check(currentLocation.is("https://snl-frontend-snlperf.service.core-compute-snlperf.internal:443/cfg")))
//
//        .exec(http("SNL_LOGIN_060_O")
//          //        .options("/sessions?startDate=20-07-2018&endDate=21-07-2018")
//          .options("/sessions?startDate=${today}&endDate=${tomorrow}")
//          .headers(headers_7)
//          .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/sessions?startDate=${today}&endDate=${tomorrow}")))
//
//        .exec(http("SNL_LOGIN_070_G")
//          .get("/sessions?startDate=${today}&endDate=${tomorrow}")
//          .headers(headers_8)
//          .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/sessions?startDate=${today}&endDate=${tomorrow}")))

    val logout =
    // LOGOUT
      pause(pause_factor)
      .exec(http("SNL_LOGOUT_010_G")
        .get(uri2 + "/cfg")
        .headers(headers_2)
        .check(currentLocation.is("https://snl-frontend-snlperf.service.core-compute-snlperf.internal:443/cfg")))

//        .exec(http("SNL_LOGOUT_020_O")
//          .options("/security/user")
//          .headers(headers_3)
//          .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/security/user")))

        .exec(http("SNL_LOGOUT_030_G")
          .get("/security/user")
          .headers(headers_4)
          .check(status.is(401))
          .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/security/user")))

        .exec(http("SNL_LOGOUT_040_G")
          .get(uri2 + "/auth/login")
          .headers(headers_0)
          .check(currentLocation.is("https://snl-frontend-snlperf.service.core-compute-snlperf.internal:443/auth/login")))

        .exec(http("SNL_LOGOUT_050_G")
          .get(uri2 + "/cfg")
          .headers(headers_2)
          .check(currentLocation.is("https://snl-frontend-snlperf.service.core-compute-snlperf.internal:443/cfg")))

//        .exec(http("SNL_LOGOUT_060_O")
//          .options("/security/user")
//          .headers(headers_3)
//          .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/security/user")))

        .exec(http("SNL_LOGOUT_070_G")
          .get("/security/user")
          .headers(headers_4)
          .check(status.is(401))
          .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/security/user")))



  }

  object Mainpage {

    val mainpage =

      pause(pause_factor * 3)

        // MAIN
        .exec(http("SNL_MAIN")
        .get(uri2 + "/cfg")
        .headers(headers_9)
        .check(currentLocation.is("https://snl-frontend-snlperf.service.core-compute-snlperf.internal:443/cfg")))
  }

  object Sessions {

    val search =

      pause(pause_factor * 3)

        // SESSIONS - SEARCH
        .exec(http("SNL_SESSIONS_SEARCH_010_G")
        .get(uri2 + "/cfg")
        .headers(headers_9)
        .check(currentLocation.is("https://snl-frontend-snlperf.service.core-compute-snlperf.internal:443/cfg")))

        .exec(http("SNL_SESSIONS_SEARCH_020_O")
          .options("/sessions?startDate=${today}&endDate=${plusFiveYears}")
          .headers(headers_7)
          .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/sessions?startDate=${today}&endDate=${plusFiveYears}")))

        .exec(http("SNL_SESSIONS_SEARCH_030_O")
          .options("/hearing-part")
          .headers(headers_7)
          .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/hearing-part")))

        .exec(http("SNL_SESSIONS_SEARCH_040_G")
          .options("/person?personType=judge")
          .headers(headers_7)
          .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/person?personType=judge")))

        .exec(http("SNL_SESSIONS_SEARCH_050_O")
          .options("/room")
          .headers(headers_7)
          .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/room")))

        .exec(http("SNL_SESSIONS_SEARCH_060_G")
          .get("/hearing-part")
          .headers(headers_8)
          .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/hearing-part")))

        .exec(http("SNL_SESSIONS_SEARCH_070_G")
          .get("/person?personType=judge")
          .headers(headers_8)
          .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/person?personType=judge")))

        .exec(http("SNL_SESSIONS_SEARCH_080_G")
          .get("/sessions?startDate=${today}&endDate=${plusFiveYears}")
          .headers(headers_8)
          .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/sessions?startDate=${today}&endDate=${plusFiveYears}")))

        .exec(http("SNL_SESSIONS_SEARCH_090_G")
          .get("/room")
          .headers(headers_8)
          .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/room")))

    val createSession =

      pause(pause_factor * 3)


        // SESSIONS - CREATE
        .exec(http("SNL_SESSION_CREATE_010_O")
        .options("/room")
        .headers(headers_7)
        .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/room")))

        .exec(http("SNL_SESSION_CREATE_020_O")
          .options("/person?personType=judge")
          .headers(headers_7)
          .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/person?personType=judge")))

        .exec(http("SNL_SESSION_CREATE_030_G")
          .get("/person?personType=judge")
          .headers(headers_8)
          .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/person?personType=judge")))

        .exec(http("SNL_SESSION_CREATE_040_G")
          .get("/room")
          .headers(headers_8)
          .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/room")))

    val createSessionCreate =

      pause(pause_factor * 3)

        //.feed(snlSessionData)

        // CREATE
//        .exec(http("SNL_SESSION_CREATE_CREATE_010_O")
//        .options("/sessions")
//        .headers(headers_25)
//        .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/sessions")))

        .exec(http("SNL_SESSION_CREATE_CREATE_020_P")
          .put("/sessions")
          .headers(headers_26)
          .body(StringBody(
          """{"userTransactionId":"${userTransactionId}",
            |"id":"${sessionId}",
            |"personId":"${personId}",
            |"roomId":"${roomId}",
            |"start":"${sessionStart}",
            |"duration":"PT2H",
            |"caseType":"${sessionCaseType}"}""".stripMargin)).asJSON
          .check(jsonPath("$..rulesProcessingStatus").saveAs("status"))
          .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/sessions")))

//        .exec(session => {println(session); session})
//
//        .exec(http("SNL_SESSION_CREATE_CREATE_030_O")
//          .options("/problems/by-user-transaction-id?id=${userTransactionId}")
//          .headers(headers_7)
//          .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/problems/by-user-transaction-id?id=${userTransactionId}")))
//
//        .exec(http("SNL_SESSION_CREATE_CREATE_040_G")
//          .get("/problems/by-user-transaction-id?id=${userTransactionId}")
//          .headers(headers_8)
//          .check(jsonPath("$..type").optional.saveAs("errorType"))
//          .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/problems/by-user-transaction-id?id=${userTransactionId}")))
//
//        .doIfOrElse(_.contains("errorType"))
//          {
//            // Equals chain
//            //exec(Sessions.createAccept)
//            exec(Sessions.rollBack)
//            // should print the error for debug?
//          }
//          {
//            // Else chain
//            //exec(Sessions.rollBack)
//            exec(Sessions.createAccept)
//          }

    val createAccept =

      pause(pause_factor * 3)

      // ACCEPT
//      .exec(http("SNL_SESSION_CREATE_ACCEPT_010_O")
//        .options("/user-transaction/${userTransactionId}/commit")
//        .headers(headers_29)
//        .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/user-transaction/${userTransactionId}/commit")))

        .exec(http("SNL_SESSION_CREATE_ACCEPT_020_P")
          .post("/user-transaction/${userTransactionId}/commit")
          .headers(headers_26)
          .body(RawFileBody("snlOfficerJourneyNewRec_0030_request.txt"))
          .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/user-transaction/${userTransactionId}/commit")))
        //        .body(StringBody"""{}"""))

//        .exec(http("SNL_SESSION_CREATE_ACCEPT_030_O")
//        .options("/sessions/${sessionId}")
//        .headers(headers_7)
//        .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/sessions/${sessionId}")))
//
//        .exec(http("SNL_SESSION_CREATE_ACCEPT_040_G")
//          .get("/sessions/${sessionId}")
//          .headers(headers_8)
//          .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/sessions/${sessionId}")))
//
//        .exec(http("SNL_SESSION_CREATE_ACCEPT_050_O")
//          .options("/hearing-part")
//          .headers(headers_7)
//          .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/hearing-part")))
//
//        .exec(http("SNL_SESSION_CREATE_ACCEPT_060_G")
//          .get("/hearing-part")
//          .headers(headers_8)
//          .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/hearing-part")))

    val rollBack =

      exec(http("SNL_ROLLBACK_010_O")
        .options("/user-transaction/${userTransactionId}/rollback")
        .headers(headers_D0)
        .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/user-transaction/${userTransactionId}/rollback")))

        .exec(http("SNL_ROLLBACK_020_P")
          .post("/user-transaction/${userTransactionId}/rollback")
          .headers(headers_D1)
          .body(RawFileBody("snlOfficerSessionRollback_0001_request.txt"))
          .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/user-transaction/${userTransactionId}/rollback")))

        .exec(http("SNL_ROLLBACK_030_O")
          .options("/sessions/${sessionId}")
          .headers(headers_7)
          .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/sessions/${sessionId}")))

        .exec(http("SNL_ROLLBACK_040_G")
          .get("/sessions/${sessionId}")
          .headers(headers_8)
          .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/sessions/${sessionId}")))

        .exec(http("SNL_ROLLBACK_050_O")
          .options("/hearing-part")
          .headers(headers_7)
          .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/hearing-part")))

        .exec(http("SNL_ROLLBACK_060_G")
          .get("/hearing-part")
          .headers(headers_8)
          .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/hearing-part")))



  }

  object Calendar {

    val calendar =

      pause(pause_factor * 3)

        // CALENDAR
        .exec(http("SNL_CALENDAR_010_G")
        .get(uri2 + "/cfg")
        .headers(headers_9)
        .check(currentLocation.is("https://snl-frontend-snlperf.service.core-compute-snlperf.internal:443/cfg")))

        .exec(http("SNL_CALENDAR_020_O")
          .options("/sessions?startDate=${today}&endDate=${tomorrow}")
          .headers(headers_7)
          .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/sessions?startDate=${today}&endDate=${tomorrow}")))

        .exec(http("SNL_CALENDAR_030_G")
          .get("/sessions?startDate=${today}&endDate=${tomorrow}")
          .headers(headers_8)
          .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/sessions?startDate=${today}&endDate=${tomorrow}")))
  }


  object Planner {

    val planner =

      pause(pause_factor * 3)

        // PLANNER
        .exec(http("SNL_PLANNER_010_G")
        .get(uri2 + "/cfg")
        .headers(headers_9)
        .check(currentLocation.is("https://snl-frontend-snlperf.service.core-compute-snlperf.internal:443/cfg")))

        .exec(http("SNL_PLANNER_020_O")
          .options("/room")
          .headers(headers_7)
          .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/room")))

        .exec(http("SNL_PLANNER_030_G")
          .get("/room")
          .headers(headers_8)
          .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/room")))

        .exec(http("SNL_PLANNER_040_O")
          .options("/sessions?startDate=${dateSundayPrev}&endDate=${dateLastSunday}")
          .headers(headers_7)
          .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/sessions?startDate=${dateSundayPrev}&endDate=${dateLastSunday}")))

        .exec(http("SNL_PLANNER_050_G")
          .get("/sessions?startDate=${dateSundayPrev}&endDate=${dateLastSunday}")
          .headers(headers_8)
          .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/sessions?startDate=${dateSundayPrev}&endDate=${dateLastSunday}")))


  }

  object Listing {

    val listingNew =

      pause(pause_factor * 5)

      //.feed(snlListingData)

    // LISTING (NEW)
//      .exec(http("SNL_LISTING_NEW_010_G")
//      .get(uri2 + "/cfg")
//      .headers(headers_9)
//      .check(currentLocation.is("https://snl-frontend-snlperf.service.core-compute-snlperf.internal:443/cfg")))

      // SAVE NEW LISTING
//      .exec(http("SNL_LISTING_NEW_020_O")
//      .options("/hearing-part")
//      .headers(headers_25)
//      .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/hearing-part")))

//      .exec(http("SNL_LISTING_NEW_030_P")
//      .put("/hearing-part")
//      .headers(headers_26)
//      .body(StringBody(
//      """{"id":"${sessionId}",
//          |"caseNumber":"123456",
//          |"caseTitle":"Aay vs Bee",
//          |"hearingType":"Preliminary Hearing",
//          |"duration":"PT30M",
//          |"scheduleStart":"${scheduleStart}T23:00:00.000Z",
//          |"scheduleEnd":"${scheduledEnd}T23:00:00.000Z",
//          "createdAt":"${createdAt}"}""".stripMargin)).asJSON
//      .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/hearing-part")))

        .exec(session => session.set("createdAt", LocalDateTime.now().format(longerTimeFormat)))
        .exec(http("SNL_LISTING_NEW_030_P")
        .put("/hearing-part")
        .headers(headers_26)
        .body(StringBody(
          """{"id":"${listingId}",
    |"caseNumber":"123456",
    |"caseTitle":"Aay vs Bee",
    |"caseType":"${listingCaseType}",
    |"hearingType":"Preliminary Hearing",
    |"duration":"${listingDuration}",
    |"scheduleStart":"${scheduleStart}",
    |"scheduleEnd":"${scheduleEnd}",
    |"createdAt":"${createdAt}"}""".stripMargin)).asJSON
//        .check(regex("Listing added successfully"))
        .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/hearing-part")))

    // ADD A CHECK FOR NEW LISTING REQUEST CREATED

  }


  object Problems {

    val problems =
    // PROBLEMS
      exec(http("SNL_PROBLEMS_010_G")
        .get(uri2 + "/cfg")
        .headers(headers_9)
        .check(currentLocation.is("https://snl-frontend-snlperf.service.core-compute-snlperf.internal:443/cfg")))

        .exec(http("SNL_PROBLEMS_020_O")
          .options("/problems")
          .headers(headers_7)
          .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/problems")))

        .exec(http("SNL_PROBLEMS_030_G")
          .get("/problems")
          .headers(headers_8)
          .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/problems")))


  }

  object AssignListing {
    val assignListing =

      pause(pause_factor * 5)

    //    exec(http("SNL_ASSIGN_LISTING_010_O")
//      .options("/sessions?startDate=${searchStart}&endDate=${searchEnd}")
//      .headers(headers_A0))
//
//      .pause(pause_factor * 1)
//
//      .exec(http("SNL_ASSIGN_LISTING_020_G")
//        .get("/sessions?startDate=${searchStart}&endDate=${searchEnd}")
//        .headers(headers_A1))
//
//      .pause(pause_factor * 1)
//
//      .exec(http("SNL_ASSIGN_LISTING_030_O")
//        .options("/hearing-part/${listingId}")
//        .headers(headers_A2))
//
//      .pause(pause_factor * 1)

        .exec(http("SNL_ASSIGN_LISTING_040_P")
        .put("/hearing-part/${listingId}")
        .headers(headers_A3)
//        .body(RawFileBody("listingToSessionAlt_0003_request.txt")))
        .body(StringBody(
        """{"sessionId":"${sessionId}",
          |"start":"${listingStart}",
          |"userTransactionId":"${userTransactionId}"}""".stripMargin)).asJSON)
//      .pause(pause_factor * 1)
//
//      .exec(http("SNL_ASSIGN_LISTING_050_O")
//        .options("/problems/by-user-transaction-id?id=${userTransactionId}")
//        .headers(headers_A0))
//
//      .exec(http("SNL_ASSIGN_LISTING_060_G")
//        .get("/problems/by-user-transaction-id?id=${userTransactionId}")
//        .headers(headers_A1))
//
//      .pause(pause_factor * 4)
//
//      .exec(http("SNL_ASSIGN_LISTING_070_O")
//        .options("/user-transaction/${userTransactionId}/commit")
//        .headers(headers_A6))
//
//      .pause(pause_factor * 1)
//
//      .exec(http("SNL_ASSIGN_LISTING_080_P")
//        .post("/user-transaction/${userTransactionId}/commit")
//        .headers(headers_A3)
//        .body(RawFileBody("listingToSessionAlt_0007_request.txt")))
//
//      .pause(pause_factor * 1)
//
//      .exec(http("SNL_ASSIGN_LISTING_090_O")
//        .options("/sessions/${sessionId}")
//        .headers(headers_A0))
//
//      .exec(http("SNL_ASSIGN_LISTING_100_G")
//        .get("/sessions/${sessionId}")
//        .headers(headers_A1))
//
//      .exec(http("SNL_ASSIGN_LISTING_110_O")
//        .options("/hearing-part")
//        .headers(headers_A0))
//
//      .pause(pause_factor * 1)
//
//      .exec(http("SNL_ASSIGN_LISTING_120_G")
//        .get("/hearing-part")
//        .headers(headers_A1))
  }

  object CreateSessionsAndListings {

    val createSessionForSlot =
      exec(session => session.set("sessionId", java.util.UUID.randomUUID()))
        .exec(session => session.set("userTransactionId", java.util.UUID.randomUUID()))
        //.exec(Sessions.createSession)
        .exec(Sessions.createSessionCreate)
        .exec(Sessions.createAccept)

    val createListingForSlot =
      exec(session => session.set("scheduleStart", startDate.plusWeeks(session("week").as[Int]).atTime(23, 0).format(longerTimeFormat)))
        .exec(session => session.set("scheduleEnd", startDate.plusWeeks(session("week").as[Int] + 1).atTime(23, 0).format(longerTimeFormat)))
        .exec(session => session.set("listingId", java.util.UUID.randomUUID()))
        .exec(Listing.listingNew)

    val createWrongCaseTypeListingForSlot =
      feed(caseTypeFeeder)
        .exec(session => session.set("listingCaseType", session("caseType").as[String]))
        .exec(createListingForSlot)

    val createWrongLengthListingForSlot =
      exec(session => session.set("listingDuration", "PT3H"))
        .exec(createListingForSlot)

    val createSometimesWrongListingForSlot =
      exec(session => session.set("listingDuration", "PT30M"))
        .exec(session => session.set("listingRoomId", session("roomId").as[String]))
        .exec(session => session.set("listingPersonId", session("personId").as[String]))
        .exec(session => session.set("listingCaseType", session("sessionCaseType").as[String]))
        .randomSwitch(
          15.0 -> exec(createWrongCaseTypeListingForSlot),
          //2.0 -> exec(createWrongLengthListingForSlot),
          85.0 -> exec(createListingForSlot)
        )

    val assignListing =
      exec(session => session.set("searchStart", startDate.format(formatter)))
        .exec(session => session.set("searchEnd", startDate.plusWeeks(1).format(formatter)))
        .exec(session => session.set("userTransactionId", java.util.UUID.randomUUID()))
        .exec(AssignListing.assignListing)

    val createSessionsAndListingsForSlot =
      feed(roomFeeder)
        .exec(session => session.set("hour", 9 + session("slot").as[Int] * 2))
        .exec(session => session.set("roomId", session("id").as[String]))
        .feed(judgeFeeder)
        .exec(session => session.set("personId", session("id").as[String]))
        .feed(caseTypeFeeder)
        .exec(session => session.set("sessionCaseType", session("caseType").as[String]))
        .exec(session => session.set("sessionStart", startDate.plusDays(session("day").as[Int])
          .atTime(session("hour").as[Int], 0).format(longerTimeFormat)))
        .exec(createSessionForSlot)
        .repeat(4, "l") {
          exec(session => session.set("listingStart", startDate.plusDays(session("day").as[Int])
            .atTime(session("hour").as[Int] + (session("l").as[Int] * 30) / 60, (session("l").as[Int] * 30) % 60).format(longerTimeFormat)))
            .exec(createSometimesWrongListingForSlot)
            .exec(assignListing)
        }

    val createSessionsAndListingsForWeek =
      repeat(5, "d") {
        exec(LoginOut.login)
        .exec(session => session.set("day", (80 + session("week").as[Int]) * 7 + session("d").as[Int]))
        .repeat(4, "slot") {
          exec(createSessionsAndListingsForSlot)
        }
        .exec(LoginOut.logout)
      }

    val createSessionsAndListingsForYear =
      repeat(4, "week") {
        //exec(session => session.set("week", 64)
          exec(createSessionsAndListingsForWeek)
      }
  }

  val scn = scenario("createSessionAndListings")

    .exec(session => session.set("today", today))
    .exec(session => session.set("tomorrow", tomorrow))
    .feed(snlUsers)

    // copied from gatling.io documetation but does not work yat:
//  .exec { session =>
//    println(session)
//    session
//  }

    //.exec(Home.home)
//    .exec(LoginOut.login)
    //.exec(Mainpage.mainpage)
    //.exec(Sessions.search)
    .exec(CreateSessionsAndListings.createSessionsAndListingsForYear)
   //.exec(Sessions.rollBack)
    //.exec(Calendar.calendar)
    //.exec(Planner.planner)
    //.exec(Listing.listingNew)
    //.exec(Problems.problems)
//    .exec(LoginOut.logout)

  //setUp(scn.inject(atOnceUsers(600))).protocols(httpProtocol)

  setUp(scn.inject(rampUsers(600) over (300))).protocols(httpProtocol)
}