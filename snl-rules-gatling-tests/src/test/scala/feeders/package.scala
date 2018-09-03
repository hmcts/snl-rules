package object feeders {

  import com.typesafe.config._

  val conf = ConfigFactory.load()

  val random = new scala.util.Random

  def randomString(alphabet: String)(n: Int): String =
    Stream.continually(random.nextInt(alphabet.size)).map(alphabet).take(n).mkString

  def randomAlphanumericString(n: Int) =
    randomString("abcdefghijklmnopqrstuvwxyz0123456789")(n)

  def caseNumber(): String = randomAlphanumericString(8)

  def caseTitle(): String = randomAlphanumericString(10)

}