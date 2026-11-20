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

import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.concurrent.ThreadLocalRandom

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
          "redirectionUrl" -> shortformsSA1StartUrl,
          "credentialStrength" -> "strong",
          "confidenceLevel" -> "250",
          "affinityGroup" -> "Individual",
          "authorityId" -> "",
          "csrfToken" -> "#{csrfToken}",
          "nino" -> "NM772208B",
        )
      )
      .check(status.is(303))
      .check(header("Location").is(shortformsSA1StartUrl))

  private def randomValidDate(): (String, String, String) = {
    val startDate = LocalDate.of(1906, 1, 3)
    val endDate = LocalDate.now().minusYears(15).minusMonths(9)

    val daysBetween = ChronoUnit.DAYS.between(startDate, endDate)

    val randomDays =
      ThreadLocalRandom.current().nextLong(daysBetween + 1)

    val randomDate = startDate.plusDays(randomDays)

    (
      randomDate.getDayOfMonth.toString,
      randomDate.getMonthValue.toString,
      randomDate.getYear.toString
    )
  }

  val getSA1StartPage: HttpRequestBuilder =
    http("[GET] SA1 Start Page")
      .get(shortformsSA1StartUrl + "/start")
      .check(status.is(200))
      .check(
        css("a.govuk-button--start", "href")
          .transform(_.split("/").last)
          .saveAs("journeyId")
      )

  val getFirstAndLastNamePage: HttpRequestBuilder =
    http("[GET] What Is Your Name Page")
      .get(shortformsSA1StartUrl + "/name/#{journeyId}")
      .check(status.is(200))
      .check(css("input[name=csrfToken]", "value").saveAs("csrfToken"))

  def postFirstAndLastNamePage(first: String, last: String): HttpRequestBuilder =
    http("[POST] What Is Your Name Page")
      .post(shortformsSA1StartUrl + "/name/#{journeyId}")
      .formParam("firstName", first)
      .formParam("lastName", last)
      .formParam("csrfToken", "#{csrfToken}")
      .check(status.is(303))

  val getPreviousLastNamePage: HttpRequestBuilder =
    http("[GET] Previous Last Name Page")
      .get(shortformsSA1StartUrl + "/previous-last-name/#{journeyId}")
      .check(status.is(200))
      .check(css("input[name=csrfToken]", "value").saveAs("csrfToken"))

