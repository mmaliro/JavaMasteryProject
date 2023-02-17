package learn.domain;

import learn.data.DataException;
import learn.data.GuestRepository;
import learn.models.Guest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GuestService {
    private final GuestRepository guestRepository;

    @Autowired
    public GuestService(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    public Guest findByEmail(String guestEmail) throws DataException {
        return guestRepository.findByEmail(guestEmail);
    }
}
