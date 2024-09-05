package tests;

import dto.ErrorResponse;
import dto.RegisterRequest;
import dto.SuccessfulRegisterResponse;
import dto.UserDataResponse;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class RegisterSuccessfulTest {

    @Test
    public void successRegistration() {
        RegisterRequest requestBody = new RegisterRequest("eve.holt@reqres.in", "pistol");
        SuccessfulRegisterResponse response =
        given().baseUri("https://reqres.in")
                .when().log().all()
                .post("/api/register")
                .then().log().all().statusCode(200).extract().body()
                .jsonPath().getObject("", SuccessfulRegisterResponse.class);

        //Check that id and token are not empty

        assertNotNull(response.getId());
        assertFalse(response.getToken().isEmpty());
    }

    @Test
    public void registerWithoutPassword(){
        //email, without password

        RegisterRequest requestBody = new RegisterRequest("eve.holt@reqres.in", "");

        //"error" contains "Missing password"


        ErrorResponse response = given().baseUri("https://reqres.in")
                .body(requestBody)
                .when().log().all()
                .contentType(ContentType.JSON)
                .post("/api/register")
                .then().log().all().statusCode(400).extract().body()
                .jsonPath().getObject("", ErrorResponse.class);

        assertEquals("Missing password", response.getError());
    }

    @Test
    public void registerWithoutEmail() {
        RegisterRequest requestBody = new RegisterRequest("", "pistol");
        ErrorResponse response = given().baseUri("https://reqres.in")
                .body(requestBody)
                .when().log().all()
                .contentType(ContentType.JSON)
                .post("/api/register")
                .then().log().all().statusCode(400).extract().body()
                .jsonPath().getObject("", ErrorResponse.class);
        assertEquals("Missing email or username", response.getError());
    }

}