//  val getSA1StartPage: HttpRequestBuilder =
//    http("[GET] SA1 Start Page")
//      .get(shortformsSA1StartUrl + "/start")
//      // your existing form parameters here
//      .check(status.is(200))
//      .check(
//        headerRegex("Location", """.*/name/([0-9a-fA-F-]{36}).*""").saveAs("journeyId")
//      )
//
//  val getFirstAndLastNamePage: HttpRequestBuilder =
//    http("[GET] What Is Your Name Page")
//      .get(shortformsSA1StartUrl + "/name/#{journeyId}")
//      .check(currentLocation.transform(url => { println("ACTUAL URL = " + url); url }))
//      .check(status.is(200))
//      .check(css("input[name=csrfToken]", "value").saveAs("csrfToken"))
//
//  def postFirstAndLastNamePage(first: String, last: String): HttpRequestBuilder =
//    http("[post] What Is Your Name Page")
//      .get(shortformsSA1StartUrl + "/name/#{journeyId}")
//      .check(currentLocation.transform(url => { println("ACTUAL URL = " + url); url }))
//      .formParam("firstName", first)
//      .formParam("lastName", last)
//      .formParam("csrfToken", "#{csrfToken}")
//      .check(status.is(303))

  val getAddPreviousLastNamePage: HttpRequestBuilder =
    http("[GET] Do You Have A Previous Last Name Page")
      .get(shortformsSA1StartUrl + "/previous-last-name/#{journeyId}")
      .check(currentLocation.transform(url => { println("ACTUAL URL = " + url); url }))
      .check(status.is(200))
      .check(css("input[name=csrfToken]", "value").saveAs("csrfToken"))

  def postAddPreviousLastNamePage(option: String): HttpRequestBuilder =
    http("[post] Do You Want To Add The Individual's Address Page")
      .get(shortformsSA1StartUrl + "/previous-last-name/#{journeyId}")
      .formParam("value", option)
      .formParam("csrfToken", "#{csrfToken}")
      .check(status.is(303))

  val getDateOfBirthPage: HttpRequestBuilder =
    http("[GET] What Is Your Date Of Birth Page")
      .get(shortformsSA1StartUrl + "/date-of-birth/#{journeyId}")
      .check(currentLocation.transform(url => { println("ACTUAL URL = " + url); url }))
      .check(status.is(200))
      .check(css("input[name=csrfToken]", "value").saveAs("csrfToken"))

  def postDateOfBirthPage: HttpRequestBuilder = {
    val (day, month, year) = randomValidDate()
    http("[post] Confirm nil return Page")
      .get(shortformsSA1StartUrl + "/date-of-birth/#{journeyId}")
      .formParam("dateOfBirth.day", day)
      .formParam("dateOfBirth.month", month)
      .formParam("dateOfBirth.year", year)
      .formParam("csrfToken", "#{csrfToken}")
      .check(status.is(303))
  }

  val getContactDetailsPage: HttpRequestBuilder =
    http("[GET] What Are Your Contact Details Page")
      .get("#{dynamicUrl}")
      .check(status.is(200))
      .check(css("input[name=csrfToken]", "value").saveAs("csrfToken"))

  def postContactDetailsPage(email: String, phone: String): HttpRequestBuilder =
    http("[post] What Are Your Contact Details Page")
      .post("#{dynamicUrl}")
      .formParam("emailAddress", email)
      .formParam("phoneNumber", phone)
      .formParam("csrfToken", "#{csrfToken}")
      .check(status.is(303))

  val getAddressLookupCountryPicker: HttpRequestBuilder =
    http("[GET] What is the country or territory of your home address Page")
      .get("#{dynamicUrl}")
      .check(status.is(200))
      .check(css("input[name=csrfToken]", "value").saveAs("csrfToken"))

  def postAddressLookupCountryPicker(country: String): HttpRequestBuilder =
    http("[post] What is the country or territory of your home address Page")
      .post("#{dynamicUrl}")
      .formParam("countryCode", country)
      .formParam("csrfToken", "#{csrfToken}")
      .check(status.is(303))

  val getManuallyEnterAddressPage: HttpRequestBuilder =
    http("[GET] What is your address page")
      .get("#{dynamicUrl}")
      .check(status.is(200))
      .check(css("input[name=csrfToken]", "value").saveAs("csrfToken"))

  def postManuallyEnterAddressPage(
                                    addressLine1: String,
                                    addressLine2: String,
                                    addressLine3: String,
                                    town: String,
                                    postalCode: String,
                                    country: String
                                  ): HttpRequestBuilder =
    http("[post] What is your address page")
      .post("#{dynamicUrl}")
      .formParam("line1", addressLine1)
      .formParam("line2", addressLine2)
      .formParam("line3", addressLine3)
      .formParam("town", town)
      .formParam("postcode", postalCode)
      .formParam("countryCode", country)
      .formParam("csrfToken", "#{csrfToken}")
      .check(status.is(303))

  val getReviewAddress: HttpRequestBuilder =
    http("[GET] Review your address Page")
      .get("#{dynamicUrl}")
      .check(status.is(200))
      .check(css("input[name=csrfToken]", "value").saveAs("csrfToken"))

  val postReviewAddress: HttpRequestBuilder =
    http("[post] Review your address Page")
      .get("#{dynamicUrl}")
      .disableFollowRedirect
      .formParam("csrfToken", "#{csrfToken}")
      .check(status.is(303))
      .check(headerRegex("Location", "[?&]id=([^&]+)").saveAs("id"))

  val getAddressDateMovedPage: HttpRequestBuilder =
    http("[GET] When did you move to your address page")
      .get("#{dynamicUrl}")
      .check(status.is(200))
      .check(css("input[name=csrfToken]", "value").saveAs("csrfToken"))

  def postAddressDateMovedPage: HttpRequestBuilder = {
    val (day, month, year) = randomValidDate()
    http("[post] When did you move to your address page")
      .post("#{dynamicUrl}")
      .formParam("moveAddressDate.day", day)
      .formParam("moveAddressDate.month", month)
      .formParam("moveAddressDate.year", year)
      .formParam("csrfToken", "#{csrfToken}")
      .check(status.is(303))
  }

  val getAddNinoPage: HttpRequestBuilder =
    http("[GET] Do you have a National Insurance number page")
      .get("#{dynamicUrl}")
      .check(status.is(200))
      .check(css("input[name=csrfToken]", "value").saveAs("csrfToken"))

  def postAddNinoPage(option: String): HttpRequestBuilder =
    http("[post] Do you have a National Insurance number page")
      .post("#{dynamicUrl}")
      .formParam("value", option)
      .formParam("csrfToken", "#{csrfToken}")
      .check(status.is(303))

  val getNoNinoReasonPage: HttpRequestBuilder =
    http("[GET] Why do you not have a National Insurance number page")
      .get("#{dynamicUrl}")
      .check(status.is(200))
      .check(css("input[name=csrfToken]", "value").saveAs("csrfToken"))
