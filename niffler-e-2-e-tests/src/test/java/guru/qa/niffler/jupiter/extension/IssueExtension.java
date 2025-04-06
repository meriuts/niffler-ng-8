package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.api.github.GhApiClient;
import guru.qa.niffler.jupiter.annotation.DisabledByIssue;
import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.support.AnnotationSupport;
import org.junit.platform.commons.support.SearchOption;

public class IssueExtension implements ExecutionCondition {

    private final GhApiClient ghApiClient = new GhApiClient();

    @Override
    public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
        return AnnotationSupport.findAnnotation(
                context.getRequiredTestMethod(),
                DisabledByIssue.class)
                .or(() -> AnnotationSupport.findAnnotation(
                        context.getRequiredTestClass(), DisabledByIssue.class, SearchOption.INCLUDE_ENCLOSING_CLASSES))
                .map(issue -> "open".equals(ghApiClient.getIssueSate(issue.value()))
                        ? ConditionEvaluationResult.disabled("Exist issue " + issue.value())
                        : ConditionEvaluationResult.enabled("issue closed")
                ).orElseGet(() -> ConditionEvaluationResult.enabled("not found @DisabledByIssue"));
    }
}
