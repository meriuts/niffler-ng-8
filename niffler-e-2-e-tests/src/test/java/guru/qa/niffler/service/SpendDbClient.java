package guru.qa.niffler.service;

import guru.qa.niffler.config.Config;
import guru.qa.niffler.data.Databases;
import guru.qa.niffler.data.dao.impl.CategoryDaoJdbc;
import guru.qa.niffler.data.dao.impl.SpendDaoJdbc;
import guru.qa.niffler.data.entity.spend.CategoryEntity;
import guru.qa.niffler.data.entity.spend.SpendEntity;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.SpendJson;

public class SpendDbClient {

    private static final Config CFG = Config.getInstanceForLocale();

    public SpendJson createSpend(SpendJson spend) {
        return Databases.transaction(connection -> {
            SpendEntity spendEntity = SpendEntity.fromJson(spend);
            if (spendEntity.getCategory().getId() == null) {
                CategoryEntity categoryEntity = new CategoryDaoJdbc(connection).create(spendEntity.getCategory());
                spendEntity.setCategory(categoryEntity);
            }
            return SpendJson.fromEntity(new SpendDaoJdbc(connection).create(spendEntity));
        }, CFG.spendJdbcUrl(), Databases.TransactionIsolationLvl.REPEATABLE_READ);
    }

    public CategoryJson createCategory(CategoryJson category) {
        return Databases.transaction(connection -> {
           CategoryEntity categoryEntity = new CategoryDaoJdbc(connection).create(CategoryEntity.fromJson(category));
           return CategoryJson.fromEntity(categoryEntity);
        }, CFG.spendJdbcUrl(), Databases.TransactionIsolationLvl.REPEATABLE_READ);
    }

    public void deleteCategory(CategoryJson category) {
        Databases.transaction(connection -> {
            CategoryEntity categoryEntity = CategoryEntity.fromJson(category);
            new CategoryDaoJdbc(connection).deleteCategory(categoryEntity);
        }, CFG.spendJdbcUrl(), Databases.TransactionIsolationLvl.REPEATABLE_READ);
    }
}
