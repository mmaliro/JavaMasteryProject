package learn.models;

import java.math.BigDecimal;
import java.util.UUID;

public class Host {
    private UUID host_id;

    private String lastName;

    private String hostEmail;

    private String phone;

    private String address;

    private String city;

    private String state;

    private String postal_code;

    private BigDecimal standardRate;

    private BigDecimal weekendRate;

    public Host(UUID host_id, String lastName, String email, String phone, String address, String city, String state, String postal_code, BigDecimal standardRate, BigDecimal weekendRate) {
        this.host_id = host_id;
        this.lastName = lastName;
        this.hostEmail = email;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.state = state;
        this.postal_code = postal_code;
        this.standardRate = standardRate;
        this.weekendRate = weekendRate;
    }

    public UUID getHost_id() {
        return host_id;
    }

    public void setHost_id(UUID host_id) {
        this.host_id = host_id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getHostEmail() {
        return hostEmail;
    }

    public void setHostEmail(String email) {
        this.hostEmail = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public BigDecimal getStandardRate() {
        return standardRate;
    }

    public void setStandardRate(BigDecimal standardRate) {
        this.standardRate = standardRate;
    }

    public BigDecimal getWeekendRate() {
        return weekendRate;
    }

    public void setWeekendRate(BigDecimal weekendRate) {
        this.weekendRate = weekendRate;
    }
}
