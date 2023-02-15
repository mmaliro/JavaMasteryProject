package learn.data;

import learn.models.Host;
import learn.models.Reservation;

import java.util.List;

public class ReservationFileRepository implements ReservationRepository {


    @Override
    public List<Reservation> findByHost(Host host) throws DataException {
        return null;
    }

    @Override
    public List<Reservation> findAll() throws DataException {
        return null;
    }

    @Override
    public Reservation add(Reservation reservation) throws DataException {
        return null;
    }

    @Override
    public boolean update(Reservation reservation) throws DataException {
        return false;
    }

    @Override
    public boolean deleteById(int id) throws DataException {
        return false;
    }
}
