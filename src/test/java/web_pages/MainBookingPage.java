package web_pages;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utility.MyProperty;
import web_driver.Config;
import web_driver.Driver;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

public class MainBookingPage {
    public static final Logger LOGGER = LogManager.getLogger(MainBookingPage.class);
    WebDriver driver;
    private String propPath = "src/test/resources/booking/mail.properties";
    private Properties properties = MyProperty.getProperties(propPath);

    @FindBy(xpath = "//a[contains(@href, 'https://account.booking.com/auth')]")
    private WebElement signIn;

    @FindBy(xpath = "//input[@id='login_name_register']")
    private WebElement login;

    @FindBy(xpath = "//form[contains(@class, 'register')]//button[@type='submit']")
    private WebElement confirmEmail;

    @FindBy(xpath = "//input[@id='password']")
    private WebElement password;

    @FindBy(xpath = "//input[@id='confirmed_password']")
    private WebElement cofirmPSW;

    @FindBy(xpath = "//form[contains(@class, 'register')]//button[@type='submit']")
    private WebElement submit;

    @FindBy(xpath = "//button[@title='Close']")
    private WebElement closeWelcom;

    @FindBy(xpath = "//a[@data-command='show-profile-menu']")
    private WebElement yourAccount;

    @FindBy(xpath = "//div[@id='profile-menu']/div") //div[@id='profile-menu']/div/a[contains(@href, 'https://secure.booking.com/mydashboard')]
    private WebElement dashboard;

    @FindBy(xpath = "//a[@data-trackname='Settings']")
    private WebElement settings;

    @FindBy(xpath = "//a[contains(@class,'email-resend')]")
    private WebElement emailResend;

    @FindBy(xpath = "//li[@id='current_account']/a")
    private WebElement loginToCurrentAcc;

    @FindBy(xpath = "//input[@id='username']")
    private WebElement userName;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement submitsForLogin;

    @FindBy(css = ".c-autocomplete__input")
    private WebElement city;

    @FindBy(xpath = "//div[contains(@class, 'db_panel ')]/a//span[@class='list_nr']")
    private WebElement myList;

    public MainBookingPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    public void createAccount() throws InterruptedException {
        LOGGER.debug("Create account on ");
        driver.navigate().to("https://booking.com/");
        Thread.sleep(2000);
        LOGGER.debug(signIn);
        signIn.click();
        Thread.sleep(2000);
        LOGGER.debug(login);
        login.sendKeys(MyProperty.getProperties(propPath).getProperty("TRASHMAIL"));
        LOGGER.debug(confirmEmail);
        confirmEmail.click();
        Thread.sleep(2000);
        LOGGER.debug(password);
        password.sendKeys(MyProperty.getProperties(propPath).getProperty("TRASHMAIL_PSW"));
        LOGGER.debug(cofirmPSW);
        cofirmPSW.sendKeys(MyProperty.getProperties(propPath).getProperty("TRASHMAIL_PSW"));
        LOGGER.debug(submit);
        submit.click();
    }

    public void goToDashboard() throws InterruptedException {
        LOGGER.debug("Go To Dashboard");
        LOGGER.debug(yourAccount);
        yourAccount.click();
        LOGGER.debug(dashboard);
        dashboard.click();
        Thread.sleep(2000);

    }

    public boolean verifyAccActivation() throws InterruptedException {
        LOGGER.debug("Verify Account Activation");
        Thread.sleep(3000);
        LOGGER.debug(closeWelcom);
        closeWelcom.click();
        LOGGER.debug(yourAccount);
        yourAccount.click();
        LOGGER.debug(dashboard);
        dashboard.click();
        LOGGER.debug(settings);
        settings.click();
        Thread.sleep(2000);
        LOGGER.debug(emailResend);
        if (emailResend.isDisplayed()){
            return true;
        } else {
            return false;
        }
    }

    public void login() throws InterruptedException {
        LOGGER.debug("login to");
        driver.navigate().to("https://booking.com/");
        Thread.sleep(2000);
        LOGGER.debug(loginToCurrentAcc);
        loginToCurrentAcc.click();
        LOGGER.debug(userName);
        userName.sendKeys(MyProperty.getProperties(propPath).getProperty("REAL_MAIL"));
        LOGGER.debug(submitsForLogin);
        submitsForLogin.click();
        Thread.sleep(2000);
        LOGGER.debug(password);
        password.sendKeys(MyProperty.getProperties(propPath).getProperty("BOOKING_PSW"));
        LOGGER.debug(submitsForLogin);
        submitsForLogin.click();
    }

    public void enterCity(String city) {
        LOGGER.debug("Enter City");
        driver.findElement(By.cssSelector(".c-autocomplete__input")).sendKeys(city);
    }

    public void enterDate(int dayBeforeStartDate, int dayOfStay) {
        LOGGER.debug("Enter Date");
        driver.findElement(By.xpath("//*[@id='frm']//descendant::div[@class='xp__dates xp__group']")).click();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, dayBeforeStartDate);
        Date plusDayBeforeStartDate = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, dayOfStay);
        Date plusDayOfStayDate = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String plusDayOfStay = dateFormat.format(plusDayBeforeStartDate);
        String datePlusStay = dateFormat.format(plusDayOfStayDate);
        driver.findElement(By.xpath(String.format("//*[contains(@data-date,'%s')]", plusDayOfStay))).click();
        driver.findElement(By.xpath(String.format("//*[contains(@data-date,'%s')]", datePlusStay))).click();
    }

    public void enterGuestData(int adults, int children, int rooms) {
        LOGGER.debug("Enter Guest Data");
        WebElement guestField = driver.findElement(By.xpath("//*[contains(@for, 'xp__guests__input')]"));
        guestField.click();
        WebElement adultPlus = driver.findElement(By.xpath("//*[contains(@aria-describedby, 'group_adults_desc')][2]"));
        WebElement childrenPlus = driver.findElement(By.xpath("//*[contains(@aria-describedby, 'group_children_desc')][2]"));
        WebElement roomPlus = driver.findElement(By.xpath("//*[contains(@aria-describedby, 'no_rooms_desc')][2]"));
        if (adults > 2) {
            for (int i = 3; i <= adults; i++) {
                adultPlus.click();
            }
        }
        if (children > 0) {
            for (int i = 1; i <= children; i++) {
                childrenPlus.click();
            }
        }
        if (rooms > 1) {
            for (int i = 2; i <= rooms; i++) {
                roomPlus.click();
            }
        }
    }

    public void search() throws InterruptedException {
        LOGGER.debug("Search trip with enter city, dates, adults, rooms, children data");
        driver.findElement(By.xpath("//*[contains(@class, 'sb-searchbox__button')]")).click();
        Thread.sleep(2000);
    }

    public void cleanCityData(){
        LOGGER.debug("Clear City Data");
        city.clear();
    }

    public int retrieveNumberOfTrips(){
        LOGGER.debug("Retrieve Number Of Trips");
        LOGGER.debug(myList);
        String trips = myList.getText();
        String str = trips.replaceAll("[^0-9]+","");
        int tripsInt = Integer.parseInt(str);
        return tripsInt;
    }

}
