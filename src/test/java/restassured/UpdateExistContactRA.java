package restassured;

import dto.ContactDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class UpdateExistContactRA {
    String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoiZHVzbTVAZ21haWwuY29tIiwiaXNzIjoiUmVndWxhaXQiLCJleHAiOjE3NTk4Mjg5NjMsImlhdCI6MTc1OTIyODk2M30._zN4NWjZsxQ4ov4BwhgNs1Kx5Mt0hgfmDk8Go55QMx0";
    String id;
    String endpoint = "contacts";

    int i = new Random().nextInt(1000) + 1000;

    ContactDTO contact = ContactDTO.builder()
            .name("Stiven")
            .lastName("Smith")
            .email("stiv" + i + "@gmail.com")
            .phone("21354687" + i)
            .address("home")
            .description("brother")
            .build();

    @BeforeMethod
    public void preCondition() {
        RestAssured.baseURI = "https://contactapp-telran-backend.herokuapp.com";
        RestAssured.basePath = "v1";

        String message = given()
                .body(contact)
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .when()
                .post("contacts")
                .then()
                .assertThat().statusCode(200)
                .extract()
                .path("message");
        String[] all = message.split(": ");
        id = all[1];
    }

    @Test
    public void updateExistContactSuccess() {
        String name = contact.getName();
        contact.setId(id);
        contact.setName("Yuriiiiiiy");

        given()
                .body(contact)
                .header("Authorization", token)
                .contentType(ContentType.JSON)
                .when()
                .put(endpoint)
                .then()
                .assertThat().statusCode(200)
                .assertThat().body("message", containsString("Contact was updated"));

    }
}
