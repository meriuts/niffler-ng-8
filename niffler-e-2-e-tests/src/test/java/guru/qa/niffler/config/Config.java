package guru.qa.niffler.config;

public interface Config {

  static Config getInstanceForLocale() {
    return LocalConfig.instance;
  }

  String loginPageUrl();

  String registerPagerUrl();

  String profilePageUrl();

  String friendPageUrl();

  String allPeoplePageUrl();

  String gatewayUrl();

  String spendServiceUrl();

  String userDataServiceUrl();

  String ghUrl();

  String authJdbcUrl();

  String userdataJdbcUrl();

  String spendJdbcUrl();

  String currencyJdbcUrl();

}
