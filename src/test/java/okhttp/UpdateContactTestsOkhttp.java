package okhttp;

import com.google.gson.Gson;
import dto.ContactDTO;
import dto.MessageDTO;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class UpdateContactTestsOkhttp {
    Gson gson = new Gson();
    public static final MediaType JSON = MediaType.get("Application/json");
    OkHttpClient client = new OkHttpClient();
    String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoiZHVzbTVAZ21haWwuY29tIiwiaXNzIjoiUmVndWxhaXQiLCJleHAiOjE3NjAxODk1NTIsImlhdCI6MTc1OTU4OTU1Mn0.5l7e5zKHX8B4I0wkJRZrGqTLAoTI7H641V6NOyE6D8o";

    @Test
    public void updateContactPositiveTest() throws IOException {
        ContactDTO contactDTO = ContactDTO.builder()
                .id("7da3e49d-0598-4663-a43a-1494419fa313")
                .name("Maya")
                .lastName("ZhuZhu")
                .email("maya@gmail.com")
                .phone("987456216547")
                .address("Flower Land")
                .description("Fantasy")
                .build();
        RequestBody body = RequestBody.create(gson.toJson(contactDTO), JSON);
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .put(body)
                .header("Authorization", token)
                .build();

        Response response = client.newCall(request).execute();
        MessageDTO messageDTO = gson.fromJson(response.body().string(), MessageDTO.class);
        System.out.println(messageDTO.getMessage());
        System.out.println(response.code());

    }
}
//"timestamp": "2025-10-04T16:14:09",
//        "status": 400,
//        "error": "Bad Request",
//        "message": {
//        "lastName": "must not be blank",
//        "address": "must not be blank",
//        "phone": "Phone number must contain only digits! And length min 10, max 15!",
//        "name": "must not be blank"

