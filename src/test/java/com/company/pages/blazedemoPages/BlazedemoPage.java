package com.company.pages.blazedemoPages;

import com.company.pages.basePages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class BlazedemoPage extends BasePage {

    public BlazedemoPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//select[@name='fromPort']")
    private WebElement departureCityWEle;
    @FindBy(xpath = "//select[@name='toPort']")
    private WebElement destinationCityWEle;
    @FindBy(xpath = "//input[@value='Find Flights']")
    private WebElement findFlightsBtn;
    @FindBy(xpath = "//tbody/tr[1]/td[1]/input[1]")
    private WebElement flightsFirstRowWEle;
    @FindBy(xpath = "//tbody/tr")
    private List<WebElement> totalFlightsWEle;
    @FindBy(xpath = "//input[@value='Purchase Flight']")
    private WebElement purchaseFlightBtn;
    @FindBy(id = "inputName")
    private WebElement nameWEle;
    @FindBy(id = "address")
    private WebElement addressWEle;
    @FindBy(id = "city")
    private WebElement cityWEle;
    @FindBy(id = "state")
    private WebElement stateWEle;
    @FindBy(id = "zipCode")
    private WebElement zipCodeWEle;
    @FindBy(xpath = "//select[@id='cardType']")
    private WebElement cardTypeWEle;
    @FindBy(xpath = "//input[@id='creditCardNumber']")
    private WebElement cardNumberWEle;
    @FindBy(xpath = "//input[@id='creditCardMonth']")
    private WebElement cardMonthWEle;
    @FindBy(xpath = "//input[@id='creditCardYear']")
    private WebElement cardYearWEle;
    @FindBy(xpath = "//input[@id='nameOnCard']")
    private WebElement nameOnCardWEle;
    @FindBy(xpath = "//input[@id='rememberMe']")
    private WebElement rememberMeWEle;
    @FindBy(xpath = "//h1[contains(text(),'Thank you for your purchase today!')]")
    private WebElement flightPurchaseConfHeaderWEle;
    @FindBy(xpath = "//tbody/tr[1]/td[1]")
    private WebElement bookingIdLabelWEle;
    @FindBy(xpath = "//tbody/tr[1]/td[2]")
    private WebElement bookingIdWEle;

    public void getFlightsForGivenCities(String departureCity,String destinationCity){
        Select depCity = new Select(departureCityWEle);
        depCity.selectByValue(departureCity);
        Select desCity = new Select(destinationCityWEle);
        desCity.selectByValue(destinationCity);
        findFlightsBtn.click();
    }

    public boolean verifySearchedFlightsAvailable(String departureCity,String destinationCity){
        String flightsResultsHeader = "//h3[contains(text(),'Flights from "+departureCity+" to "+destinationCity+":')]";
        WebElement flightsResultsHeaderWEle = driver.findElement(By.xpath(flightsResultsHeader));
        if(flightsResultsHeaderWEle.isDisplayed() && flightsFirstRowWEle.isDisplayed()){
            return true;
        }else{
            return false;
        }
    }

    public void selectFlightFromSearchedResults(int desiredFlightRow){
        int totalFlightsCnt = getTotalFlightsAvailableToSelect();
        if(desiredFlightRow>=1 && desiredFlightRow<=totalFlightsCnt){
            String desiredFlight = "//tbody/tr["+desiredFlightRow+"]/td[1]/input[1]";
            WebElement desiredFlightWEle = driver.findElement(By.xpath(desiredFlight));
            desiredFlightWEle.click();
        }
    }

    public int getTotalFlightsAvailableToSelect(){
        return totalFlightsWEle.size();
    }

    public boolean verifyFlightPurchaseIsDisplayed(){
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        return purchaseFlightBtn.isDisplayed();
    }

    public void bookFlightByEnteringPassengerInfo(String pName,String pAddress,String pCity,String pState,String pZipCode,String pCardType,String pCardNumber,String pCardMonth,String pCardYear,String pNameONCard,String rememberMeOption){
        nameWEle.sendKeys(pName);
        addressWEle.sendKeys(pAddress);
        cityWEle.sendKeys(pCity);
        stateWEle.sendKeys(pState);
        zipCodeWEle.sendKeys(pZipCode);
        Select cardType = new Select(cardTypeWEle);
        cardType.selectByVisibleText(pCardType);
        cardNumberWEle.sendKeys(pCardNumber);
        cardNumberWEle.sendKeys(pCardNumber);
        cardMonthWEle.sendKeys(pCardMonth);
        cardYearWEle.sendKeys(pCardYear);
        nameOnCardWEle.sendKeys(pNameONCard);
        if(rememberMeOption.equals("Yes")){
            rememberMeWEle.click();
        }
        purchaseFlightBtn.click();
    }

    public boolean verifyFlightBookingIdGenerated(){
        boolean bookingIdFlag = false;
        if(bookingIdLabelWEle.isDisplayed()){
            String bookingID = bookingIdWEle.getText();
            if(bookingID!=null){
                System.out.println("Flight Booking Id is : "+bookingID+"");
                bookingIdFlag= true;
            }else{
                System.out.println("Flight Booking Id is not generated");
                bookingIdFlag= false;
            }
        }
        return bookingIdFlag;
    }
}