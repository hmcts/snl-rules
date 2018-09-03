package simulations.probate

import checks.CsrfCheck
import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._


class probateLogin {

//  val chain_0 = exec(http("PROBATE_010_HOME")
//    .get("/")
//    // .check(CurrentPageCheck.save)
//    .check(CsrfCheck.save)
//    .check(css(".form-group>input[name='client_id']", "value").saveAs("clientId"))
//    .check(css(".form-group>input[name='state']", "value").saveAs("state"))
//    .check(css(".form-group>input[name='redirect_uri']", "value").saveAs("redirectUri"))
//    .check(css(".form-group>input[name='continue']", "value").saveAs("continue"))
//    .check(regex("Email address")))
//
//    // replace
//
//    .feed(userFeeder)
//
//
//    .pause(20)
//
//    .exec(http("PROBATE_020_LOGIN")
//      .post(uri1 + "/login?response_type=code&state=${state}&client_id=probate&redirect_uri=https%3A%2F%2Fprobate-frontend-saat.service.core-compute-saat.internal%2Foauth2%2Fcallback")
//      .headers(headers_16)
//      .formParam("username", "${email}")
//      .formParam("password", "${password}")
//      .formParam("continue", "${continue}")
//      .formParam("state", "${state}")
//      .formParam("upliftToken", "")
//      .formParam("response_type", "code")
//      .formParam("_csrf", "${csrf}")
//      .formParam("scope", "")
//      .formParam("redirect_uri", "${redirectUri}")
//      .formParam("selfRegistrationEnabled","true")
//      .formParam("client_id", "probate")
//      //    .check(regex("Apply for Probate"))
//    )


}
