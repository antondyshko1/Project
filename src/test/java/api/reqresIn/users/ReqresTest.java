package reqresIn.users;

import reqresIn.Colors.ColorsData;
import org.junit.Assert;
import org.junit.Test;
import reqresIn.registration.Register;
import reqresIn.registration.SuccessReg;
import reqresIn.registration.UnSuccessReg;
import spec.Specification;
import reqresIn.users.UserData;
import reqresIn.users.UserTime;
import reqresIn.users.UserTimeResponse;

import java.time.Clock;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;


public class ReqresTest {
     private final static String URL = "https://reqres.in/";

    @Test
    public void checkAvatarAndIdTest(){
        Specification.InstallSpecification(Specification.requestSpec(URL), Specification.responseSpecOK200());//Вызываем метод через имя класса, так как он static, для установки спецификаций. В аргумент метода передаем методы кторые заготовили для запроса и ответа в классе Specification
        List<UserData> users = given()
                .when()
                .get("api/users?page=2")
                .then().log().all()
                .extract().body().jsonPath().getList("data", UserData.class);
       users.forEach(x-> Assert.assertTrue(x.getAvatar().contains(x.getId().toString())));//Проверяем, что аватар содержит id, forEach переберает каждый элемент списка users, x локальная переменная
       Assert.assertTrue(users.stream().allMatch(x->x.getEmail().endsWith("reqres.in")));//Проверяем, что все email из списка users оканчиваются на "reqres.in" allMatch() означает, что если все проверки пройдут вернется true.

        // Другой способ проверки с использование stream() и созданием списков отдельных перемнных.
        List<String> avatars = users.stream().map(UserData::getAvatar).collect(Collectors.toList());// Создаем список avatars, который содержит только Avatar. Достаем из списка users только аватар с помощью map() и добавляем их в список avatars

        List<String> ids = users.stream().map(x-> x.getId().toString()).collect(Collectors.toList()); // Создаем список  ids который содержит только id. Используем ляьбду x-> т.к. id это Integer
        for (int i = 0; i < avatars.size(); i++){// Создаем цикл для проверки
            Assert.assertTrue(avatars.get(i).contains(ids.get(i)));// С помощью цикла проверяем, что avatars содержет ids
        }

    }

    /**
     * Тест проверки успешной регистрации на сайте reqres.in.
     */
    @Test
    public void successRegTest(){
        Specification.InstallSpecification(Specification.requestSpec(URL),Specification.responseSpecOK200());
      Integer id = 4;                     // Переменная с ожидаемым ответом
      String token = "QpwL5tke4Pnpja7X4"; // Переменная с ожидаемым ответом
      Register user = new Register("eve.holt@reqres.in", "pistol");// Заготовка с телом запроса. Создаем экземпляр user класса Register  и в параметре передаем тело( с помощью созданного конструктора).
      SuccessReg successReg = given()     // Вызываем метод given() для pojo класса SuccessReg, чтобы сохранить туда ответ
            .body(user)                                   //Указываем тело запроса
            .when()
            .post("api/register")                      // Указываем метод и куда отправить запрос
            .then().log().all()                          // Указываем куда вывести : в логи, вывести всё
            .extract().as(SuccessReg.class);             //Указываем, куда извлекаем ответ.
        Assert.assertNotNull(successReg.getId());        // Проверяем, что результ не пустой
        Assert.assertNotNull(successReg.getToken());     // Проверяем, что результ не пустой

        Assert.assertEquals(id,successReg.getId());      // Сравниваем, что ожидаемый id соответствует id из ответа.
        Assert.assertEquals(token,successReg.getToken());// Сравниваем, что ожидаемый token соответствует token из ответа.

    }

    /**
     * Тест для проверки неуспешной регистрации на сайте reqres.in.
     */
    @Test
    public void unSuccessRegTest(){
        Specification.InstallSpecification(Specification.requestSpec(URL),Specification.responseError());
        Register user = new Register("sydney@fife", "");// Заготовка с телом запроса. Создаем экземпляр user класса Register  и в параметре передаем тело( с помощью созданного конструктора).
        UnSuccessReg unSuccessReg = given()       // Вызываем метод given() для pojo класса UnSuccessReg, чтобы сохранить туда ответ
                .body(user)
                .when()
                .post("api/register")
                .then().log().all()
                .extract().as(UnSuccessReg.class);
        Assert.assertEquals("Missing password", unSuccessReg.getError());// Проверяем, что ответ содержет ошибку
    }

    /**
     * Проверка того, что в ответе все года Year приходят отсортированные.
     */
    @Test
    public void sortedYearsTest(){
        Specification.InstallSpecification(Specification.requestSpec(URL), Specification.responseSpecOK200());
        List<ColorsData> colors = given()                                          //Создали список colors
                .when()
                .get("api/unknown")
                .then().log().all()
                .extract().body().jsonPath().getList("data",ColorsData.class);// Извлекли ответ. Указали путь к json 'data' и в pojo класс.
        List<Integer> years = colors.stream().map(ColorsData::getYear).collect(Collectors.toList()); // Создали список years и с помощью stream().map() извлекли в него все Year.
        List<Integer> sortedYears = years.stream().sorted().collect(Collectors.toList()); // Создали отсортированный спиок sortedYears из списка years.
        Assert.assertEquals(sortedYears,years); // Сравниваем ожидаемый и фактический результат.
    }
    /**
     * Тест для удаления пользователя.
     */
    @Test
    public void deleteUserTest(){
        Specification.InstallSpecification(Specification.requestSpec(URL),Specification.responseSpecUnique(204));
        given()
                .when()
                .delete("api/users/2")
                .then().log().all();
    }
    /**
     * Тест для сравнения времени на сревере и компьютере. Метод PUT.
     */
    @Test
    public void timeTest(){
        Specification.InstallSpecification(Specification.requestSpec(URL),Specification.responseSpecUnique(200));
        UserTime user = new UserTime("morpheus","zion resident"); // Создали пользователя для отправки на сервер и передали параметры с помощью конструктора в классе UserTime.
        UserTimeResponse response = given()// Создаем объект response, чтобы сохранить ответ в класс UserTimeResponse;
                .body(user)// Передали тело запроса user.
                .when()
                .put("api/users/2")
                .then().log().all()
                .extract().as(UserTimeResponse.class);// Извлекли ответ в класс.
        String regex = "(.{5})$";// Регулярное выражение, чтобы взять последние 5 символов с конца из времени.
        String currentTime = Clock.systemUTC().instant().toString().replaceAll(regex,"");
        System.out.println(currentTime);
        Assert.assertEquals(currentTime,response.getUpdatedAt().replaceAll(regex,""));
        System.out.println(response.getUpdatedAt().replaceAll(regex,""));
    }
}
