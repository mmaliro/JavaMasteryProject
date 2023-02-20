package learn.data;

import learn.models.Host;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class HostFileRepositoryTest {

    static final String SEED_FILE_PATH = "./data/hosts-seed.csv";
    static final String TEST_FILE_PATH = "./data/hosts-test.csv";

    @Autowired
    HostFileRepository repository;

    @BeforeEach
    void setUp() throws IOException {
        Path seedPath = Paths.get(SEED_FILE_PATH);
        Path testPath = Paths.get(TEST_FILE_PATH);

        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    @DisplayName("Should return a list of hosts when the file has data")
    void findAllWhenFileHasData() {
    }

    @Test
    @DisplayName("Should throw an exception when the file is not found")
    void findAllWhenFileIsNotFoundThenThrowException() {
        HostFileRepository repository = new HostFileRepository("./data/hosts-not-found.csv");
    }

    @Test
    @DisplayName("Should return an empty list when the file is empty")
    void findAllWhenFileIsEmpty() throws DataException {
        HostFileRepository repository = new HostFileRepository(TEST_FILE_PATH);

        List<Host> result = repository.findAll();

        assertEquals(3, result.size());
    }

    @Test
    void findById() {
    }

    @Test
    void findAll() {
    }

    @Test
    void findHostByEmail() {
    }
}