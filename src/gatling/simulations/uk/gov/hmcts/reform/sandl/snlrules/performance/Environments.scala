package uk.gov.hmcts.reform.sandl.snlrules.performance

object Environments {

  val baseUrl : String = scala.util.Properties.envOrElse("TEST_URL","http://localhost:8091")

}
