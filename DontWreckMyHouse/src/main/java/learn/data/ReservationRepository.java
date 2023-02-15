package learn.data;

import learn.models.Host;
import learn.models.Reservation;

import java.util.List;
import java.util.UUID;

public interface ReservationRepository {

    List<Reservation> findByHost(UUID host_id) throws DataException;

    Reservation add(Reservation reservation) throws DataException;

    boolean update(Reservation reservation) throws DataException;

    boolean deleteById(Reservation reservation) throws DataException;

}
