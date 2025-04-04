package guru.qa.niffler.test.web;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.page.LoginPage;
import guru.qa.niffler.page.MainPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class LoginTest {

    private final static String LOGIN_URL = Config.getInstanceForLocale().loginPageUrl();

    @AfterEach
    void close() {
        Selenide.closeWebDriver();
    }

    @Test
    void mainPageShouldBeDisplayAfterSuccessLogin() {
        Selenide.open(LOGIN_URL, LoginPage.class)
                .doLogin("test", "test");

        new MainPage().checkThatPageLoaded();
    }

    @Test
    void userShouldStayOnLoginPageAfterLoginWithBadCredentials() {
        Selenide.open(LOGIN_URL, LoginPage.class)
                .doLogin("ttt", "ttt");

        new LoginPage().checkThatPageLoaded();
    }

    @Test
    void shouldNotLoginWithInvalidPassword() {
        Selenide.open(LOGIN_URL, LoginPage.class)
                .doLogin("test", "test1");

        new LoginPage().checkInvalidLoginMessage();
    }

    @Test
    void shouldNotLoginWithInvalidUsername() {
        Selenide.open(LOGIN_URL, LoginPage.class)
                .doLogin("test1", "test");

        new LoginPage().checkInvalidLoginMessage();
    }
}
