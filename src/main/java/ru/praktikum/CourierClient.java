package ru.praktikum;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;

public class CourierClient extends RestAssuredClient{

    public static final String COURIER_PATH = "/api/v1/courier/";

    @Step("Запрос на создание курьера")
    public ValidatableResponse createResponse(Courier courier){
        return  given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then();
    }

    @Step("Зпрос на авторизацию курьера")
    public ValidatableResponse loginResponse (CourierCredentials courierCredentials){
        return given()
                .spec(getBaseSpec())
                .body(courierCredentials)
                .when()
                .post(COURIER_PATH + "login")
                .then();
    }

    @Step("Удаляем курьера")
    public boolean delete (int courierId){
        return given()
                .spec(getBaseSpec())
                .when()
                .delete(COURIER_PATH + courierId)
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .path("ok");
    }
}