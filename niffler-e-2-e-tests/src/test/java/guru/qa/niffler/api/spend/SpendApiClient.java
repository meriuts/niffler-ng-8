package guru.qa.niffler.api.spend;

import guru.qa.niffler.api.ControllerClientGenerator;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.SpendJson;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpendApiClient {

  private final SpendApi spendApi = ControllerClientGenerator.createControllerClient(
          SpendApi.class, Config.getInstanceForLocale().spendServiceUrl());

  public SpendJson addSpend(SpendJson spend) {
    Call<SpendJson> spendCall = spendApi.addSpend(spend);
    final Response<SpendJson> response;
    try {
      response = spendCall.execute();
    } catch (IOException ex) {
      throw new AssertionError("API request failed: " + ex.getMessage(), ex);
    }
    assertEquals(200, response.code());
    return response.body();
  }

  public SpendJson editSpend(SpendJson editSpend) {
    Call<SpendJson> spendCall = spendApi.editSpend(editSpend);
    final Response<SpendJson> response;
    try {
      response = spendCall.execute();
    } catch (IOException ex) {
      throw new AssertionError("API request failed: " + ex.getMessage(), ex);
    }
    assertEquals(200, response.code());
    return response.body();
  }

  public SpendJson getSpend(String id) {
    final Response<SpendJson> response;
    try {
      response = spendApi.getSpend(id)
              .execute();
    } catch (IOException ex) {
      throw new AssertionError("API request failed: " + ex.getMessage(), ex);
    }
    assertEquals(200, response.code());
    return response.body();
  }

  public List<SpendJson> allSpends(String username,
                                   CurrencyValues currency,
                                   String from,
                                   String to) {
    final Response<List<SpendJson>> response;
    try {
      response = spendApi.getAllSpends(username, currency, from, to)
              .execute();
    } catch (IOException ex) {
      throw new AssertionError("API request failed: " + ex.getMessage(), ex);
    }
    assertEquals(200, response.code());
    return response.body();
  }

  public void removeSpends(String username, List<String> ids) {
    final Response<Void> response;
    try {
      response = spendApi.removeSpends(username, ids)
              .execute();
    } catch (IOException e) {
      throw new AssertionError(e);
    }
    assertEquals(200, response.code());
  }

  public CategoryJson addCategory(CategoryJson categoryJson) {
    Call<CategoryJson> categoryCall = spendApi.addCategory(categoryJson);
    final Response<CategoryJson> response;
    try {
      response = categoryCall.execute();
    } catch (IOException ex) {
      throw new AssertionError("API request failed: " + ex.getMessage(), ex);
    }
    assertEquals(200, response.code());
    return response.body();
  }

  public CategoryJson updateCategory(CategoryJson categoryJson) {
    Call<CategoryJson> categoryCall = spendApi.updateCategory(categoryJson);
    final Response<CategoryJson> response;
    try {
      response = categoryCall.execute();
    } catch (IOException ex) {
      throw new AssertionError("API request failed: " + ex.getMessage(), ex);
    }
    assertEquals(200, response.code());
    return response.body();
  }

  public List<CategoryJson> allCategory(String username) {
    final Response<List<CategoryJson>> response;
    try {
      response = spendApi.getAllCategories(username)
              .execute();
    } catch (IOException e) {
      throw new AssertionError(e);
    }
    assertEquals(200, response.code());
    return response.body();
  }

}
