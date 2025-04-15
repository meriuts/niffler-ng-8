package guru.qa.niffler.config;

enum LocalConfig implements Config {
  instance;

  @Override
  public String loginPageUrl() {
    return "http://127.0.0.1:3000/login";
  }

  @Override
  public String profilePageUrl() {
    return "http://127.0.0.1:3000/profile";
  }

  @Override
  public String registerPagerUrl() {
    return "http://127.0.0.1:3000/register";
  }

  @Override
  public String friendPageUrl() {
    return "http://127.0.0.1:3000/people/friends";
  }

  @Override
  public String allPeoplePageUrl() {
    return "http://127.0.0.1:3000/people/all";
  }

  @Override
  public String gatewayUrl() {
    return "http://127.0.0.1:8093/";
  }

  @Override
  public String spendServiceUrl() {
    return "http://127.0.0.1:8093";
  }

  @Override
  public String userDataServiceUrl() {
    return "http://127.0.0.1:8089";
  }

  @Override
  public String ghUrl() {
    return "https://api.github.com";
  }

  @Override
  public String authJdbcUrl() {
    return "jdbc:postgresql://127.0.0.1:5432/niffler-auth";
  }

  @Override
  public String userdataJdbcUrl() {
    return "jdbc:postgresql://127.0.0.1:5432/niffler-userdata";
  }

  @Override
  public String spendJdbcUrl() {
    return "jdbc:postgresql://127.0.0.1:5432/niffler-spend";
  }

  @Override
  public String currencyJdbcUrl() {
    return "jdbc:postgresql://127.0.0.1:5432/niffler-currency";
  }

}
