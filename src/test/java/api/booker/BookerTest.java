package api.booker;

import api.spec.Specification;
import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class BookerTest {
    final String URL = "https://restful-booker.herokuapp.com/booking/";
    @Test
    public void getBookingId(){
        Specification.InstallSpecification(Specification.requestSpec(URL),Specification.responseSpecUnique(200));
        Response response = given()
                .when()
                .get("5")
                .then().log().all()
                .body("firstname",equalTo("Mark"))
                .body("bookingdates.checkin",equalTo("2017-05-26"))
                .extract().response();

    }
}
