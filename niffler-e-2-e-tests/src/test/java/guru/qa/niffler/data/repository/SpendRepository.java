package guru.qa.niffler.data.repository;

import guru.qa.niffler.data.entity.spend.CategoryEntity;
import guru.qa.niffler.data.entity.spend.SpendEntity;

import java.util.Optional;
import java.util.UUID;

public interface SpendRepository {

    SpendEntity create(SpendEntity spend);

    Optional<SpendEntity> findSpendById(UUID id);

    SpendEntity update(SpendEntity spend);

    void remove(SpendEntity spend);

    CategoryEntity create(CategoryEntity category);

    Optional<CategoryEntity> findById(UUID id);

    void removeCategory(CategoryEntity category);
}
