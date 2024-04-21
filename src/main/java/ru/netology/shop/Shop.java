package ru.netology.shop;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import ru.netology.basket.Basket;
import ru.netology.logging.ClientLog;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;

public class Shop {

    private static final String PATH = "src/main/resources/shop-config.xml";
    private static final XPath X_PATH = XPathFactory.newInstance().newXPath();

    public static Basket load() throws Exception {
        Document doc = getDocument();
        String bool = X_PATH.evaluate("/config/load/enabled/text()", doc);
        String path = X_PATH.evaluate("/config/load/fileName/text()", doc);
        File fileJson, fileTxt;
        if (bool.equals("true")) {
            if (path.equals("basket.json")) {
                fileJson = new File(path);
                if (fileJson.exists()) {
                    return Basket.loadFromJson(fileJson);
                }
            } else if (path.equals("basket.txt")) {
                fileTxt = new File(path);
                if (fileTxt.exists()) {
                    return Basket.loadFromTxtFile(fileTxt);
                }
            }
        }
        return Basket.defaultBasket();
    }

    public static void save(Basket basket) throws Exception {
        Document doc = getDocument();
        String bool = X_PATH.evaluate("/config/save/enabled/text()", doc);
        String format = X_PATH.evaluate("/config/save/format/text()", doc);
        if (bool.equals("true")) {
            if (format.equals("json")) {
                basket.saveJson();
            } else {
                basket.saveTxt();
            }
        }
    }

    public static void log(ClientLog person1) throws Exception {
        Document doc = getDocument();
        String bool = X_PATH.evaluate("/config/log/enabled/text()", doc);
        //String bool = doc.getDocumentElement().getChildNodes().item(5).getChildNodes().item(1).getTextContent();
        if (bool.equals("true")) {
            person1.exportAsCSV(person1.getLog());
        }
    }

    private static Document getDocument() throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new File(PATH));
    }

}
