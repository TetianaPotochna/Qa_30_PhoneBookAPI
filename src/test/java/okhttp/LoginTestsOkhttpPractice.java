package okhttp;

import com.google.gson.Gson;
import dto.AuthRequestDTO;
import dto.AuthResponseDTO;
import dto.ErrorDTO;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginTestsOkhttpPractice {
    Gson gson = new Gson();
    public static final MediaType JSON = MediaType.get("Application/json");
    OkHttpClient client = new OkHttpClient();

    @Test
    public void loginPositiveTestOkhttpPractice() throws IOException {
        AuthRequestDTO auth = AuthRequestDTO.builder().username("dusm5@gmail.com").password("Dudu12345@").build();
        RequestBody body = RequestBody.create(gson.toJson(auth), JSON);
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/login/usernamepassword")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(), 200);
        AuthResponseDTO responseDTO = gson.fromJson(response.body().string(), AuthResponseDTO.class);
        System.out.println(responseDTO.getToken());

//eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoiZHVzbTVAZ21haWwuY29tIiwiaXNzIjoiUmVndWxhaXQiLCJleHAiOjE3NjAwODc5MjMsImlhdCI6MTc1OTQ4NzkyM30.HxJ2o-HfWa-WQIaa8Hs4Y_9h4K0lnNh1imYiRh6-sbU
    }

    @Test
    public void loginWrongEmailOkhttp() throws IOException {
        AuthRequestDTO auth = AuthRequestDTO.builder().username("dusm5gmail.com").password("Dudu12345@").build();
        RequestBody body = RequestBody.create(gson.toJson(auth),JSON);
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/login/usernamepassword")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(), 401);
        ErrorDTO errorDTO = gson.fromJson(response.body().string(), ErrorDTO.class);
        Assert.assertEquals(errorDTO.getStatus(), 401);
        Assert.assertEquals(errorDTO.getMessage(), "Login or Password incorrect");
        Assert.assertEquals(errorDTO.getPath(), "/v1/user/login/usernamepassword");
    }

    @Test
    public void loginWrongPasswordOkhttp() throws IOException {
        AuthRequestDTO auth = AuthRequestDTO.builder().username("dusm5@gmail.com").password("Dudu12345").build();
        RequestBody body = RequestBody.create(gson.toJson(auth),JSON);
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/login/usernamepassword")
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(), 401);
       ErrorDTO errorDTO = gson.fromJson(response.body().string(), ErrorDTO.class);
       Assert.assertEquals(errorDTO.getStatus(), 401);
       Assert.assertEquals(errorDTO.getMessage(), "Login or Password incorrect");
       Assert.assertEquals(errorDTO.getError(), "Unauthorized");
    }

    @Test
    public void loginUnregisteredUser() throws IOException {
        AuthRequestDTO auth = AuthRequestDTO.builder().username("dhfg@gmail.com").password("Dudu12345@").build();
        RequestBody body = RequestBody.create(gson.toJson(auth), JSON);
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/login/usernamepassword")
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(), 401);
        ErrorDTO errorDTO = gson.fromJson(response.body().string(), ErrorDTO.class);
        Assert.assertEquals(errorDTO.getStatus(), 401);
        Assert.assertEquals(errorDTO.getMessage(), "Login or Password incorrect");
        Assert.assertEquals(errorDTO.getError(), "Unauthorized");
    }
}
