//1-Pacote
package petstore;

//2-Bibliotecas

import javafx.scene.layout.Priority;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;

//3-Classe
public class Pet {
    //3.1-Atributos
    String uri = "https://petstore.swagger.io/v2/pet"; //endereço da entidade Pet






    //3.2-Métodos e Funções
    public String lerJson(String caminhoJson) throws IOException {
       return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    //Incluir - Create - Post
    @Test(priority = 1) //Identifica o método ou função como um teste para o TestNG
    public void incluirPet() throws IOException {
        String jsonBody = lerJson("db/pet1.json");

        //Sintaxe Gherkin
        //Dado - Quando - Então
        //Given - When - Then
        given()
                .contentType("application/json") //comum em API REST - antigas era"text/xml"
                .log().all()
                .body(jsonBody)
        .when()
                .post(uri)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Amy"))
                .body("status", is("available"))
                .body("category.name", is("dog"))//checagem dentro de categoria
                .body("tags.name",contains("Curso REST-assured"))//Checagem dentro de container
        ;
    }
    @Test(priority = 2)
    public void consultarPet(){
        String petId = "1987021822";
        String token =
        given()
                .contentType("application/json")
                .log().all()
        .when()
                .get(uri + "/" + petId)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Amy"))
                .body("category.name", is("dog"))
                .body("tags.name", contains("Curso REST-assured"))
                .body("status", is ("available"))
        .extract()
                .path("category.name")

        ;
        System.out.println("0 token é "+ token);
    }
    @Test(priority = 3)
    public void alterarPet() throws IOException {
        String jsonBody = lerJson("db/pet2.json");
        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
        .when()
                .put(uri)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Amy"))
                .body("status",is("sold"))
        ;
    }
    @Test(priority = 4)
    public void excluirPet(){
        String petId = "1987021822";
        given()
                .contentType("application/json")
                .log().all()
        .when()
                .delete(uri + "/" + petId)
        .then()
                .log().all()
                .statusCode(200)
                .body("code", is (200))
                .body("type", is("unknown"))
                .body("message", is(petId))
        ;
    }
    @Test(priority = 5)
    public void consultarPetporStatus(){
        String status = "available";
        given()
                .contentType("application/json")
                .log().all()
        .when()
                .get(uri = "https://petstore.swagger.io/v2/pet/findByStatus?status="+ status)
        .then()
                .log().all()
                .statusCode(200)
                .body("name[]", everyItem(equalTo("Amy")))
        ;
    }

}
