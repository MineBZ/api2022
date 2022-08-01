package get_requests;

import base_urls.HerOkuAppBaseUrl;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Test;
import org.testng.asserts.SoftAssert;

import static io.restassured.RestAssured.given;
import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.CoreMatchers.equalTo;



public class Get06 extends HerOkuAppBaseUrl {
    /*Given
    https://restful-booker.herokuapp.com/booking/5
    When
    User send a GET request to the URL
    Then
    HTTP Status Code should be 200
    And
    Response content type is “application/json”
    And
    Response body should be like;
    {
        "firstname": "Susan",
            "lastname": "Smith",
            "totalprice": 474,
            "depositpaid": true,
            "bookingdates": {
            "checkin": "2017-11-29",
                "checkout": "2020-03-10"
    },
        "additionalneeds": "Breakfast"
    }

     */

    @Test
    public void get01() {

        //1. Step: Set the Url

        spec.pathParams("first", "booking", "second", 5);
        //2. Set the expected data

        //3. Step: Send the request and get the response
        Response response = given().spec(spec).when().get("/{first}/{second}");
        response.prettyPrint();

        //4. Step: Do Assertion

        //1.yol
        response.
                then().
                assertThat().statusCode(200).
                contentType(ContentType.JSON).
                body("firstname", equalTo("Susan"),
                        "lastname", equalTo("Smith"),
                        "totalprice", equalTo(474),
                        "depositpaid", equalTo(true),
                        "bookingdates.checkin", equalTo("2017-11-29"),
                        "bookingdates.checkout", equalTo("2020-03-10"));

        //2. Yol: JsonPath Class kullanılır

        JsonPath json=response.jsonPath();
        assertEquals("Susan", json.getString("firstname"));
        assertEquals("Smith", json.getString("lastname"));
        assertEquals(474, json.getInt("totalprice"));
        assertEquals(true, json.getBoolean("depositpaid"));
        assertEquals("2017-11-29", json.getString("bookingdates.checkin"));
        assertEquals("2020-03-10",json.getString("bookingdates.checkout"));



        //3. Yol: Soft Assertion
        //Soft Assertion için 3 adım izlenir;

        //1) SoftAssert Objesi oluşturulur.
        SoftAssert softAssert = new SoftAssert();

        //2) Obje aracılığı ile assert yapılır.

        softAssert.assertEquals(json.getString("firstname"), "Susan","firstname uyuşmadı");
        softAssert.assertEquals(json.getString("lastname"),"Smith","lastname uyuşmadı");
        softAssert.assertEquals(json.getInt("totalprice"),474,"totalprice uyuşmadı");
        softAssert.assertEquals(json.getBoolean("depositpaid"),true,"depositpaid uyuşmadı");
        softAssert.assertEquals(json.getString("bookingdates.checkin"),"2017-11-29","checkin uyuşmadı");
        softAssert.assertEquals(json.getString("bookingdates.checkout"),"2020-03-10","checkout uyuşmadı");

        //3) assertAll() methodu kullanılır. Aksi taktirde kod her zaman pass olur.
        softAssert.assertAll();


        //3) assertAll() methodu kullanılır. Aksi taktirde kod her zaman pass olur.
        softAssert.assertAll();



    }
}



