package learn.data;

import learn.models.Guest;

import java.util.List;

public interface GuestRepository {
    Guest findById(int guest_id) throws DataException;

    List<Guest> findAll() throws DataException;

    Guest findByEmail(String guestEmail) throws DataException;

}
