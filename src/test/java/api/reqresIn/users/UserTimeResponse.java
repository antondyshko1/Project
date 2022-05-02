package api.reqresIn.users;

/**
 * Pojo класс для ответа метода PUT
 */
public class UserTimeResponse extends UserTime {
    private String updatedAt;
    public UserTimeResponse(String name, String job, String updatedAt) { // Унаследовали родительский конструктор и добавили в него ещё один параметр.
        super(name, job);
        this.updatedAt = updatedAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}
