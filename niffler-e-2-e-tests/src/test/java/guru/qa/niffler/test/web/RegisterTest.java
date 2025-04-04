package guru.qa.niffler.test.web;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.page.LoginPage;
import guru.qa.niffler.page.RegisterPage;
import guru.qa.niffler.page.SuccessRegisterPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegisterTest {
    private final static String LOGIN_URL = Config.getInstanceForLocale().loginPageUrl();
    private final static String REGISTER_URL = Config.getInstanceForLocale().registePagerUrl();

    @AfterEach
    void close() {
        Selenide.closeWebDriver();
    }

    @Test
    void shouldRegisterNewUser() {
        String username = UUID.randomUUID().toString();

        Selenide.open(REGISTER_URL, RegisterPage.class)
                .doRegister(username, "password");

        new SuccessRegisterPage().checkSuccessMessage();
    }

    @Test
    void shouldNavigateToRegisterFromLogin() {
        Selenide.open(LOGIN_URL, LoginPage.class)
                .navigateToRegister()
                .checkThatPageLoaded();
    }

    @Test
    void shouldNotRegisterUserWithExistingUsername() {
        String username = "test";

        Selenide.open(REGISTER_URL, RegisterPage.class)
                .doRegister(username, "test");

        assertEquals("Username `" + username + "` already exists", new RegisterPage().getUsernameErrorMessage());
    }

    @Test
    void shouldShowErrorIfPasswordAndConfirmPasswordAreNotEqual() {
        Selenide.open(REGISTER_URL, RegisterPage.class)
                .setUsername("test01")
                .setPassword("test01")
                .setPasswordSubmit("test02")
                .clickSubmit();

        assertEquals("Passwords should be equal", new RegisterPage().getPasswordErrorMessage());

    }
}
