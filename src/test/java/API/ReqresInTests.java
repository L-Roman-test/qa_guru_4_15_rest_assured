package API;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ReqresInTests {
    private final int userId = 2;
    private final String newUserName = "morpheus";

    @BeforeAll
    static void setup() {
        RestAssured.filters(new AllureRestAssured());
        RestAssured.baseURI = "https://reqres.in";
    }

    @Test
    public void createUserTest() {
        String user = "{\"name\": \"" + newUserName + "\",\"job\": \"leader\"}";

        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/api/users")
                .then()
                .statusCode(201)
                .body("id", is(notNullValue()));
    }

    @Test
    public void updateUserTest() {
        String user = "{\"name\": \"" + newUserName + "\",\"job\": \"zion resident\"}";

        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .put("/api/users/" + userId + "")
                .then()
                .statusCode(200)
                .body("job", is("zion resident"));
    }

    @Test
    public void deleteUserTest() {
        given()
                .when()
                .delete("/api/users/" + userId + "")
                .then()
                .statusCode(204);
    }

    @Test
    public void listOfUsersTest() {
        given()
                .when()
                .get("/api/users?page=1")
                .then()
                .statusCode(200)
                .body("support.text", is("To keep ReqRes free, contributions towards server costs are appreciated!"));
    }

    @Test
    public void singleUserTest() {
        given()
                .when()
                .get("/api/users/" + userId + "")
                .then()
                .statusCode(200)
                .log().body()
                .body("data.id", is(userId));
    }
}



