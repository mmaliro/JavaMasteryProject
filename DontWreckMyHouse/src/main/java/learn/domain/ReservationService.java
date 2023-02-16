package learn.domain;

import learn.data.DataException;
import learn.data.GuestRepository;
import learn.data.HostRepository;
import learn.data.ReservationRepository;
import learn.models.Guest;
import learn.models.Host;
import learn.models.Reservation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {

    private final GuestRepository guestRepository;
    private final HostRepository hostRepository;
    private final ReservationRepository reservationRepository;

    public ReservationService(GuestRepository guestRepository, HostRepository hostRepository, ReservationRepository reservationRepository) {
        this.guestRepository = guestRepository;
        this.hostRepository = hostRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> findByHost(String host_id) throws DataException {
        List<Reservation> result = reservationRepository.findByHost(host_id);
        for (Reservation reservation : result) {
            int guest_id = reservation.getGuest().getGuest_id();
            reservation.setGuest(guestRepository.findById(guest_id));
            reservation.setHost(hostRepository.findById(host_id));
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


}
