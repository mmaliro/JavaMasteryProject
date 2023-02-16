package learn.data;

import learn.models.Guest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GuestFileRepository implements GuestRepository {

    private final String filePath;
    private final String DELIMITER = ",";

    public GuestFileRepository(@Value("${guestFilePath}") String filePath) {
        this.filePath = filePath;
    }


    @Override
    public Guest findById(int guest_id) throws DataException {
        return findAll().stream()
                .filter(guest -> guest.getGuest_id() == guest_id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Guest> findAll() throws DataException {
        ArrayList<Guest> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            reader.readLine(); // read header

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {

                String[] fields = line.split(DELIMITER);
                if (fields.length == 6) {
                    result.add(deserialize(fields));
                }
            }
        } catch (IOException ex) {
            throw new DataException(ex.getMessage());

        }
        return result;

    }

    @Override
    public Guest findByEmail(String guestEmail) throws DataException {
        return findAll().stream()
                .filter(guest -> guest.getGuestEmail() == guestEmail)
                .findFirst()
                .orElse(null);
    }


    private Guest deserialize(String[] fields) {
        Guest result = new Guest();
        result.setGuest_id(Integer.parseInt(fields[0]));
        result.setFirstName(fields[1]);
        result.setLastName(fields[2]);
        result.setGuestEmail(fields[3]);
        return result;
    }


}
