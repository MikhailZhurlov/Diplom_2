package praktikum;
import io.restassured.response.ValidatableResponse;

public class UserClient extends ConfigRest {
    private static final String USER_PATH = "api/auth/register";
    private static final String LOGIN_PATH = "api/auth/login";
    private static final String UPDATING_USER = "api/auth/user";
    private static final String DELETE_USER = "api/auth/user";

    public static ValidatableResponse create(CreateUsers createUsers){
        return getSpec()
                .body(createUsers)
                .when()
                .post(USER_PATH)
                .then().log().all();
    }

    public static ValidatableResponse loginAuth(LoginUser loginUser){
        return getSpec()
                .body(loginUser)
                .when()
                .post(LOGIN_PATH)
                .then().log().all();
    }

    public static ValidatableResponse updatingUsers(LoginUser loginUser, UserToken userToken){
        return getSpec()
                .header("Authorization", userToken.getToken())
                .and()
                .body(loginUser)
                .when()
                .patch(UPDATING_USER)
                .then().log().all();
    }

    public static ValidatableResponse updatingNoAuthUsers(LoginUser loginUser){
        return getSpec()
                .and()
                .body(loginUser)
                .when()
                .patch(UPDATING_USER)
                .then().log().all();
    }

    public static ValidatableResponse deleteUsers(String token){
        return getSpec()
                .header("Authorization", token)
                .when()
                .delete(DELETE_USER)
                .then().log().all();
    }
}
