package restassured;

import dto.AuthRequestDTO;
import dto.AuthResponseDTO;
import dto.ErrorDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.containsString;


public class LoginTestsRA {

    String endpoint = "user/login/usernamepassword";

    @BeforeMethod
    public void preCondition() {
        RestAssured.baseURI = "https://contactapp-telran-backend.herokuapp.com";
        RestAssured.basePath = "v1";
    }

    @Test
    public void LoginSuccess() {
        AuthRequestDTO auth = AuthRequestDTO.builder()
                .username("dusm5@gmail.com")
                .password("Dudu12345@")
                .build();

        AuthResponseDTO responseDTO = given()
                .body(auth)
                .contentType("Application/json")
                .when()
                .post(endpoint)
                .then()
                .assertThat().statusCode(200)
                .extract()
                .response()
                .as(AuthResponseDTO.class);
        System.out.println(responseDTO.getToken());
    }
    @Test
    public void LoginWrongEmail() {
        AuthRequestDTO auth = AuthRequestDTO.builder()
                .username("dusm5gmail.com")
                .password("Dudu12345@")
                .build();

      ErrorDTO errorDTO = given()
                .body(auth)
                .contentType(ContentType.JSON)
                .when()
                .post(endpoint)
                .then()
                .assertThat().statusCode(401)
                .extract()
                .response()
                .as(ErrorDTO.class);
      Assert.assertEquals(errorDTO.getMessage(), "Login or Password incorrect");
    }

    @Test
    public void loginWrongEmailFormat(){
        AuthRequestDTO auth = AuthRequestDTO.builder()
                .username("dusm5gmail.com")
                .password("Dudu12345@")
                .build();

        given()
                .body(auth)
                .contentType(ContentType.JSON)
                .when()
                .post(endpoint)
                .then()
                .assertThat().statusCode(401)
                .assertThat().body("message", containsString("Login or Password incorrect"))
                .assertThat().body("path",equalTo("/v1/user/login/usernamepassword"));
    }

    @Test
    public void loginWrongPassword(){
        AuthRequestDTO auth = AuthRequestDTO.builder()
                .username("dusm5@gmail.com")
                .password("Dud")
                .build();

      ErrorDTO errorDTO = given()
                .body(auth)
                .contentType(ContentType.JSON)
                .when()
                .post(endpoint)
                .then()
                .assertThat().statusCode(401)
                .extract()
                .response()
                .as(ErrorDTO.class);
        Assert.assertEquals(errorDTO.getMessage(), "Login or Password incorrect");
    }

    @Test
    public void loginWrongPasswordFormat(){
        AuthRequestDTO auth = AuthRequestDTO.builder()
                .username("dusm5@gmail.com")
                .password("Dudu")
                .build();

        given()
                .body(auth)
                .contentType(ContentType.JSON)
                .when()
                .post(endpoint)
                .then()
                .assertThat().statusCode(401)
                .assertThat().body("message", equalTo("Login or Password incorrect"))
                .assertThat().body("path", equalTo("/v1/user/login/usernamepassword"));
    }
    }


