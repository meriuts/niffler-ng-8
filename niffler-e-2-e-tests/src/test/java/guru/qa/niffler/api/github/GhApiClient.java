package guru.qa.niffler.api.github;

import com.fasterxml.jackson.databind.JsonNode;
import guru.qa.niffler.api.ControllerClientGenerator;
import guru.qa.niffler.config.Config;
import lombok.SneakyThrows;

import java.util.Objects;

public class GhApiClient {

    private static final String GH_TOKEN_ENV = "GITHUB_TOKEN";

    private final GhApi ghApi = ControllerClientGenerator.createControllerClient(
            GhApi.class, Config.getInstanceForLocale().ghUrl());

    @SneakyThrows
    public String getIssueSate(String issueNumber) {
        JsonNode resp =  ghApi.getIssue(
    "Bearer " + System.getenv(GH_TOKEN_ENV),
                issueNumber
        ).execute().body();
        return Objects.requireNonNull(resp).get("state").asText();
    }
}
