package testaraujo;

import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasItem;

public class Pet{
    String uri = "https://petstore.swagger.io/v2/pet";

    public String lerJson(String caminhoJson) throws IOException {


        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }
    @Test(priority = 1 )
    public void criarPet() throws IOException {
        String jsonBody = lerJson("src/data/pet1.json");

        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
                .when()
                .post(uri)
                .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Thor"))
                .body("status", is("available"))
                .body("category.name", is("dog"))
                .body("tags.name", contains("araujo"))
        ;

    }

    @Test(priority = 2 )
    public void consultarPetPorId(){
        String petId = "06111993";

        given()
                .contentType("application/json")
                .log().all()
                .when()
                .get(uri + "/" + petId)
                .then()
                .log().all()
                .statusCode(200)

        ;
    }
    @Test
    public void atualizarPet() throws IOException {
        String jsonBody = lerJson("src/data/petAtualizado.json");

        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
        .when()
                .put(uri)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Wesley Teste Araujo"))
                .body("status", is("sold"));
    }


    @Test(priority=0)
    public void consultarStatus(){
        String uri = "https://petstore.swagger.io/v2/pet";
        String status = "pending";

        given()
                .contentType("application/json")
                .log().all()
                .when()
                .get(uri + "/findByStatus?status=" + status)
                .then()
                .log().all()
                .statusCode(200);

    }

}
