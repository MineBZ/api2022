package get_requests;

import base_urls.GoRestBaseUrl;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertTrue;

public class Get11 extends GoRestBaseUrl {
    /*
        Given
            https://gorest.co.in/public/v1/users
        When
            User send GET Request
        Then
            The value of "pagination limit" is 10
        And
            The "current link" should be "https://gorest.co.in/public/v1/users?page=1"
        And
            The number of users should  be 10
        And
            We have at least one "active" status
        And
          Diptendu Deshpande Ret., "Anagh Devar", "Malti Acharya" are among the users
        And
            The male users are more than or equals to female users
     */

    @Test
    public void get01(){
        //1. Step: Set the Url

        spec.pathParam("first", "users");

        //2. Set the Expected Data

        //3. Step: Send the Request get the Response
        Response response = given().spec(spec).when().get("/{first}");
        response.prettyPrint();

        //4. Step: Do Assertion

        response.
                then().
                assertThat().
                statusCode(200).
                body("meta.pagination.limit",equalTo(10),
                        "meta.pagination.links.current", equalTo("https://gorest.co.in/public/v1/users?page=1"),
                        "data.id", hasSize(10),
                        "data.status", hasItem("active"),
                        "data.name", hasItems("Diptendu Deshpande Ret.", "Sheela Jain", "Malti Acharya"));
        JsonPath json = response.jsonPath();
        List<String> genders=json.getList("data.gender");
        System.out.println(genders);
        int numberOfFemale=0;
        for (String w:genders){
            if (w.equalsIgnoreCase("male")){
                numberOfFemale++;
            }

        }
        System.out.println(numberOfFemale);
        assertTrue(numberOfFemale>=genders.size()-numberOfFemale);

        //2. Yol:  Tüm bayan ve bayları ayrı ayrı Groovy ile çekelim.
        List<String> femaleList = json.getList("data.findAll{it.gender=='female'}.gender");
        System.out.println(femaleList);

        List<String> maleList = json.getList("data.findAll{it.gender=='male'}.gender");
        System.out.println(maleList);

        assertTrue(femaleList.size()>=maleList.size());

    }
}

