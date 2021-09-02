//1. Pacote
package datadriven;
//2. Bibliotecas
import org.json.JSONObject;
import org.testng.annotations.*;
import utils.Data;
import utils.Log;

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
    Log log; //objeto que representa a classe utils.log
    int contador = 0;//Ajuda a contar o numero de linhas executadas
    double soma = 0;//Soma os valores do registro senha em user.csv

    //3.2. Métodos e Funções
    @DataProvider//Provedor de dados para o teste
    public Iterator<Object[]> provider() throws IOException {
        List<Object[]> testCases = new ArrayList<>();
        //List<String[]> testCases = new ArrayList<>();
        String[]testCase;
        String linha;
        BufferedReader bufferedReader = new BufferedReader(new FileReader("db/usersPairwise.csv"));
        while((linha = bufferedReader.readLine())!= null){
            testCase = linha.split(",");
            testCases.add(testCase);
        }

        return testCases.iterator();
    }
    @BeforeClass
    public void setup() throws IOException {
        data = new Data();
        log = new Log();
        int contador = 0;
        log.iniciarLog();//Cria e escreve a linha de cabeçalho

    }
    @AfterClass
    public void tearDown(){
        System.out.println("O total de linhas é "+ contador);
        System.out.println("A soma total das senhas é "+ soma);
    }
    @Test(dataProvider = "provider")//Método de teste
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
        System.out.println("O userID é "+ userId);
        contador ++;
        System.out.println("Essa é a linha n° "+ contador );
        soma = soma + Double.parseDouble(password);
    }
}
