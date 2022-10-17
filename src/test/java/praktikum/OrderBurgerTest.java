package praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static praktikum.ErrorMessage.GET_ORDER_NO_AUTH;
import static praktikum.ErrorMessage.NO_INGREDIENTS;

public class OrderBurgerTest {
    private String accessToken;
    CreateUsers createUsers;
    UserClient userClient;
    OrderClient orderClient;

    @Before
    public void setUp() {
        createUsers = GenerationUsers.getRandomUsers();
        userClient = new UserClient();
        orderClient = new OrderClient();
    }

    @After
    public void tearDown() {
        if (accessToken != null) {
            UserClient.deleteUsers(accessToken);
        }
    }

    @Test
    @DisplayName("Создание заказа с авторизацией")
    public void orderBurgerAuthTest(){
        UserClient.create(createUsers);
        ValidatableResponse response = UserClient.loginAuth(LoginUser.from(createUsers));
        response.statusCode(SC_OK);
        accessToken = response.extract().path("accessToken");
        String[] ingredients = {"61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa70"};
        OrderBurger orderBurger = new OrderBurger(ingredients);
        ValidatableResponse orderResponse = OrderClient.createOrder(orderBurger, accessToken);
        orderResponse.statusCode(SC_OK).and().assertThat().body("order.number", notNullValue());
    }

    @Test
    @DisplayName("Создание заказа без авторизации")
    public void orderBurgerNoAuthTest(){
        ValidatableResponse response = UserClient.create(createUsers);
        response.statusCode(SC_OK);
        String[] ingredients = {"61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa70"};
        OrderBurger orderBurger = new OrderBurger(ingredients);
        ValidatableResponse orderResponse = OrderClient.createOrderNotAuth(orderBurger);
        orderResponse.statusCode(SC_OK).and().assertThat().body("order.number", notNullValue());
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов")
    public void orderBurgerNoIngredientsTest(){
        UserClient.create(createUsers);
        ValidatableResponse response = UserClient.loginAuth(LoginUser.from(createUsers));
        response.statusCode(SC_OK);
        accessToken = response.extract().path("accessToken");
        ValidatableResponse orderResponse = OrderClient.createOrderNoIngredients(accessToken);
        orderResponse.statusCode(SC_BAD_REQUEST).and().assertThat().body("message", is(NO_INGREDIENTS));
    }

    @Test
    @DisplayName("Создание заказа с неверным хешем ингредиентов")
    public void orderBurgerInvalidHashTest(){
        UserClient.create(createUsers);
        ValidatableResponse response = UserClient.loginAuth(LoginUser.from(createUsers));
        response.statusCode(SC_OK);
        accessToken = response.extract().path("accessToken");
        String[] ingredients = {"@@@", "#####"};
        OrderBurger orderBurger = new OrderBurger(ingredients);
        ValidatableResponse orderResponse = OrderClient.createOrder(orderBurger, accessToken);
        orderResponse.statusCode(SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    @DisplayName("Получение заказа с авторизацией")
    public void getOrderBurgerTest(){
        UserClient.create(createUsers);
        ValidatableResponse response = UserClient.loginAuth(LoginUser.from(createUsers));
        response.statusCode(SC_OK);
        accessToken = response.extract().path("accessToken");
        String[] ingredients = {"61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa70"};
        OrderBurger orderBurger = new OrderBurger(ingredients);
        OrderClient.createOrder(orderBurger, accessToken);
        ValidatableResponse orderResponse = OrderClient.getOrdersUser(accessToken);
        orderResponse.statusCode(SC_OK);
    }

    @Test
    @DisplayName("Получение заказа без авторизациич")
    public void getOrderBurgerNoAuthTest(){
        ValidatableResponse response = UserClient.create(createUsers);
        response.statusCode(SC_OK);
        ValidatableResponse orderResponse = OrderClient.getOrdersNoAuth();
        orderResponse.statusCode(SC_UNAUTHORIZED).and().assertThat().body("message", is(GET_ORDER_NO_AUTH));
    }
}     