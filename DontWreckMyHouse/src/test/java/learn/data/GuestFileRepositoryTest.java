package learn.data;

import learn.models.Guest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GuestFileRepositoryTest {

    static final String SEED_FILE_PATH = "./data/guests-seed.csv";
    static final String TEST_FILE_PATH = "./data/guests-test.csv";

    @BeforeEach
    void setup() throws IOException {
        Path seedPath = Paths.get(SEED_FILE_PATH);
        Path testPath = Paths.get(TEST_FILE_PATH);

        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    @DisplayName("Should return null when the email is not found")
    void findByEmailWhenEmailIsNotFoundThenReturnNull() throws DataException {
        GuestFileRepository repository = new GuestFileRepository(TEST_FILE_PATH);

        Guest result = repository.findByEmail("not-found@email.com");

        assertNull(result);
    }

    @Test
    @DisplayName("Should return the guest when the email is found")
    void findByEmailWhenGuestIsFound() throws DataException {
        GuestFileRepository repository = new GuestFileRepository(TEST_FILE_PATH);

        Guest guest = repository.findByEmail("ogecks1@dagondesign.com");

        List<Guest> guests = new ArrayList<>();
        guests.add(guest);
        assertEquals(1, guests.size());
    }


    @Test
    @DisplayName("Should return an empty list when the file is empty")
    void findAllWhenFileIsEmpty() throws DataException {
        GuestFileRepository repo = new GuestFileRepository(TEST_FILE_PATH);

        List<Guest> guests = repo.findAll();

        assertEquals(3, guests.size());
    }

    @Test
    @DisplayName("Should return a list of guests when the file is not empty")
    void findAllWhenFileIsNotEmpty() throws DataException {
        GuestFileRepository repo = new GuestFileRepository(TEST_FILE_PATH);

        var result = repo.findAll();

        assertNotNull(result);
        assertEquals(3, result.size());
    }

    @Test
    @DisplayName("Should return null when the guest_id is not found")
    void findByIdWhenGuestIdIsNotFound() throws DataException {
        GuestFileRepository guestFileRepository = new GuestFileRepository(TEST_FILE_PATH);

        Guest guest = guestFileRepository.findById(0);

        assertNull(guest);
    }

    @Test
    @DisplayName("Should return the guest when the guest_id is found")
    void findByIdWhenGuestIdIsFound() throws DataException {
        GuestFileRepository guestFileRepository = new GuestFileRepository(TEST_FILE_PATH);

        Guest guest = guestFileRepository.findById(1);

        assertNotNull(guest);
        assertEquals(1, guest.getGuest_id());
    }

}