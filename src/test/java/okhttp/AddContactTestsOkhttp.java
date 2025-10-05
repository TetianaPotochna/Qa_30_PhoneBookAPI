package okhttp;

import com.google.gson.Gson;
import dto.ContactDTO;
import dto.ErrorDTO;
import dto.MessageDTO;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class AddContactTestsOkhttp {
    Gson gson = new Gson();
    public static final MediaType JSON = MediaType.get("Application/json");
    OkHttpClient client = new OkHttpClient();
    String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoiZHVzbTVAZ21haWwuY29tIiwiaXNzIjoiUmVndWxhaXQiLCJleHAiOjE3NjAxODk1NTIsImlhdCI6MTc1OTU4OTU1Mn0.5l7e5zKHX8B4I0wkJRZrGqTLAoTI7H641V6NOyE6D8o";

    @Test
    public void addContactPositiveTest() throws IOException {
        int i = (int) (System.currentTimeMillis()/1000%3600);
        ContactDTO contactDTO = ContactDTO.builder()
                .name("Maya" + i)
                .lastName("ZhuZhu")
                .email("maya" + i + "@gmail.com")
                .phone("98745621" + i)
                .address("Flower")
                .description("Fantasy")
                .build();

        RequestBody body = RequestBody.create(gson.toJson(contactDTO), JSON);
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .post(body)
                .header("Authorization", token)
                .build();

        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(), 200);
        MessageDTO messageDTO = gson.fromJson(response.body().string(), MessageDTO.class);
        String message = messageDTO.getMessage();
        String[]all =message.split(": ");
        String id = all[1];
        System.out.println(id);
        Assert.assertTrue(message.contains("Contact was added!"));
    }

    @Test
    public void addContactWrongPhone() throws IOException {
        ContactDTO contactDTO = ContactDTO.builder()
                .name("Maya")
                .lastName("ZhuZhu")
                .email("asd@mhjf.com")
                .phone("")
                .address("Flower")
                .description("Fantasy")
                .build();

        RequestBody body = RequestBody.create(gson.toJson(contactDTO), JSON);
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .post(body)
                .header("Authorization", token)
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(), 400);
        ErrorDTO errorDTO = gson.fromJson(response.body().string(), ErrorDTO.class);
        Assert.assertEquals(errorDTO.getStatus(), 400);
        Assert.assertEquals(errorDTO.getError(), "Bad Request");
        Assert.assertEquals(errorDTO.getMessage().toString(), "{phone=Phone number must contain only digits! And length min 10, max 15!}");
    }

    @Test
    public void addContactWrongToken() throws IOException {
        ContactDTO contactDTO = ContactDTO.builder()
                .name("Maya")
                .lastName("ZhuZhu")
                .email("maya@gmail.com")
                .phone("987456216547")
                .address("Flower")
                .description("Fantasy")
                .build();
        RequestBody body = RequestBody.create(gson.toJson(contactDTO), JSON);
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .post(body)
                .header("Authorization", "kjnkjbvgc")
                .build();

        Response response = client.newCall(request).execute();
        ErrorDTO errorDTO = gson.fromJson(response.body().string(), ErrorDTO.class);
        Assert.assertEquals(errorDTO.getStatus(), 401);
        Assert.assertEquals(errorDTO.getMessage().toString(), "JWT strings must contain exactly 2 period characters. Found: 0");
        Assert.assertEquals(errorDTO.getError(), "Unauthorized");

    }

    @Test(enabled = false)//this is a BUG. provides an opportunity to create a duplicate contact
    public void addContactDuplicate() throws IOException {
        ContactDTO contactDTO = ContactDTO.builder()
                .name("Maya")
                .lastName("ZhuZhu")
                .email("maya@gmail.com")
                .phone("987456216547")
                .address("Flower")
                .description("Fantasy")
                .build();

        RequestBody body = RequestBody.create(gson.toJson(contactDTO), JSON);
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .post(body)
                .header("Authorization", token)
                .build();

        Response response = client.newCall(request).execute();

//        Assert.assertFalse(response.isSuccessful());
//        Assert.assertEquals(response.code(), 409);
//        ErrorDTO errorDTO = gson.fromJson(response.body().string(), ErrorDTO.class);
//        System.out.println(errorDTO.getMessage());
//        System.out.println(errorDTO.getStatus());
    }

}
