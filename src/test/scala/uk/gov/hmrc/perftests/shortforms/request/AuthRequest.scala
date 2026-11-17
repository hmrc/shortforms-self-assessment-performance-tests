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

package uk.gov.hmrc.perftests.shortforms.request

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder

object AuthRequest extends ShortformsPerformanceTestBase {

  val getAuthPage: HttpRequestBuilder =
    http("[GET] gg-sign-in")
      .get(baseUrlAuthLoginStub + "/auth-login-stub/gg-sign-in")
      .disableFollowRedirect
      .check(status.is(200))
      .check(
        css("input[name=csrfToken]", "value")
          .saveAs("csrfToken")
      )

  val postAuthPage: HttpRequestBuilder =
    http("[POST] gg-sign-in")
      .post(baseUrlAuthLoginStub + "/auth-login-stub/gg-sign-in")
      .disableFollowRedirect
      .formParamMap(
        Map(
          "redirectionUrl"     -> shortformsSA1StartUrl,
          "credentialStrength" -> "strong",
          "confidenceLevel"    -> "250",
          "affinityGroup"      -> "Individual",
          "authorityId"        -> "",
          "csrfToken"          -> "#{csrfToken}",
          "nino"                -> "NM772208B",
        )
      )
      .check(status.is(303))
      .check(header("Location").is(shortformsSA1StartUrl))

  val getSA1StartPage: HttpRequestBuilder =
    http("[GET] SA1 start page")
      .get(shortformsSA1StartUrl)
      .disableFollowRedirect
      .check(status.is(200))
}