//
//  def postNoNinoReasonPage(reason: String): HttpRequestBuilder =
//    http("[post] What Is Your Name Page")
//      .post("#{dynamicUrl}")
//      .formParam("value", reason)
//      .formParam("csrfToken", "#{csrfToken}")
//      .check(status.is(303))
//      .check(header("Location").saveAs("taxReturnReasonsUrl"))

  val getTaxReturnReasonsPage: HttpRequestBuilder =
    http("[GET] Why do you need to complete a tax return page")
      .get("#{dynamicUrl}")
      .check(status.is(200))
      .check(css("input.govuk-checkboxes__input", "value").findAll.saveAs("checkboxValues"))
      .check(css("input[name=csrfToken]", "value").saveAs("csrfToken"))

  def postNoNinoReasonPage(reason: String): HttpRequestBuilder =
    http("[POST] Current Page")
      .post("#{dynamicUrl}")
      .formParam("value", reason)
      .formParam("csrfToken", "#{csrfToken}")
      .disableFollowRedirect
      .check(status.is(303))
      .check(header("Location").saveAs("nextUrl"))

  def postTaxReturnPage(): HttpRequestBuilder =
    http("[post] Why do you need to complete a tax return page")
      .post("#{dynamicUrl}")
      .formParam("csrfToken", "#{csrfToken}")
      .formParam("value[]", "8")
      .check(status.is(303))

  val getOtherTaxReasonPage: HttpRequestBuilder =
    http("[GET] Other reason page")
      .get("#{dynamicUrl}")
      .check(status.is(200))
      .check(css("input[name=csrfToken]", "value").saveAs("csrfToken"))

  def postOtherTaxReasonPage(reason: String): HttpRequestBuilder =
    http("[post] Other reason page")
      .post("#{dynamicUrl}")
      .formParam("reason", reason)
      .formParam("csrfToken", "#{csrfToken}")
      .check(status.is(303))

  def postOtherTaxReasonDatePage: HttpRequestBuilder = {
    val (day, month, year) = randomValidDate()
    http("[post] Other reason page")
      .post("#{dynamicUrl}")
      .formParam("reasonDate.day", day)
      .formParam("reasonDate.month", month)
      .formParam("reasonDate.year", year)
      .formParam("csrfToken", "#{csrfToken}")
      .check(status.is(303))
  }

  val getCheckAnswersPage: HttpRequestBuilder =
    http("[GET] Check answers page")
      .get("#{dynamicUrl}")
      .check(status.is(200))
      .check(css("input[name=csrfToken]", "value").saveAs("csrfToken"))






}