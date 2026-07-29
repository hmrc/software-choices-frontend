/*
 * Copyright 2026 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package utils

import ch.qos.logback.classic.{Level, Logger => LogbackLogger}
import play.api.mvc.AnyContentAsEmpty
import play.api.test.FakeRequest
import uk.gov.hmrc.http.{HeaderNames, HttpResponse, SessionKeys}
import uk.gov.hmrc.play.bootstrap.tools.LogCapturing

class LoggingUtilSpec extends TestUtils with LogCapturing {

  object TestLoggingUtil extends LoggingUtil

  private val testLogger: LogbackLogger = TestLoggingUtil.logger.asInstanceOf[LogbackLogger]

  "trueClientIp" when {

    "the True-Client-IP header is present" should {

      "return it formatted with a trailing space" in {
        val request = FakeRequest().withHeaders(HeaderNames.trueClientIp -> "127.0.0.1")
        TestLoggingUtil.trueClientIp(request) shouldBe Some("trueClientIp: 127.0.0.1 ")
      }
    }

    "the True-Client-IP header is absent" should {

      "return None" in {
        TestLoggingUtil.trueClientIp(FakeRequest()) shouldBe None
      }
    }
  }

  "sessionId" when {

    "the sessionId is present in the session" should {

      "return it formatted with a trailing space" in {
        val request = FakeRequest().withSession(SessionKeys.sessionId -> "session-123")
        TestLoggingUtil.sessionId(request) shouldBe Some("sessionId: session-123 ")
      }
    }

    "the sessionId is absent from the session" should {

      "return None" in {
        TestLoggingUtil.sessionId(FakeRequest()) shouldBe None
      }
    }
  }

  "identifiers" when {

    "both the True-Client-IP header and sessionId are present" should {

      "return them concatenated" in {
        val request = FakeRequest()
          .withHeaders(HeaderNames.trueClientIp -> "127.0.0.1")
          .withSession(SessionKeys.sessionId -> "session-123")
        TestLoggingUtil.identifiers(request) shouldBe "trueClientIp: 127.0.0.1 sessionId: session-123 "
      }
    }

    "neither the True-Client-IP header nor sessionId are present" should {

      "return an empty string" in {
        TestLoggingUtil.identifiers(FakeRequest()) shouldBe ""
      }
    }
  }

  "trueClientIpFromHttpResponse" when {

    "the True-Client-IP header is present" should {

      "return it formatted" in {
        val response = HttpResponse(status = 200, headers = Map(HeaderNames.trueClientIp -> Seq("127.0.0.1")))
        TestLoggingUtil.trueClientIpFromHttpResponse(response) shouldBe Some("trueClientIp: List(127.0.0.1)")
      }
    }

    "the True-Client-IP header is absent" should {

      "return None" in {
        TestLoggingUtil.trueClientIpFromHttpResponse(HttpResponse(status = 200)) shouldBe None
      }
    }
  }

  "sessionIdFromHttpResponse" when {

    "the X-Session-ID header is present" should {

      "return it formatted" in {
        val response = HttpResponse(status = 200, headers = Map(HeaderNames.xSessionId -> Seq("session-123")))
        TestLoggingUtil.sessionIdFromHttpResponse(response) shouldBe Some("sessionId: List(session-123)")
      }
    }

    "the X-Session-ID header is absent" should {

      "return None" in {
        TestLoggingUtil.sessionIdFromHttpResponse(HttpResponse(status = 200)) shouldBe None
      }
    }
  }

  "identifiersFromHttpResponse" when {

    "both the True-Client-IP and X-Session-ID headers are present" should {

      "return them concatenated" in {
        val response = HttpResponse(
          status  = 200,
          headers = Map(
            HeaderNames.trueClientIp -> Seq("127.0.0.1"),
            HeaderNames.xSessionId   -> Seq("session-123")
          )
        )
        TestLoggingUtil.identifiersFromHttpResponse(response) shouldBe "trueClientIp: List(127.0.0.1)sessionId: List(session-123)"
      }
    }

    "neither header is present" should {

      "return an empty string" in {
        TestLoggingUtil.identifiersFromHttpResponse(HttpResponse(status = 200)) shouldBe ""
      }
    }
  }

  "infoLog" should {

    "log the message with identifiers appended at INFO level" in {
      implicit val request: FakeRequest[AnyContentAsEmpty.type] = FakeRequest().withHeaders(HeaderNames.trueClientIp -> "127.0.0.1")

      withCaptureOfLoggingFrom(testLogger) { logEvents =>
        TestLoggingUtil.infoLog("a message")
        logEvents.size shouldBe 1
        logEvents.head.getMessage shouldBe "a message (trueClientIp: 127.0.0.1 )"
        logEvents.head.getLevel shouldBe Level.INFO
      }
    }
  }

  "warnLog" should {

    "log the message with identifiers appended at WARN level" in {
      implicit val request: FakeRequest[AnyContentAsEmpty.type] = FakeRequest().withHeaders(HeaderNames.trueClientIp -> "127.0.0.1")

      withCaptureOfLoggingFrom(testLogger) { logEvents =>
        TestLoggingUtil.warnLog("a warning")
        logEvents.size shouldBe 1
        logEvents.head.getMessage shouldBe "a warning (trueClientIp: 127.0.0.1 )"
        logEvents.head.getLevel shouldBe Level.WARN
      }
    }

    "log the message, identifiers and throwable at WARN level" in {
      implicit val request: FakeRequest[AnyContentAsEmpty.type] = FakeRequest().withHeaders(HeaderNames.trueClientIp -> "127.0.0.1")
      val throwable = new RuntimeException("something went wrong")

      withCaptureOfLoggingFrom(testLogger) { logEvents =>
        TestLoggingUtil.warnLog("a warning", throwable)
        logEvents.size shouldBe 1
        logEvents.head.getMessage shouldBe "a warning (trueClientIp: 127.0.0.1 )"
        logEvents.head.getLevel shouldBe Level.WARN
        logEvents.head.getThrowableProxy.getMessage shouldBe "something went wrong"
      }
    }
  }

  "errorLog" should {

    "log the message with identifiers appended at ERROR level" in {
      implicit val request: FakeRequest[AnyContentAsEmpty.type] = FakeRequest().withHeaders(HeaderNames.trueClientIp -> "127.0.0.1")

      withCaptureOfLoggingFrom(testLogger) { logEvents =>
        TestLoggingUtil.errorLog("an error")
        logEvents.size shouldBe 1
        logEvents.head.getMessage shouldBe "an error (trueClientIp: 127.0.0.1 )"
        logEvents.head.getLevel shouldBe Level.ERROR
      }
    }

    "log the message, identifiers and throwable at ERROR level" in {
      implicit val request: FakeRequest[AnyContentAsEmpty.type] = FakeRequest().withHeaders(HeaderNames.trueClientIp -> "127.0.0.1")
      val throwable = new RuntimeException("something went wrong")

      withCaptureOfLoggingFrom(testLogger) { logEvents =>
        TestLoggingUtil.errorLog("an error", throwable)
        logEvents.size shouldBe 1
        logEvents.head.getMessage shouldBe "an error (trueClientIp: 127.0.0.1 )"
        logEvents.head.getLevel shouldBe Level.ERROR
        logEvents.head.getThrowableProxy.getMessage shouldBe "something went wrong"
      }
    }
  }
}
