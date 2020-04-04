

package fudan.se.lab5.repository;

import fudan.se.lab5.entity.Drink;

public interface DrinkRepository {

    Drink getDrink(String name, String currency, String language, String id);

    void createDrink(String[] drink, String id);
}