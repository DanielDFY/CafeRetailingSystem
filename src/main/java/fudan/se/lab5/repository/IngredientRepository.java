package fudan.se.lab5.repository;

import fudan.se.lab5.entity.IngredientEntity;

public interface IngredientRepository {
    //提供数据存储操作
    /**
     * Get  Ingredient by name in data/ Ingredient.csv
     *
     * @param name
     * @return Ingredient
     */
    IngredientEntity getIngredient(String name,int number);

    void createIngredient(IngredientEntity Ingredient);
}
