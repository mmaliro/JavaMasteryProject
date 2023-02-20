package learn.models;

public class Guest {
    private int guest_id;

    private String firstName;

    private String lastName;

    private String guestEmail;

    private String phone;

    private String state;

    public Guest(int guest_id, String firstName, String lastName, String guestEmail, String phone, String state) {
        this.guest_id = guest_id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.guestEmail = guestEmail;
        this.phone = phone;
        this.state = state;
    }

    public Guest() {

    }


    public int getGuest_id() {
        return guest_id;
    }

    public void setGuest_id(int guest_id) {
        this.guest_id = guest_id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGuestEmail() {
        return guestEmail;
    }

    public void setGuestEmail(String email) {
        this.guestEmail = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
