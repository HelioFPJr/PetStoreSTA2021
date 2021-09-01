// 1. Pacote
package petstore;
//2. Bibliotecas
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Data;//Importar

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
//3. Classes
public class User {
    //3.1. Atributos
    String uri = "https://petstore.swagger.io/v2/user";
    Data data;// declarar

    //3.2-Métodos e Funções
    @BeforeMethod
    public void setup(){
        data = new Data();//Instanciar
    }

    @Test
    public void incluirUser() throws IOException {
        String jsonBody = data.lerJson("db/user1.json");
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
        System.out.println("O userID é "+ userId);
    }
}
