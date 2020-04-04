package fudan.se.lab5.repository.impl;

import fudan.se.lab5.constant.FileConstant;
import fudan.se.lab5.constant.StyleConstant;
import fudan.se.lab5.constant.SystemInfoConstant;
import fudan.se.lab5.entity.IngredientEntity;
import fudan.se.lab5.repository.IngredientRepository;
import fudan.se.lab5.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;

public class IngredientRepositoryImpl implements IngredientRepository {
    private static Logger errorLogger = LoggerFactory.getLogger("orderInfoLogger");

    @Override
    public IngredientEntity getIngredient(String name, int number) {
        if (!IngredientEntity.validDrinkNameStyle(name)) {
            throw new RuntimeException(StyleConstant.INVALID_INGREDIENT_STYLE);
        }
        try {
            return stringArrayToObject(FileUtil.readByName(name, FileConstant.INGREDIENT_CSV), number);
        } catch (RuntimeException e) {
            errorLogger.error(e.getMessage());
            throw new RuntimeException(SystemInfoConstant.UNKNOWN_INGREDIENT_NAME);
        }
    }

    @Override
    public void createIngredient(IngredientEntity ingredient) {
        FileUtil.write(objectToStringArray(ingredient), FileConstant.INGREDIENT_CSV);
    }

    private String[] objectToStringArray(IngredientEntity ingredient) {
        // if user already exists, throw exception
        if (FileUtil.exist(ingredient.getName(), FileConstant.INGREDIENT_CSV)) {
            throw new RuntimeException(MessageFormat.format(SystemInfoConstant.Entity_EXIST, "Ingredient",
                    ingredient.getName()));
        }
        String[] array = new String[2];
        array[0] = ingredient.getName();
        array[1] = String.valueOf(ingredient.getPrice());
        return array;
    }

    private IngredientEntity stringArrayToObject(String[] array, int number) {
        IngredientEntity ingredient = new IngredientEntity();
        ingredient.setName(array[0]);
        ingredient.setPrice(Double.parseDouble(array[1]));
        ingredient.setNumber(number);
        return ingredient;
    }
}
