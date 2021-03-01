import exchangerate.ExchangeRate;
import io.restassured.response.ValidatableResponse;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Test;
import utils.Schema;

import java.time.LocalDate;
import java.time.Period;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class ExchangeRateTest {

    private static final int STATUS_CODE_OK = HttpStatus.OK_200;

    @Test
    public void testLatestExchangeRate() {
        String pathParam = "latest";
        String schemaPath = "schemas/getExchangeRate";
        LocalDate date = LocalDate.now();
        String currentDate = date.toString();
        String respBody = "";

        ValidatableResponse validatableResponse = ExchangeRate.getExchangeRate(pathParam, STATUS_CODE_OK);

        respBody = validatableResponse.extract().response().jsonPath().getString("date");

        assertEquals(STATUS_CODE_OK, validatableResponse.extract().response().getStatusCode());
        assertEquals(currentDate, respBody);
        Schema.schemaValidator(validatableResponse, schemaPath);
    }

    @Test
    public void testSpecificExchangeDate() {
        LocalDate date = LocalDate.now().minus(Period.ofDays((new Random().nextInt(365 * 70))));
        String pathParam = date.toString();
        String schemaPath = "schemas/getExchangeRate";
        System.out.println(pathParam);



        ValidatableResponse validatableResponse = ExchangeRate.getExchangeRate(pathParam, STATUS_CODE_OK);

    }

    @Test
    public void testNonRegisteredExchangeDate() {
        LocalDate date = LocalDate.now().minus(Period.ofDays((new Random().nextInt(365 * 70))));
        String pathParam = date.toString();
        int statusCode = HttpStatus.BAD_REQUEST_400;
        String respBody = "";
        String errormessage = "There is no data for dates older then 1999-01-04.";

        ValidatableResponse validatableResponse = ExchangeRate.getExchangeRate(pathParam, statusCode);
        respBody = validatableResponse.extract().response().jsonPath().getString("error");

        assertEquals(statusCode, validatableResponse.extract().response().getStatusCode());
        assertEquals(errormessage, respBody);
    }
}
