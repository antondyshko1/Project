package api.booker;

public class BookingData extends BookerPojo{
    public BookingData(String firstname, String lastname, Integer totalprice, Boolean depositpaid, String additionalneeds) {
        super(firstname, lastname, totalprice, depositpaid, additionalneeds);
    }
    private String checkin;
    private String checkout;
}
