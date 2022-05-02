package spec;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.asynchttpclient.RequestBuilder;

/**
 * Заготовка спецификаций. Позволяет не прописывать каждый раз URL и тип контента
  */

public class Specification {
    public static RequestSpecification requestSpec(String url){ // В метод передаем базовый URL
        return new RequestSpecBuilder()
                .setBaseUri(url)                   // Базовый URL
                .setContentType(ContentType.JSON)  // Тип контента
                .build();                          // Метод для сборки спецификации
    }

    /**
     * Заготовка спецификации для ответа ОК 200.
     */
    public static ResponseSpecification responseSpecOK200(){
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();
    }

    /**
     * Заготовка спецификации для ответа 400.
     */
    public static ResponseSpecification responseError(){
        return new ResponseSpecBuilder()
                .expectStatusCode(400 )
                .build();
    }

    /**
     * Уникальная спецификация в параметр которой передаётся статус ответа.
     */
    public static ResponseSpecification responseSpecUnique(int status){
        return new ResponseSpecBuilder()
                .expectStatusCode(status)
                .build();
    }
    /**
     * Метод для вызова спецификаций
     */
    public static void InstallSpecification(RequestSpecification request,ResponseSpecification response){
        RestAssured.requestSpecification = request;   // Библиотека Rest Assured для запроса
        RestAssured.responseSpecification = response; // Библиотека Rest Assured для ответа
    }
}
