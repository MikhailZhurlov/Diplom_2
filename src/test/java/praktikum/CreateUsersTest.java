package praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static praktikum.ErrorMessage.*;

public class CreateUsersTest {
    String accessToken;

    CreateUsers createUsers;
    LoginUser loginUser;
    UserToken userToken;

    @Before
    public void setUp() {
        loginUser = new LoginUser();
    }

    @After
    public void tearDown(){
        if (accessToken != null) {
            UserClient.deleteUsers(accessToken);
        }
    }

    @Test
    @DisplayName("Создание уникального пользователя")
    public void createUsersTest() {
        createUsers = GenerationUsers.getRandomUsers();
        ValidatableResponse response = UserClient.create(createUsers);
        boolean isOk = response.extract().path("success");
        accessToken = response.extract().path("accessToken");
        response.statusCode(SC_OK);
        assertTrue(isOk);

    }

    @Test
    @DisplayName("Создание пользователя, который уже зарегистрирован")
    public void createDoubleUserTest() {
        createUsers = GenerationUsers.getRandomUsers();
        ValidatableResponse response = UserClient.create(createUsers);
        response.statusCode(SC_OK);
        boolean isOk = response.extract().path("success");
        assertTrue(isOk);
        ValidatableResponse doubleResponse = UserClient.create(createUsers);
        doubleResponse.statusCode(SC_FORBIDDEN).and().assertThat().body("message", is(FORBIDDEN));
    }

    @Test
    @DisplayName("Создание пользователя, без поля Email")
    public void createWithoutEmailTest() {
        createUsers = GenerationUsers.getRandomWithoutEmail();
        ValidatableResponse response = UserClient.create(createUsers);
        response.statusCode(SC_FORBIDDEN).and().assertThat().body("message", is(NOT_FIELD));
    }

    @Test
    @DisplayName("Создание пользователя, без поля Password")
    public void createWithoutPasswordTest() {
        createUsers = GenerationUsers.getRandomWithoutPassword();
        ValidatableResponse response = UserClient.create(createUsers);
        response.statusCode(SC_FORBIDDEN).and().assertThat().body("message", is(NOT_FIELD));
    }

    @Test
    @DisplayName("Создание пользователя, без поля Name")
    public void createWithoutNameTest() {
        createUsers = GenerationUsers.getRandomWithoutName();
        ValidatableResponse response = UserClient.create(createUsers);
        response.statusCode(SC_FORBIDDEN).and().assertThat().body("message", is(NOT_FIELD));
    }
}

