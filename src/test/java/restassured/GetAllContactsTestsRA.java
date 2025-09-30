package restassured;

import dto.AllContactsDTO;
import dto.ContactDTO;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class GetAllContactsTestsRA {

    String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoiZHVzbTVAZ21haWwuY29tIiwiaXNzIjoiUmVndWxhaXQiLCJleHAiOjE3NTk4MDM5MzYsImlhdCI6MTc1OTIwMzkzNn0.RfU_MfjmfNynD8WGG_HrTqYIN7U0VWqeOPzeiT1mW2U";
    String endpoint = "contacts";

    @BeforeMethod
    public void preCondition() {
        RestAssured.baseURI = "https://contactapp-telran-backend.herokuapp.com";
        RestAssured.basePath = "v1";
    }

    @Test
    public void getAllContactsSuccess() {

        AllContactsDTO contactsDTO = given()
                .header("Authorization", token)
                .when()
                .get(endpoint)
                .then()
                .assertThat().statusCode(200)
                .extract()
                .response()
                .as(AllContactsDTO.class);

        List<ContactDTO>list = contactsDTO.getContacts();
        for (ContactDTO contact:list){
            System.out.println(contact.getId());
            System.out.println(contact.getEmail());
            System.out.println("Size of list" + list.size());
        }
    }
}
