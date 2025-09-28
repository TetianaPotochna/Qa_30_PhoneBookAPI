package okhttp;

import com.google.gson.Gson;
import dto.ContactDTO;
import dto.MessageDTO;
import okhttp3.*;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Random;

public class DeleteContactByIDTestOkhttp {
    String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoibWFyZ29AZ21haWwuY29tIiwiaXNzIjoiUmVndWxhaXQiLCJleHAiOjE3NTkwNzE3NTAsImlhdCI6MTc1ODQ3MTc1MH0.FeP3SryF43GzXm78mm9nH3EEKhK9xrRcjmlc7uyJbCg";
    Gson gson = new Gson();
    public static final MediaType JSON = MediaType.get("application/json;charset=utf-8");
    OkHttpClient client = new OkHttpClient();

    String id;

    @BeforeMethod
    public void preCondition() throws IOException {
        //create contact
        int i = new Random().nextInt(1000) + 1000;
        ContactDTO contactDTO = ContactDTO.builder()
                .name("Maya")
                .lastName("Dow")
                .email("maya" + i + "@gmail.com")
                .phone("12365984" + i)
                .address("NY")
                .description("MayaDow")
                .build();

        RequestBody body = RequestBody.create(gson.toJson(contactDTO), JSON);

        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .post(body)
                .addHeader("Authorization", token)
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        MessageDTO messageDTO = gson.fromJson(response.body().string(), MessageDTO.class);
        String message = messageDTO.getMessage();// "Contact was added! ID: a72596d5-4c61-40ce-a687-67ecffa2765d"
        System.out.println(message);
        String[] all = message.split(": ");
        id = all[1];
        System.out.println(id);

        //get id from "message": "Contact was added! ID: a72596d5-4c61-40ce-a687-67ecffa2765d"


    }


    @Test
    public void DeleteContactByIDSuccess() throws IOException {
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts/" + id)
                .delete()
                .addHeader("Authorization", token)
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertEquals(response.code(), 200);
        MessageDTO dto = gson.fromJson(response.body().string(), MessageDTO.class);
        Assert.assertEquals(dto.getMessage(), "Contact was deleted!");
        System.out.println(dto.getMessage());
    }
}


//4b40514f-bd91-4623-91f9-f9401ccaa704
//anna@anna
//================================
//a1357169-95db-4cb3-a29a-576efb04b57b
//elena@ellema
//================================
//e2e9836f-3ed4-4925-8b0c-bd671d67c9f0
//petr@petr