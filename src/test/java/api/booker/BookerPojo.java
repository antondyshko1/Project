package api.booker;

public class BookerPojo {
    private String firstname;
    private String lastname;
    private Integer totalprice;
    private Boolean depositpaid;
    private String additionalneeds;

    public BookerPojo(String firstname, String lastname, Integer totalprice, Boolean depositpaid, String additionalneeds) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.totalprice = totalprice;
        this.depositpaid = depositpaid;
        this.additionalneeds = additionalneeds;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public Integer getTotalprice() {
        return totalprice;
    }

    public Boolean getDepositpaid() {
        return depositpaid;
    }

    public String getAdditionalneeds() {
        return additionalneeds;
    }
}
