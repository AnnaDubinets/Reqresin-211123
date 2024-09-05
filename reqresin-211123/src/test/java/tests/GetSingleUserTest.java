package tests;

import dto.UserDataResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class GetSingleUserTest {

    @Test
    public void getValidUserTest(){
        int requestedId = 2;
        UserDataResponse user =
        given().baseUri("https://reqres.in")
                .when().log().all()
                .get("/api/users/" + requestedId)
                .then().log().all().statusCode(200).extract().body()
                .jsonPath().getObject("data", UserDataResponse.class);

        assertEquals(requestedId, user.getId());

        //Check that email is NOT empty

        assertFalse(user.getEmail().isEmpty());

        //Check that email ends with "@reqres.in"

        assertTrue(user.getEmail().endsWith("@reqres.in"));

        //Check that avatar ends with "id-value" + "-image.jpg"

        assertTrue(user.getAvatar().endsWith(requestedId + "-image.jpg"));


    }
}
