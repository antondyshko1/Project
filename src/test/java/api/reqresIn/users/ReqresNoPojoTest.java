package reqresIn.users;

import spec.Specification;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

/**
 * Проверка api без использования pojo класса
 * 1. Получить список пользователей со второй страници сайта https://reqres.in/
 * 2. Убедиться, что id пользователя содержаться в их avatar
 * 3. Убедиться, что email пользователя имеет окончание reqres.in;
 */

public class ReqresNoPojoTest {
    private final static String URL = "https://reqres.in/";
    @Test
    public void CheckAvatarNoPojoTest(){
        Specification.InstallSpecification(Specification.requestSpec(URL),Specification.responseSpecOK200());
        Response response = given()// Создаём экземпляр интерфейса Response.
                .when()
                .get("api/users?page=2")
                .then().log().all()
                .body("page", equalTo(2)) // Проверяем, что page содержит 2, импортируем библиотеку org.hamcrest.CoreMatchers.equalTo
                .body("data.id", notNullValue()) // Проверяем, что data.id не пустое. Если использовать equalTo(значение), то выдаст ошибку так как ответ содержит несколько id
                .body("data.email",notNullValue())
                .body("data.first_name",notNullValue())
                .body("data.last_name",notNullValue())
                .body("data.avatar",notNullValue())
                .extract().response(); // Извлекаем ответ в response
        JsonPath jsonPath = response.jsonPath(); // Преобразовываем response в json.
        List<String> emails = jsonPath.get("data.email"); // Создаем спиок emails и извлекаем в него c помощью jsonPath всё, что указано под эти путём "data.email"
        List<Integer> ids = jsonPath.get("data.id");// Создали список ids.
        List<String> avatars = jsonPath.get("data.avatar");// Создали список avatars.
        for(int i = 0; i > avatars.size(); i++){
            Assert.assertTrue(avatars.get(i).contains(ids.get(i).toString()));// Сравниеваем, что avatars содержит ids, приобразовываем ids  в строку.
        }
        Assert.assertTrue(emails.stream().allMatch(x->x.endsWith("@reqres.in")));// Проверяем, что emails оканчиваются на @reqres.in allMatch означает все проверки, если хотябы одна не пройдёт, то assert не выполнится
    }

    /**
     * Тест на создание пользователя в системе  используя метод POST
     * Успешная регистрация
     */
    @Test
    public void successUserRegTestNoPojo(){
        Specification.InstallSpecification(Specification.requestSpec(URL),Specification.responseSpecOK200());
        Map<String,String> user = new HashMap<>();// Создали хеш карту user для отправки запроса в формате ключ : значение
        user.put("email", "eve.holt@reqres.in"); // Заполняем хэш карту используя метод put(ключ,значение).
        user.put("password","pistol");
        Response response = given()
                .body(user)//Передаём тело запроса
                .when()
                .post("api/register")
                .then().log().all()
                .extract().response(); //Извлекаем ответ в response
        JsonPath jsonPath = response.jsonPath();// Преобразовываем response в json
        int id = jsonPath.get("id"); // Получаем значение id из jsonPath
        String token = jsonPath.get("token");
        Assert.assertEquals(4, id); // Сравниваем результат
        Assert.assertEquals("QpwL5tke4Pnpja7X4", token);
    }

    /**
     * Тест на неуспешную регистрацию
     */
    @Test
    public void unSuccessUserRegTest(){
        Specification.InstallSpecification(Specification.requestSpec(URL),Specification.responseSpecUnique(400));
        Map<String,String> user = new HashMap<>();
        user.put("email","sydney@fife");
        given()
                .body(user)// Передаем тело запрос.
                .when()
                .post("api/register")
                .then().log().all()
                .body("error", equalTo("Missing password")); //Сравниваем полученый ответ с ожидаемым результатом

    }
}
