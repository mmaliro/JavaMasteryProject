package learn.domain;

import learn.data.DataException;
import learn.data.GuestRepository;
import learn.data.HostRepository;
import learn.data.ReservationRepository;
import learn.models.Guest;
import learn.models.Host;
import learn.models.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {

    private final GuestRepository guestRepository;
    private final HostRepository hostRepository;
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(GuestRepository guestRepository, HostRepository hostRepository, ReservationRepository reservationRepository) {
        this.guestRepository = guestRepository;
        this.hostRepository = hostRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> findByHost(String hostEmail) throws DataException {
        Host host = hostRepository.findHostByEmail(hostEmail);


        List<Reservation> result = reservationRepository.findByHost(host.getHost_id());
        for (Reservation reservation : result) {
            int guest_id = reservation.getGuest().getGuest_id();
            reservation.setGuest(guestRepository.findById(guest_id));
            reservation.setHost(host);
        }

        return result;
    }

    public List<Reservation> findByGuest(Host host, Guest guest) throws DataException {
        List<Reservation> result = reservationRepository.findByHost(host.getHost_id());
        List<Reservation> guestReservations = new ArrayList<>();
        for (Reservation reservation : result) {
            int guest_id = reservation.getGuest().getGuest_id();
            reservation.setGuest(guestRepository.findById(guest_id));
            if (reservation.getGuest().getGuest_id() == guest.getGuest_id()) {
                guestReservations.add(reservation);
            }
        }

        return guestReservations;
    }

    public Result createReservation(Reservation reservation) throws DataException {
        //1. Create a new result -Future addition: Add validation
        //2. Use the repository "add" method to write the new reservation to the files
        //3. Set the reservation on the result
        //4. Return the result
        Result result = validate(reservation);

        if (result.isSuccess()) {
            reservation.setTotal(calculate(reservation));
            Reservation newReservation = reservationRepository.add(reservation);
            result.setReservation(newReservation);

        }

       // Result result = new Result();
      //  Reservation newReservation = reservationRepository.add(reservation);
    //    result.setReservation(newReservation);

        return result;
    }

    public Result editReservation(Reservation reservation) throws DataException {
        Result result = validate(reservation);

        if (result.isSuccess()) {
            reservation.setTotal(calculate(reservation));
            reservationRepository.update(reservation);
            result.setReservation(reservation);

        }
        return result;
    }

    public boolean cancelReservation(Reservation reservation) throws DataException {
        reservationRepository.deleteById(reservation);
        return true;
    }


    public Result validate(Reservation reservation) throws DataException {
        Result result = new Result();

        if (reservation.getGuest() == null || reservation.getGuest().getGuestEmail() == null || reservation.getGuest().getGuestEmail().trim().length() == 0) {
            result.addMessage("Guest email is required.");
            return result;
        }

        if (reservation.getHost() == null || reservation.getHost().getHostEmail() == null || reservation.getHost().getHostEmail().trim().length() == 0) {
            result.addMessage("Host email is required.");
            return result;
        }

        if (reservation.getStartDate() == null) {
            result.addMessage("Start date is required.");
            return result;
        }

        if (reservation.getEndDate() == null) {
            result.addMessage("End date is required.");
            return result;
        }

        if (reservation.getStartDate() != null && reservation.getEndDate() != null && reservation.getStartDate().isAfter(reservation.getEndDate())) {
            result.addMessage("Start date should be before the end date.");
            return result;
        }

        if (reservation.getStartDate() != null && reservation.getStartDate().isBefore(LocalDate.now())) {
            result.addMessage("Start date should not be in the past.");
            return result;
        }

        List<Reservation> hostReservations = reservationRepository.findByHost(reservation.getHost().getHost_id());
        for (Reservation res : hostReservations) {
            if (reservation.getStartDate().isBefore(res.getEndDate()) && reservation.getEndDate().isAfter(res.getStartDate())) {
                result.addMessage("This reservation overlaps with an existing reservation.");
                return result;
            }

            if (reservation.getStartDate().isBefore(res.getStartDate()) && reservation.getEndDate().isAfter(res.getStartDate())) {
                result.addMessage("This reservation overlaps with an existing reservation.");
                return result;
            }

            if (reservation.getStartDate().isBefore(res.getEndDate()) && reservation.getEndDate().isAfter(res.getEndDate())) {
                result.addMessage("This reservation overlaps with an existing reservation.");
                return result;
            }

            if (reservation.getStartDate().isAfter(res.getStartDate()) && reservation.getEndDate().isBefore(res.getEndDate())) {
                result.addMessage("This reservation overlaps with an existing reservation.");
                return result;
            }


        }


        return result;
    }

    public BigDecimal calculate(Reservation total) throws DataException {
        BigDecimal standardRate = new BigDecimal(String.valueOf(total.getHost().getStandardRate()));
        BigDecimal weekendRate = new BigDecimal(String.valueOf(total.getHost().getWeekendRate()));
        LocalDate startDate = total.getStartDate();
        LocalDate endDate = total.getEndDate();
        BigDecimal totalWeekdays = new BigDecimal("0");
        BigDecimal totalWeekends = new BigDecimal("0");

        for (; startDate.compareTo(endDate) < 0; startDate = startDate.plusDays(1)) {
            if (startDate.getDayOfWeek() != DayOfWeek.FRIDAY && startDate.getDayOfWeek() != DayOfWeek.SATURDAY) {
                totalWeekdays = totalWeekdays.add(standardRate);
            }

            if (startDate.getDayOfWeek() == DayOfWeek.FRIDAY || startDate.getDayOfWeek() == DayOfWeek.SATURDAY) {
                totalWeekends = totalWeekends.add(weekendRate);
            }
        }

        return totalWeekdays.add(totalWeekends);
    }






}

