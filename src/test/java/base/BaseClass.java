package base;

import io.restassured.path.json.JsonPath;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class BaseClass {

    public static String getGlobalValues(String key) throws IOException {

        Properties pro = new Properties();
        pro.load(new FileInputStream("src/test/resources/properties/global.properties"));
        return pro.getProperty(key);
    }

    public static String convertStringToJSONAndGetStringValue(String response, String key) {
        JsonPath jp = new JsonPath(response);
        return jp.get(key);
    }
}
