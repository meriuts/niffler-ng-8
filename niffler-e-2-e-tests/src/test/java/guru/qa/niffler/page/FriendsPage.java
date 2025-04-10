package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;

import java.util.List;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.CollectionCondition.textsInAnyOrder;
import static com.codeborne.selenide.Selenide.$;

public class FriendsPage {

    private final SelenideElement peopleTab = $("a[href='/people/friends']");
    private final SelenideElement allTab = $("a[href='/people/all']");
    private final SelenideElement requestsTable = $("#requests");
    private final SelenideElement friendsTable = $("#friends");

    public FriendsPage checkThatUserHasFriends(List<String> expectedUsernames) {
        friendsTable.$$("tr").shouldHave(textsInAnyOrder(expectedUsernames));
        return this;
    }

    public FriendsPage checkThatFriendsNotExist() {
        friendsTable.$$("tr").shouldHave(size(0));
        return this;
    }

    public FriendsPage checkThatUserHasIncomeFriend(List<String> expectedUsernames) {
        requestsTable.$$("tr").shouldHave(textsInAnyOrder(expectedUsernames));
        return this;
    }

}
