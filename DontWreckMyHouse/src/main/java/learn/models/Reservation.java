package learn.models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Reservation {

    private Host host;

    private int res_id;

    private LocalDate startDate;

    private LocalDate endDate;

    private Guest guest;

    private BigDecimal total;

    public Reservation(Host host, int res_id, LocalDate startDate, LocalDate endDate, Guest guest, BigDecimal total) {
        this.host = host;
        this.res_id = res_id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.guest = guest;
        this.total = total;
    }

    public Reservation() {

    }


    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public int getRes_id() {
        return res_id;
    }

    public void setRes_id(int res_id) {
        this.res_id = res_id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
