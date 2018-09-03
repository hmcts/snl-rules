package simulations.probate

import io.gatling.core.Predef.pause
import io.gatling.http.Predef.{http, status}

class probateExecutors {

//  val chain_4 = pause(8)
//    .exec(http("PROBATE_210_EXECUTORS_01")
//      .post("/executors-number")
//      .headers(headers_69)
//      .formParam("_csrf", "")
//      .formParam("executorsNumber", "2")
//      .check(status.is(200)))
//
//    .pause(10)
//
//    .exec(http("PROBATE_220_EXECUTORS_02")
//      .post("/executors-names")
//      .headers(headers_69)
//      .formParam("_csrf", "")
//      .formParam("executorName[0]", "Paul Poor")
//      .check(status.is(200)))
//
//    .pause(5)
//
//    .exec(http("PROBATE_230_EXECUTORS_03")
//      .post("/executors-all-alive")
//      .headers(headers_69)
//      .formParam("_csrf", "")
//      .formParam("allalive", "Yes")
//      .check(status.is(200)))
//
//    .pause(5)
//
//    .exec(http("PROBATE_240_EXECUTORS_04")
//      .post("/other-executors-applying")
//      .headers(headers_69)
//      .formParam("_csrf", "")
//      .formParam("otherExecutorsApplying", "Yes")
//      .check(status.is(200)))
//
//    .pause(20)
//
//    .exec(http("PROBATE_250_EXECUTORS_05")
//      .post("/executors-dealing-with-estate")
//      .headers(headers_69)
//      .formParam("_csrf", "")
//      .formParam("executorsApplying[]", "Paul Poor")
//      .check(status.is(200)))
//
//
//  val chain_5 = pause(3)
//    .exec(http("PROBATE_260_EXECUTORS_06")
//      .post("/executors-alias")
//      .headers(headers_69)
//      .formParam("_csrf", "")
//      .formParam("alias", "No")
//      .check(status.is(200)))
//
//    .pause(20)
//
//    .exec(http("PROBATE_270_EXECUTORS_07")
//      .post("/executor-contact-details/*")
//      .headers(headers_69)
//      .formParam("_csrf", "")
//      .formParam("email", "dcspamkill-2017@yahoo.co.uk")
//      .formParam("mobile", "07853989215")
//      .check(status.is(200)))
//
//    .pause(10)
//
//    .exec(http("PROBATE_280_EXECUTORS_01")
//      .post("/find-address")
//      .headers(headers_69)
//      .formParam("_csrf", "")
//      .formParam("postcode", "KT2 5BU")
//      .formParam("referrer", "ExecutorAddress")
//      .formParam("findaddress", "Find UK address")
//      .formParam("addressFound", "none")
//      .formParam("freeTextAddress", "")
//      .check(status.is(200)))
//
//    .pause(5)
//
//    .exec(http("PROBATE_290_EXECUTORS_02")
//      .post("/executor-address/*")
//      .headers(headers_69)
//      .formParam("_csrf", "")
//      .formParam("postcode", "KT2 5BU")
//      .formParam("referrer", "ExecutorAddress")
//      .formParam("addressFound", "true")
//      .formParam("postcodeAddress", "Flat 10 Bramber House Royal Quarter Seven Kings Way Kingston Upon Thames KT2 5BU")
//      .formParam("freeTextAddress", "")
//      .check(status.is(200)))
//


}
