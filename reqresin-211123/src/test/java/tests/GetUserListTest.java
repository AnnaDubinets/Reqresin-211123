package tests;

import dto.UserDataResponse;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GetUserListTest {

    @Test
    public void getUserListPage2(){

        List<UserDataResponse> users = given().baseUri("https://reqres.in")
                .when().log().all()
                .get("/api/users?page=2")
                .then().log().all().statusCode(200).extract().body()
                .jsonPath().getList("data", UserDataResponse.class);

        //Check that 6 items in the users list
        assertEquals(6, users.size());

        //Check that all IDs are positive values
        //Check that all email ends with "@reqres.in"
        //Check that avatar ends with "id-value" + "-image.jpg"

        for(UserDataResponse user: users) {
            assertTrue(user.getId()>0);
            assertTrue(user.getEmail().endsWith("@reqres.in"));
            assertTrue(user.getAvatar().endsWith(user.getId() + "-image.jpg"));
        }




    }
}
