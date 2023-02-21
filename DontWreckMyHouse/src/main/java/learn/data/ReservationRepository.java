package learn.data;

import learn.models.Host;
import learn.models.Reservation;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface ReservationRepository {

    List<Reservation> findByHost(String host_id) throws DataException;

    Reservation add(Reservation reservation) throws DataException;

    boolean update(Reservation reservation) throws DataException;

    boolean deleteById(Reservation reservation) throws DataException;

}
