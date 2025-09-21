//"application/json;charset=utf-8"
 //"https://contactapp-telran-backend.herokuapp.com/v1/user/registration/usernamepassword"

package okhttp;


import com.google.gson.Gson;
import dto.AuthRequestDTO;
import dto.AuthResponseDTO;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class RegistrationTestsOkhttp {
Gson gson = new Gson();
public static final MediaType JSON = MediaType.get("application/json;charset=utf-8");
OkHttpClient client = new OkHttpClient();

@Test
    public void registrationSuccess() throws IOException {
    int i = (int) (System.currentTimeMillis()/1000)%3600;
    AuthRequestDTO auth = AuthRequestDTO.builder()
            .username("dusm" + i + "@gmail.com")
            .password("Dudu12345@")
            .build();
    RequestBody body = RequestBody.create(gson.toJson(auth), JSON);
    Request request = new Request.Builder()
            .url("https://contactapp-telran-backend.herokuapp.com/v1/user/registration/usernamepassword")
            .post(body)
            .build();

    Response response = client.newCall(request).execute();
    Assert.assertTrue(response.isSuccessful());
    Assert.assertEquals(response.code(), 200);

    AuthResponseDTO responseDTO = gson.fromJson(response.body().string(), AuthResponseDTO.class);
    System.out.println(responseDTO.getToken());
}

}