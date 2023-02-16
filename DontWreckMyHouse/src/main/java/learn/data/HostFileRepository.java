package learn.data;

import learn.models.Guest;
import learn.models.Host;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
public class HostFileRepository implements HostRepository{

    private final String filePath;
    private final String DELIMITER = ",";

    public HostFileRepository(@Value("${hostFilePath:./data/hosts.csv/}") String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Host findById(String host_id) throws DataException {
        return findAll().stream()
                .filter(host -> host.getHost_id() == host_id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Host> findAll() throws DataException {
        ArrayList<Host> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            reader.readLine(); // read header

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {

                String[] fields = line.split(DELIMITER, -1);
                if (fields.length == 10) {
                    result.add(deserialize(fields));
                }
            }
        } catch (IOException ex) {
            throw new DataException(ex.getMessage());

        }
        return result;

    }

    @Override
    public Host findHostByEmail(String hostEmail) throws DataException {
        return findAll().stream()
                .filter(host -> host.getHostEmail() == hostEmail)
                .findFirst()
                .orElse(null);
    }

    private Host deserialize(String[] fields) {
        Host result = new Host();
        result.setHost_id(fields[0]);
        result.setLastName(fields[1]);
        result.setHostEmail(fields[2]);
        result.setCity(fields[3]);
        result.setPhone(fields[4]);
        result.setAddress(fields[5]);
        result.setCity(fields[6]);
        result.setState(fields[7]);
        result.setPostal_code(fields[8]);
        result.setStandardRate(new BigDecimal(fields[9]));
        result.setWeekendRate(new BigDecimal(fields[10]));
        return result;
    }

}
