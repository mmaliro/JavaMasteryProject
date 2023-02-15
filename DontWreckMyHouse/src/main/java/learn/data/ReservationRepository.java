package learn.data;

import learn.models.Host;
import learn.models.Reservation;

import java.util.List;

public interface ReservationRepository {

    List<Reservation> findByHost(Host host) throws DataException;

    List<Reservation> findAll() throws DataException;

    Reservation add(Reservation reservation) throws DataException;

    boolean update(Reservation reservation) throws DataException;

    boolean deleteById(int id) throws DataException;

}
