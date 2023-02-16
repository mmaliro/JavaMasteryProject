package learn.data;

import learn.models.Guest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface GuestRepository {
    Guest findById(int guest_id) throws DataException;

    List<Guest> findAll() throws DataException;

    Guest findByEmail(String guestEmail) throws DataException;



}



