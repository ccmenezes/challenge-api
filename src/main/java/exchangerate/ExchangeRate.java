package exchangerate;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import specs.DefaultRequest;
import specs.DefaultResponse;

import static io.restassured.RestAssured.given;

public class ExchangeRate {
    private static final String URI = "https://api.exchangeratesapi.io/api/";

    public static ValidatableResponse getExchangeRate(String pathParam, int statusCode) {
        String url = URI + pathParam;
        ContentType contentType = ContentType.JSON;


        RequestSpecification requestSpecification = DefaultRequest.requestSpecification(url);

        ResponseSpecification responseSpecification = DefaultResponse.responseSpecification(statusCode, contentType);

        return given(requestSpecification, responseSpecification)
                .get()
                .then();
    }

}
