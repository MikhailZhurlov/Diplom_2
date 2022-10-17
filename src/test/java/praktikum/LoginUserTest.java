package praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;
import static praktikum.ErrorMessage.NOT_FOUND;

public class LoginUserTest {
    String accessToken;
    CreateUsers createUsers;
    UserClient userClient;
    UserToken userToken;

    @Before
    public void setUp() {
        createUsers = GenerationUsers.getRandomUsers();
        userClient = new UserClient();
    }

    @After
    public void tearDown() {
        if (accessToken != null) {
            UserClient.deleteUsers(accessToken);
        }
    }

    @Test
    @DisplayName("логин под существующим пользователем")
    public void loginUserTest() {
        UserClient.create(createUsers);
        ValidatableResponse response = UserClient.loginAuth(LoginUser.from(createUsers));
        response.statusCode(SC_OK).and().assertThat().body("accessToken", notNullValue());
        accessToken = response.extract().path("accessToken");
    }

    @Test
    @DisplayName("логин с неверным логином и паролем")
    public void loginNotFoundUserTest() {
        ValidatableResponse response = UserClient.loginAuth(LoginUser.from(createUsers));
        response.statusCode(SC_UNAUTHORIZED).and().assertThat().body("message", is(NOT_FOUND));
    }

    @Test
    @DisplayName("Изменение данных с авторизацией")
    public void updateUserTest() {
        UserClient.create(createUsers);
        accessToken = UserClient.loginAuth(LoginUser.from(createUsers)).extract().path("accessToken");
        UserToken userToken = new UserToken(accessToken);
        CreateUsers updateUser = GenerationUsers.getRandomUsers();
        ValidatableResponse response = UserClient.updatingUsers(LoginUser.from(updateUser), userToken);
        boolean isOk = response.extract().path("success");
        assertTrue(isOk);
        response.statusCode(SC_OK);
    }

    @Test
    @DisplayName("Изменение данных без авторизации")
    public void updateUserNoAuthTest() {
        UserClient.create(createUsers);
        CreateUsers updateUser = GenerationUsers.getRandomUsers();
        ValidatableResponse response = UserClient.updatingNoAuthUsers(LoginUser.from(updateUser));
        boolean isFalse = response.extract().path("success");
        assertFalse(isFalse);
        response.statusCode(SC_UNAUTHORIZED);
    }
}