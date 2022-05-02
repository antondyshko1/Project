package api.reqresIn.registration;

/**
 * Pojo класс регистрации для передачи параметров в тело запроса.
 */
public class Register {
    private String email;
    private String password;

    /**
     * Конструктор для класса Register.
     */
    public Register(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
