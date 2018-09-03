package simulations.snl

import scala.concurrent.duration._
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._
import java.util.Date
import java.util.Calendar
import java.text.SimpleDateFormat
import java.time.temporal.TemporalAdjusters
import java.time.{DayOfWeek, LocalDate, temporal}
import java.time.LocalDateTime
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class snlOfficerJourney extends Simulation {


  val date = java.time.LocalDateTime.now()

  val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
  val longerTimeFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  val revDateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

  val today: String = date.format(formatter)
  val tomorrow: String = date.plusDays(1).format(formatter)
  val plusFiveYears: String = date.plusYears(5).format(formatter)
  val dateLastSunday: String = date.`with`(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)).format(formatter)
  val dateSundayPrev: String = date.`with`(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)).minusDays(7).format(formatter)
  val scheduleStart: String = date.plusMonths(1).format(revDateFormat)
  val scheduledEnd: String = date.plusMonths(2).format(revDateFormat)
  val createdAt: String = date.format(longerTimeFormat)

  System.out.println("1. Today = " + today.toString())
  System.out.println("2. Tomorrow = " + tomorrow.toString())
  System.out.println("3. Five years on = " + plusFiveYears.toString())
  System.out.println("4. Date last Sunday = " + dateLastSunday.toString())
  System.out.println("5. Date prev Sunday = " + dateSundayPrev.toString())
  System.out.println("6. scheduleStart = " + scheduleStart.toString())
  System.out.println("7. scheduledEnd = " + scheduledEnd.toString())
  System.out.println("8. createdAt = " + createdAt.toString())

  val snlUsers = csv("snl_users.csv").circular
  val snlSessionData = csv("snl_session_data.csv").queue
  // only one record currently but I plan to remove this and replace with some random strings soon
  val snlListingData = csv("snl_listing_data.csv").circular

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

  val uri1 = "https://snl-api-snlperf.service.core-compute-snlperf.internal:443"
  val uri2 = "https://snl-frontend-snlperf.service.core-compute-snlperf.internal:443"

  //  val snlUserFeed = feed(snlUsers)
  //  val snlSessionFeed = feed(snlSessionData)
  //  val snlListingFeed = feed(snlListingData)


  val snlHome =
  // HOME PAGE

    pause(3)

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

  val snlLogin =

    pause(10)

      .feed(snlUsers)

      // LOG IN
      .exec(http("SNL_LOGIN_010_O")
      .options("/security/signin")
      .headers(headers_5)
      .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/security/signin")))

      //  {"username":"officer1","password":"asd"}

      .exec(http("SNL_LOGIN_020_P")
      .post("/security/signin")
      .headers(headers_6)
      //        .body(RawFileBody("snlOfficerJourneyNewRec_0006_request.txt")))
      .body(StringBody("""{"username":"${username}","password":"${password}"}""")).asJSON
      //      .body(StringBody("""{"username":"officer1","password":"asd"}"""))
      .check(jsonPath("$..accessToken").saveAs("bearer"))
      .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/security/signin")))

      //  System.out.println("bearer token is = ${bearer}")

      .exec(http("SNL_LOGIN_030_O")
      .options("/security/user")
      .headers(headers_7)
      .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/security/user")))

      .exec(http("SNL_LOGIN_040_G")
        .get("/security/user")
        .headers(headers_8)
        .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/security/user")))

      // LOG IN IS COMPLETE BUT BY DEFAULT THE APP GOES TO THE CALENDAR
      // REMOVE THIS LATE AND CALL IN THE SCENARIO AS FUNCTION CALL
      .exec(http("SNL_LOGIN_050_G")
      .get(uri2 + "/cfg")
      .headers(headers_9)
      .check(currentLocation.is("https://snl-frontend-snlperf.service.core-compute-snlperf.internal:443/cfg")))

      .exec(http("SNL_LOGIN_060_O")
        //        .options("/sessions?startDate=20-07-2018&endDate=21-07-2018")
        .options("/sessions?startDate=${today}&endDate=${tomorrow}")
        .headers(headers_7)
        .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/sessions?startDate=${today}&endDate=${tomorrow}")))

      .exec(http("SNL_LOGIN_070_G")
        .get("/sessions?startDate=${today}&endDate=${tomorrow}")
        .headers(headers_8)
        .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/sessions?startDate=${today}&endDate=${tomorrow}")))


  val snlMain =

    pause(3)

      // MAIN
      .exec(http("SNL_MAIN")
      .get(uri2 + "/cfg")
      .headers(headers_9)
      .check(currentLocation.is("https://snl-frontend-snlperf.service.core-compute-snlperf.internal:443/cfg")))

  val snlSessionSearch =

    pause(3)

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

  val snlSessionsCreate =

    pause(3)


      // SESSIONS - CREATE
      .exec(http("SNL_SESSION_CREATE_010_O")
      .options("/room")
      .headers(headers_7)
      .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/room")))

      .exec(http("SNL_SESSION_CREATE_020_O")
        .options("/person?personType=judge")
        .headers(headers_7)
        .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/person?personType=judge")))

      .exec(http("SNL_SESSION_CREATE_030_P")
        .get("/person?personType=judge")
        .headers(headers_8)
        .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/person?personType=judge")))

      .exec(http("SNL_SESSION_CREATE_040_G")
        .get("/room")
        .headers(headers_8)
        .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/room")))

  val snlSessionsCreateCreate =

    pause(3)

      .feed(snlSessionData)


      // CREATE
      .exec(http("SNL_SESSION_CREATE_CREATE_010_O")
      .options("/sessions")
      .headers(headers_25)
      .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/sessions")))


      .exec(http("SNL_SESSION_CREATE_CREATE_020_P")
        .put("/sessions")
        .headers(headers_26)
        //      .body(RawFileBody("snlOfficerJourneyNewRec_0026_request.txt")))
        .body(StringBody(
        """{"userTransactionId":"${hearingPartId}",
          |"id":"${sessionId}",
          |"start":"${start}",
          |"duration":${duration},
          |"roomId":"${roomId}",
          |"personId":"${personId}",
          |"caseType":"${caseType}"}""".stripMargin)).asJSON
        .check(jsonPath("$..rulesProcessingStatus").saveAs("status"))
        .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/sessions")))

      .exec(http("SNL_SESSION_CREATE_CREATE_030_O")
        .options("/problems/by-user-transaction-id?id=${hearingPartId}")
        .headers(headers_7)
        .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/problems/by-user-transaction-id?id=${hearingPartId}")))

      .exec(http("SNL_SESSION_CREATE_CREATE_040_G")
        .get("/problems/by-user-transaction-id?id=${hearingPartId}")
        .headers(headers_8)
        .check(jsonPath("$..type").optional.saveAs("errorType"))
        .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/problems/by-user-transaction-id?id=${hearingPartId}")))

  val snlSessionCreateAccept =

    pause(3)

      // ACCEPT
      .exec(http("SNL_SESSION_CREATE_ACCEPT_010_O")
      .options("/user-transaction/${hearingPartId}/commit")
      .headers(headers_29)
      .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/user-transaction/${hearingPartId}/commit")))

      .exec(http("SNL_SESSION_CREATE_ACCEPT_020_P")
        .post("/user-transaction/${hearingPartId}/commit")
        .headers(headers_26)
        //        .body(RawFileBody("snlOfficerJourneyNewRec_0030_request.txt"))
        .body(StringBody("""{}"""))
        .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/user-transaction/${hearingPartId}/commit")))


      .exec(http("SNL_SESSION_CREATE_ACCEPT_030_O")
        .options("/sessions/${sessionId}")
        .headers(headers_7)
        .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/sessions/${sessionId}")))

      .exec(http("SNL_SESSION_CREATE_ACCEPT_040_G")
        .get("/sessions/${sessionId}")
        .headers(headers_8)
        .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/sessions/${sessionId}")))

      .exec(http("SNL_SESSION_CREATE_ACCEPT_050_O")
        .options("/hearing-part")
        .headers(headers_7)
        .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/hearing-part")))

      .exec(http("SNL_SESSION_CREATE_ACCEPT_060_G")
        .get("/hearing-part")
        .headers(headers_8)
        .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/hearing-part")))

  val snlSessionRollBack =

    exec(http("SNL_ROLLBACK_010_O")
      .options("/user-transaction/${hearingPartId}/rollback")
      .headers(headers_D0)
      .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/user-transaction/${hearingPartId}/rollback")))

      .exec(http("SNL_ROLLBACK_020_P")
        .post("/user-transaction/${hearingPartId}/rollback")
        .headers(headers_D1)
        //      .body(RawFileBody("snlOfficerSessionRollback_0001_request.txt"))
        .body(StringBody("""{}"""))
        .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/user-transaction/${hearingPartId}/rollback")))

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


  val snlCalendar =

    pause(3)

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


  val snlPlanner =

    pause(3)

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

  val listingNew =

    pause(5)

      .feed(snlListingData)


  // LISTING (NEW)
  exec(http("SNL_LISTING_NEW_010_G")
    .get(uri2 + "/cfg")
    .headers(headers_9)
    .check(currentLocation.is("https://replacethis")))

    // SAVE NEW LISTING
    .exec(http("SNL_LISTING_NEW_020_O")
    .options("/hearing-part")
    .headers(headers_25)
    .check(currentLocation.is("https://replacethis")))

    .exec(http("SNL_LISTING_NEW_030_P")
      .put("/hearing-part")
      .headers(headers_26)
      //      .body(RawFileBody("snlOfficerJourneyNewRec_0045_request.txt")))
      .body(StringBody(
      """{"id":"${sessionId}",
          |"caseNumber":"123456",
          |"caseTitle":"Aay vs Bee",
          |"hearingType":"Preliminary Hearing",
          |"duration":"PT1H",
          |"scheduleStart":"${scheduleStart}T23:00:00.000Z",
          |"scheduleEnd":"${scheduleEnd}T23:00:00.000Z",
          "createdAt":"${createdAt}"}""".stripMargin)).asJSON
      .check(currentLocation.is("https://replacethis")))

  // ADD A CHECK FOR NEW LISTING REQUEST CREATED

  val snlProblems =
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

  val snlLogout =
  // LOGOUT
    exec(http("SNL_LOGOUT_010_G")
      .get(uri2 + "/cfg")
      .headers(headers_2)
      .check(currentLocation.is("https://snl-frontend-snlperf.service.core-compute-snlperf.internal:443/cfg")))

      .exec(http("SNL_LOGOUT_020_O")
        .options("/security/user")
        .headers(headers_3)
        .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/security/user")))

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

      .exec(http("SNL_LOGOUT_060_O")
        .options("/security/user")
        .headers(headers_3)
        .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/security/user")))

      .exec(http("SNL_LOGOUT_070_G")
        .get("/security/user")
        .headers(headers_4)
        .check(status.is(401))
        .check(currentLocation.is("https://snl-api-snlperf.service.core-compute-snlperf.internal/security/user")))

  val scn = scenario("snlOfficerJourneyNewRec")

    //    .feed(snlUsers)
    //    .feed(snlSessionData)
    //    .feed(snlListingData)

    .exec(session => session.set("today", today.toString()))
    .exec(session => session.set("tomorrow", tomorrow.toString()))
    .exec(session => session.set("plusFiveYears", plusFiveYears.toString()))
    .exec(session => session.set("dateLastSunday", dateLastSunday.toString()))
    .exec(session => session.set("dateSundayPrev", dateSundayPrev.toString()))
    .exec(session => session.set("scheduleStart", scheduleStart.toString()))
    .exec(session => session.set("scheduledEnd", scheduledEnd.toString()))
    .exec(session => session.set("createdAt", createdAt.toString()))
    .exec(session => session.set("hearingPartId", java.util.UUID.randomUUID()))
    .exec(session => session.set("sessionId", java.util.UUID.randomUUID()))
    //

    // copied from gatling.io documetation but does not work yat:
    .exec { session =>
    println(session)
    session
  }
    .exec(snlHome)
    .exec(snlLogin)
    .exec(snlMain)
    //    .exec(snlSessionSearch)
    //    .exec(snlSessionsCreate)
    //    .exec(snlSessionsCreateCreate)
    //
    //    .doIfOrElse(_.contains("errorType"))
    //      {
    //        // Equals chain
    //        exec(snlSessionRollBack)
    //        // should print the error for debug?
    //      }
    //      {
    //        // Else chain
    //        exec(snlSessionCreateAccept)
    //      }
    //
    //    .exec(snlCalendar)
        .exec(snlPlanner)

    .exec(listingNew)

    //    .exec(snlProblems)
    .exec(snlLogout)

  setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)

  //    setUp(scn.inject(rampUsers(60) over (300 seconds))).protocols(httpProtocol)
}