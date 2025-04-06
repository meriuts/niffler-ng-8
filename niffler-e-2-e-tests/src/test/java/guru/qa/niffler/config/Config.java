package guru.qa.niffler.config;

public interface Config {

  static Config getInstanceForLocale() {
    return LocalConfig.instance;
  }

  String loginPageUrl();

  String registePagerUrl();

  String profilePageUrl();

  String gatewayUrl();

  String spendServiceUrl();
}
