package guru.qa.niffler.test.web;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.jupiter.extension.BrowserExtension;
import guru.qa.niffler.page.LoginPage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.UUID;

@ExtendWith(BrowserExtension.class)
public class RegisterTest {

    private final static String LOGIN_URL = Config.getInstanceForLocale().loginPageUrl();

    @Test
    void shouldRegisterNewUser() {
        String username = UUID.randomUUID().toString();

        Selenide.open(LOGIN_URL, LoginPage.class)
                .navigateToRegister()
                .checkThatPageLoaded()
                .doRegister(username, "password")
                .checkSuccessMessage();
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

        Selenide.open(LOGIN_URL, LoginPage.class)
                .navigateToRegister()
                .checkThatPageLoaded()
                .doInvalidRegister(username, "test")
                .checkRegisterError("Username `" + username + "` already exists");
    }

    @Test
    void shouldShowErrorIfPasswordAndConfirmPasswordAreNotEqual() {
        Selenide.open(LOGIN_URL, LoginPage.class)
                .navigateToRegister()
                .setUsername("test01")
                .setPassword("test01")
                .setPasswordSubmit("test02")
                .clickSubmit()
                .checkRegisterError("Passwords should be equal");
    }
}
