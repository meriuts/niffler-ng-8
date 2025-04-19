package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.api.spend.SpendApiClient;
import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.service.SpendDbClient;
import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.support.AnnotationSupport;

public class CreateCategoryExtension implements
        BeforeEachCallback,
        ParameterResolver,
        AfterTestExecutionCallback {
    private static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(CreateCategoryExtension.class);
    private final SpendApiClient spendApiClient = new SpendApiClient();
    private final SpendDbClient spendDbClient = new SpendDbClient();


    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        AnnotationSupport.findAnnotation(context.getRequiredTestMethod(), User.class)
                .ifPresent(user -> {
                    CategoryJson categoryJson = new CategoryJson(
                            null,
                            user.username() + Math.random(),
                            user.username(),
                            user.categories()[0].archived()
                    );
                    CategoryJson createdCategory = spendDbClient.createCategory(categoryJson);
                  
                    context.getStore(NAMESPACE).put(context.getUniqueId(), createdCategory);
                });
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(CategoryJson.class);
    }

    @Override
    public CategoryJson resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return extensionContext.getStore(NAMESPACE).get(extensionContext.getUniqueId(), CategoryJson.class);
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        CategoryJson category = context.getStore(NAMESPACE).get(context.getUniqueId(), CategoryJson.class);
        spendDbClient.deleteCategory(category);
    }
}
