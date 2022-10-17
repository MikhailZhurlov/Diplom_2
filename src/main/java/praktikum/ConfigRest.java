package praktikum;

import groovy.xml.StreamingDOMBuilder;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class ConfigRest {
    public static final String BASE_URL =  "https://stellarburgers.nomoreparties.site";

    public static RequestSpecification getSpec(){
        return given().log().all()
                .header("Content-type", "application/json")
                .baseUri(ConfigRest.BASE_URL);
    }
}
