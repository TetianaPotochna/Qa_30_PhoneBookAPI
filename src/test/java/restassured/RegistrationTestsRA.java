package restassured;

import dto.AuthRequestDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class RegistrationTestsRA {

    String endpoint = "user/registration/usernamepassword";

    @BeforeMethod
    public void preCondition() {
        RestAssured.baseURI = "https://contactapp-telran-backend.herokuapp.com/";
        RestAssured.basePath = "v1";
    }

    @Test
    public void registrationSuccess() {
        int i = new Random().nextInt(1000) + 1000;

        AuthRequestDTO auth = AuthRequestDTO.builder()
                .username("dusm" + i + "@gmail.com")
                .password("Dudu12345@")
                .build();

        String token = given()
                .body(auth)
                .contentType(ContentType.JSON)
                .when()
                .post(endpoint)
                .then()
                .assertThat().statusCode(200)
                .extract()
                .path("token");
        System.out.println(token);
    }

    @Test
    public void registrationWrongEmail() {
        AuthRequestDTO auth = AuthRequestDTO.builder()
                .username("dusmgmail.com")
                .password("Dudu12345@")
                .build();
        given()
                .body(auth)
                .contentType(ContentType.JSON)
                .when()
                .post(endpoint)
                .then()
                .assertThat().statusCode(400)
                .assertThat().body("message.username", containsString("must be a well-formed email address"));
    }

    @Test
    public void registrationWrongPassword(){
        AuthRequestDTO auth = AuthRequestDTO.builder()
                .username("dusm55669@gmail.com")
                .password("Dudu")
                .build();

        given()
                .body(auth)
                .contentType(ContentType.JSON)
                .when()
                .post(endpoint)
                .then()
                .assertThat().statusCode(400)
                .assertThat().body("message.password", containsString(" At least 8 characters; Must contain at least 1 uppercase letter, 1 lowercase letter, and 1 number; Can contain special characters [@$#^&*!]"));
    }

    @Test
    public void registrationDuplicate(){
        AuthRequestDTO auth = AuthRequestDTO.builder()
                .username("dusm5@gmail.com")
                .password("Dudu12345@")
                .build();

        given()
                .body(auth)
                .contentType(ContentType.JSON)
                .when()
                .post(endpoint)
                .then()
                .assertThat().statusCode(409)
                .assertThat().body("message", containsString("User already exists"));
    }
}
