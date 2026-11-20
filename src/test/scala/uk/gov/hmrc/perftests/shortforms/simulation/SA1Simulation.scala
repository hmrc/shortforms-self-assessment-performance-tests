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

package uk.gov.hmrc.perftests.shortforms.simulation

import io.gatling.core.scenario.Simulation
import uk.gov.hmrc.performance.simulation.PerformanceTestRunner
import uk.gov.hmrc.perftests.shortforms.request.AuthRequest._

class SA1Simulation extends Simulation with PerformanceTestRunner {

  setup("shortforms-sa1", "Shortforms SA1")
    .withRequests(
      getAuthPage,
      postAuthPage,
      getStartPage,
      clickStart,
      getFirstAndLastNamePage,
//      postFirstAndLastNamePage("John", "Doe"),
//      getAddPreviousLastNamePage,
//      postAddPreviousLastNamePage("false"),
//      getDateOfBirthPage,
//      postDateOfBirthPage,
//      getContactDetailsPage,
//      postContactDetailsPage("user.test@digital.hmrc.co.uk", "+44123123123"),
//      getAddressLookupCountryPicker,
//      postAddressLookupCountryPicker("Ireland"),
//      getManuallyEnterAddressPage,
//      postManuallyEnterAddressPage(
//        "25 Oakley Road",
//        "Flat 69",
//        "",
//        "City of Test",
//        "SW22 42F",
//        "IE"
//      ),
//      getReviewAddress,
//      getAddressDateMovedPage,
//      postAddressDateMovedPage,
//      getAddNinoPage,
//      postAddNinoPage("false"),
//      getNoNinoReasonPage,
//      postNoNinoReasonPage("Reason"),
//      getTaxReturnReasonsPage,
//      postTaxReturnPage(),
//      getOtherTaxReasonPage,
//      postOtherTaxReasonPage("Reason"),
//      postOtherTaxReasonDatePage,
//      getCheckAnswersPage,
//      getChangeFirstName,
//      postChangeFirstName("John Updated"),
//      getChangeLastName,
//      postChangeLastName("Doe Updated"),
//      getCheckAnswersPage,
//      getChangeAddPreviousLastName,
//      postChangeAddPreviousLastName("true"),
//      getChangePreviousLastName,
//      postChangePreviousLastName("Previous Last Name"),
//      getCheckAnswersPage,
//      getChangeDateOfBirth,
//      postChangeDateOfBirth(""),
//      getCheckAnswersPage,
//      getChangeEmailAddress,
//      postChangeEmailAddress("updated.email@digital.hmrc.co.uk")
//        getCheckAnswersPage,
//      getChangePhoneNumber,
//      postChangePhoneNumber("+44 2333 32222")
//        getCheckAnswersPage,
//      getChangeAddNino,
//      postChangeAddNino("true"),
//      getNationalInsturanceNumberPage,
//      postNationalInsturanceNumberPage("NM772208B"),
//      getCheckAnswersPage,
//      getChangeTaxReturnReasonsPage,
//      postChangeTaxReturnReasonsPage("select all"),
//      getDateUntaxedIncomeDirectorPage,
//      postDateUntaxedIncomeDirectorPage(),
//      getDateLandPropertyPage,
//      postDateLandProperty(),
//      getDateTaxableForeignIncomePage,
//      postDateTaxableForeignIncomePage(),
//      getDateTrustSettlementPage,
//      postDateTrustSettlementPage(),
//      getDateAnnualIncomePage,
//      postDateAnnualIncomePage(),
//      getDateUntaxedIncomePayePage,
//      postDateUntaxedIncomePayePage(),
//      getDateHighIncomeChildBenefitPage,
//      postDateHighIncomeChildBenefit(),
//      getDateCapitalGainsTaxPage,
//      postDateCapitalGainsTaxPage()
//      getCheckAnswersPage
    )

  runSimulation()
}