package restassured;

import dto.ContactDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class DeleteContactByIDRA {
    String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoiZHVzbTVAZ21haWwuY29tIiwiaXNzIjoiUmVndWxhaXQiLCJleHAiOjE3NTk4MDM5MzYsImlhdCI6MTc1OTIwMzkzNn0.RfU_MfjmfNynD8WGG_HrTqYIN7U0VWqeOPzeiT1mW2U";
    String id;


    @BeforeMethod
    public void preCondition() {
        RestAssured.baseURI = "https://contactapp-telran-backend.herokuapp.com";
        RestAssured.basePath = "v1";
        int i = new Random().nextInt(1000) + 1000;
        ContactDTO contactDTO = ContactDTO.builder()
                .name("Anna")
                .lastName("Home")
                .email("anna" + i + "@gmail.com")
                .phone("12365478" + i)
                .address("Haifa")
                .description("owner")
                .build();
        String message = given().body(contactDTO)
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
    public void deleteContactByIDSuccess() {
        given()
                .header("Authorization", token)
                .when()
                .delete("contacts/" + id)
                .then()
                .assertThat().statusCode(200)
                .assertThat().body("message", equalTo("Contact was deleted!"));
    }

    @Test
    public void deleteContactByIdWrongToken() {
        given()
                .header("Authorization", "kjfkjjkjnkjn")
                .when()
                .delete("contacts" + id)
                .then()
                .assertThat().statusCode(401);
    }
}
