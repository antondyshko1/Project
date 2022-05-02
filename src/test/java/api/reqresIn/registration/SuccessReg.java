package api.reqresIn.registration;

/**
 * Pojo класс для сохранения ответа.
 */
public class SuccessReg {
    private Integer id;
    private String token;

    /**
     * Конструктор для класса SuccessReg
     */
    public SuccessReg(Integer id, String token) {
        this.id = id;
        this.token = token;
    }

    /**
     * Геттер для переменных.
     */
    public Integer getId() {
        return id;
    }

    public String getToken() {
        return token;
    }
}
