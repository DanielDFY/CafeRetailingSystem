package fudan.se.lab5.repository.impl;

import fudan.se.lab5.constant.FileConstant;
import fudan.se.lab5.constant.SystemInfoConstant;
import fudan.se.lab5.entity.Drink;
import fudan.se.lab5.repository.DrinkRepository;
import fudan.se.lab5.util.FileUtil;

import java.text.MessageFormat;

import static fudan.se.lab5.util.LogUtil.sysLogger;

public class DrinkRepositoryImpl implements DrinkRepository {

    @Override
    public Drink getDrink(String name, String currency, String language, String id) {
        if (name == null || !Drink.validDrinkNameStyle(name)) {
            sysLogger.error(id + " " + SystemInfoConstant.UNKNOWN_DRINK_NAME);
            throw new RuntimeException(SystemInfoConstant.UNKNOWN_DRINK_NAME);
        }
        try {
            return stringArrayToObject(FileUtil.readByName(name, FileConstant.DRINK_CSV),currency,language);
        } catch (RuntimeException e) {
            sysLogger.info(e.getMessage());
            //throw new RuntimeException(SystemInfoConstant.UNKNOWN_DRINK_NAME);
            throw new RuntimeException(e.getMessage() + " " + name);
        }
    }

    @Override
    public void createDrink(String[] drink, String id) {
        FileUtil.write(objectToStringArray(drink, id), FileConstant.DRINK_CSV);
    }

    private String[] objectToStringArray(String[] drink, String id) {
        // if user already exists, throw exception
        if (FileUtil.exist(drink[0], FileConstant.DRINK_CSV)) {
            sysLogger.error(id + " " + MessageFormat.format(SystemInfoConstant.Entity_EXIST, "Drink",
                    drink[0]));
            throw new RuntimeException(MessageFormat.format(SystemInfoConstant.Entity_EXIST, "Drink",
                    drink[0]));
        }
        return drink;
    }

    private Drink stringArrayToObject(String[] array,String currency,String language) {
        Drink drink = new Drink();
        switch (language){
            case "English":
                drink.setName(array[0]);
                drink.setDescription(array[1]);
                break;
            case "Chinese":
                drink.setName(array[2]);
                drink.setDescription(array[3]);
                break;
            default:
                drink.setName(array[0]);
                drink.setDescription(array[1]);
                break;
        }
        switch (currency){
            case "USDollar":
                drink.setPrice(Double.parseDouble(array[4]));
                drink.setSizePrizeMap(Double.parseDouble(array[5]),Double.parseDouble(array[6]),Double.parseDouble(array[7]));
                break;
            case "RMB":
                drink.setPrice(Double.parseDouble(array[8]));
                drink.setSizePrizeMap(Double.parseDouble(array[9]),Double.parseDouble(array[10]),Double.parseDouble(array[11]));
                break;
            case "HKDollar":
                drink.setPrice(Double.parseDouble(array[12]));
                drink.setSizePrizeMap(Double.parseDouble(array[13]),Double.parseDouble(array[14]),Double.parseDouble(array[15]));
                break;
            default:
                drink.setPrice(Double.parseDouble(array[4]));
                drink.setSizePrizeMap(Double.parseDouble(array[5]),Double.parseDouble(array[6]),Double.parseDouble(array[7]));
                break;
        }
        drink.setSize(Integer.parseInt(array[16]));
        drink.setType(array[17]);
        return drink;
       }
    }