package praktikum;

import io.restassured.response.ValidatableResponse;

public class OrderClient extends ConfigRest {
    public static final String ORDER_PATH = "api/orders";

    public static ValidatableResponse createOrder(OrderBurger orderBurger, String token){
        return getSpec()
                .header("Authorization", token)
                .body(orderBurger)
                .when()
                .post(ORDER_PATH)
                .then().log().all();
    }

    public static ValidatableResponse createOrderNotAuth(OrderBurger orderBurger){
        return getSpec()
                .body(orderBurger)
                .when()
                .post(ORDER_PATH)
                .then().log().all();
    }

    public static ValidatableResponse createOrderNoIngredients(String token){
        return getSpec()
                .header("Authorization", token)
                .when()
                .post(ORDER_PATH)
                .then().log().all();
    }

    public static ValidatableResponse getOrdersUser(String token){
        return getSpec()
                .header("Authorization", token)
                .when()
                .get(ORDER_PATH)
                .then().log().all();
    }

    public static ValidatableResponse getOrdersNoAuth(){
        return getSpec()
                .when()
                .get(ORDER_PATH)
                .then().log().all();
    }
}
