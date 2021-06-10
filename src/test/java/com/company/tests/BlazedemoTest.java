package com.company.tests;

import com.company.base.BaseTest;
import com.company.pages.blazedemoPages.BlazedemoPage;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BlazedemoTest extends BaseTest {

    private static Logger log = Logger.getLogger("BlazedemoTest");

    @Test(priority = 1)
    public void searchForFlightsForGivenCities(){
        BlazedemoPage blazedemoPage = new BlazedemoPage(driver);
        blazedemoPage.getFlightsForGivenCities("Paris","New York");
        Assert.assertTrue(blazedemoPage.verifySearchedFlightsAvailable("Paris","New York"));
    }

    @Test(priority = 2,dependsOnMethods = {"com.company.tests.BlazedemoTest.searchForFlightsForGivenCities"})
    public void selectFlightFromAvailableForGivenCities(){
        BlazedemoPage blazedemoPage = new BlazedemoPage(driver);
        int totalFlights= blazedemoPage.getTotalFlightsAvailableToSelect();
        System.out.println("Flight Row Number To Select which should be between totalFlights i.e between 1 to "+totalFlights+" and enter it as a desiredFlightRow variable below");
        int desiredFlightRow =1;
        blazedemoPage.selectFlightFromSearchedResults(desiredFlightRow);
        Assert.assertTrue(blazedemoPage.verifyFlightPurchaseIsDisplayed());
    }

    @Test(priority = 3,dependsOnMethods = {"com.company.tests.BlazedemoTest.searchForFlightsForGivenCities","com.company.tests.BlazedemoTest.selectFlightFromAvailableForGivenCities"})
    public void bookFlightByEnteringPassengerDetails(){
        BlazedemoPage blazedemoPage = new BlazedemoPage(driver);
        blazedemoPage.bookFlightByEnteringPassengerInfo("Monica Parera","45 FC Road NewYork","Albany","New York","3601000","Visa","4111 2345 5432 6789","12","2022","Monica Parera","Yes");
    }

    @Test(priority = 4,dependsOnMethods = {"com.company.tests.BlazedemoTest.searchForFlightsForGivenCities","com.company.tests.BlazedemoTest.selectFlightFromAvailableForGivenCities","com.company.tests.BlazedemoTest.bookFlightByEnteringPassengerDetails"})
    public void verifyFlightBookingIdIsConfirmed(){
        BlazedemoPage blazedemoPage = new BlazedemoPage(driver);
        Assert.assertTrue(blazedemoPage.verifyFlightBookingIdGenerated());
    }
}
