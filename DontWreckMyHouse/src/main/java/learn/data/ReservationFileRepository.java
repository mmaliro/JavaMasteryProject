package learn.data;

import learn.models.Guest;
import learn.models.Reservation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReservationFileRepository implements ReservationRepository {

    private static final String HEADER = "id,start_date,end_date,guest_id,total";
    private final String directory;

    public ReservationFileRepository(@Value("${reservationFilePath:./data/reservations/}") String directory) {
        this.directory = directory;
    }

    @Override
    public List<Reservation> findByHost(String host_id) throws DataException {
        ArrayList<Reservation> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(getFilePath((host_id))))) {

            reader.readLine();

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {

                String[] fields = line.split(",");
                if (fields.length == 5) {
                    result.add(deserialize(fields));
                }
            }
        } catch (IOException ex) {

        }
        return result;
    }

    @Override
    public Reservation add(Reservation reservation) throws DataException {
        List<Reservation> all = findByHost(reservation.getHost().getHost_id());

        int nextId = all.stream()
                .mapToInt(Reservation::getRes_id)
                .max()
                .orElse(0) + 1;
        reservation.setRes_id(nextId);

        all.add(reservation);
        writeAll(all, reservation.getHost().getHost_id());
        return reservation;
    }

    @Override
    public boolean update(Reservation reservation) throws DataException {
        List<Reservation> all = findByHost(reservation.getHost().getHost_id());
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getRes_id() == (reservation.getRes_id())) {
                all.set(i, reservation);
                writeAll(all, reservation.getHost().getHost_id());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteById(Reservation reservation) throws DataException {
        List<Reservation> all = findByHost(reservation.getHost().getHost_id());
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getRes_id() == reservation.getRes_id()) {
                all.remove(i);
                writeAll(all, reservation.getHost().getHost_id());
                return true;
            }
        }
        return false;
    }

    private String getFilePath(String host_id) {
        return Paths.get(directory, host_id + ".csv").toString();
    }

    private String serialize(Reservation reservation) {
        return String.format("%s,%s,%s,%s,%s",
                reservation.getRes_id(),
                reservation.getStartDate(),
                reservation.getEndDate(),
                reservation.getGuest().getGuest_id(),
                reservation.getTotal());
    }

    private Reservation deserialize(String[] fields) {
        Reservation result = new Reservation();
        result.setRes_id(Integer.parseInt(fields[0]));
        result.setStartDate(LocalDate.parse(fields[1]));
        result.setEndDate(LocalDate.parse(fields[2]));

        Guest guest = new Guest();
        guest.setGuest_id(Integer.parseInt(fields[3]));
        result.setGuest(guest);

        result.setTotal(new BigDecimal(fields[4]));

        return result;
    }

    private void writeAll(List<Reservation> reservations, String host_id) throws DataException {
        try (PrintWriter writer = new PrintWriter(getFilePath(host_id))) {

            writer.println(HEADER);

            for (Reservation reservation : reservations) {
                writer.println(serialize(reservation));
            }
        } catch (FileNotFoundException ex) {
            throw new DataException(ex.getMessage(), ex);
        }

    }
}


