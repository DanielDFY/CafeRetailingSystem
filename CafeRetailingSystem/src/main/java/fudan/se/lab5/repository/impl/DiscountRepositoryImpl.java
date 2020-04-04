package fudan.se.lab5.repository.impl;

import fudan.se.lab5.constant.DiscountConstant;
import fudan.se.lab5.constant.UserInfoConstant;
import fudan.se.lab5.discount.DiscountComponent;
import fudan.se.lab5.discount.DiscountInfo;
import fudan.se.lab5.entity.Drink;
import fudan.se.lab5.repository.DiscountRepository;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public class DiscountRepositoryImpl implements DiscountRepository {
    private final List<DiscountComponent> componentList;


    public DiscountRepositoryImpl() {
        this.componentList = new ArrayList<>();

        String filePath = DiscountConstant.CONFIG_FILEPATH;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Document doc = null;
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(new File(filePath));
        } catch (Exception e) {
            throw new RuntimeException("Failed to open config file, because :" + e.getMessage());
        }

        ArrayList<String> componentNameList = new ArrayList<>();
        NodeList catalog = doc.getElementsByTagName("ComponentList").item(0).getChildNodes();
        for (int i = 0; i < catalog.getLength(); ++i) {
            Node node = catalog.item(i);
            if (node instanceof Element) {
                componentNameList.add(node.getTextContent());
            }
        }


        for (String componentName : componentNameList) {
            NodeList strategyList = doc.getElementsByTagName(componentName).item(0).getChildNodes();
            String componentPath = DiscountConstant.COMPONENT_PATH + componentName;

            try {
                Class<?> componentClass = Class.forName(componentPath);
                Constructor<?> constructor = componentClass.getConstructor(NodeList.class);
                componentList.add((DiscountComponent)constructor.newInstance(strategyList));
            } catch (Exception e) {
                throw new RuntimeException("Failed to add component, because: " + e.getMessage());
            }
        }
    }

    public DiscountInfo getLowestDiscountInfo(List<Drink> orderItems, double priceBeforeDiscount, String currencyName, UserInfoConstant infoConstant) {
        DiscountInfo discountInfo = new DiscountInfo(priceBeforeDiscount);
        for (DiscountComponent component : componentList) {
            DiscountInfo discountInfoTemp = component.getDiscountObj(orderItems, priceBeforeDiscount, currencyName, infoConstant);
            if (discountInfo.getDiscount() < discountInfoTemp.getDiscount()) {
                discountInfo = discountInfoTemp;
            }
        }
        return discountInfo;
    }
}
