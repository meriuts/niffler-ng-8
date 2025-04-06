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
  public String ghUrl() {
    return "https://api.github.com";
  }
}
