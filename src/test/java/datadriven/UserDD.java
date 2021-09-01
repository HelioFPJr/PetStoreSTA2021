//1. Pacote
package datadriven;
//2. Bibliotecas
import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.Data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
// 3. Classe
public class UserDD {
    //3.1. Atributos
    String uri = "https://petstore.swagger.io/v2/user";
    Data data; //Objetos que representa a classe utils.Data

    //3.2. M�todos e Fun��es
    @DataProvider//Provedor de dados para o teste
    public Iterator<Object[]> provider() throws IOException {
        List<Object[]> testCases = new ArrayList<>();
        //List<String[]> testCases = new ArrayList<>();
        String[]testCase;
        String linha;
        BufferedReader bufferedReader = new BufferedReader(new FileReader("db/users.csv"));
        while((linha = bufferedReader.readLine())!= null){
            testCase = linha.split(",");
            testCases.add(testCase);
        }

        return testCases.iterator();
    }

    @BeforeMethod
    public void setup(){
        data = new Data();
    }
    @Test(dataProvider = "provider")//M�todo de teste
    public void incluirUser(
            String id,
            String username,
            String firstName,
            String lastName,
            String email,
            String password,
            String phone,
            String userStatus){

        String jsonBody = new JSONObject()
                .put("id", id)
                .put("username", username)
                .put("firstName", firstName)
                .put("lastName", lastName)
                .put("email", email)
                .put("password", password)
                .put("phone", phone)
                .put("userStatus", userStatus)
                .toString();

        String userId =

                given()
                        .contentType("application/json")
                        .log().all()
                        .body(jsonBody)
                .when()
                        .post(uri)
                .then()
                        .log().all()
                        .statusCode(200)
                        //.body("name",is ("Helio"))
                        .body("code",is(200))
                        .body("type",is("unknown"))
                .extract()
                        .path("message")

                ;
        System.out.println("O userID � "+ userId);
    }
}
