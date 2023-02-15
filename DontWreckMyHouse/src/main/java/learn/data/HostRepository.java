package learn.data;

import learn.models.Host;

import java.util.List;
import java.util.UUID;

public interface HostRepository {

    Host findById(UUID host_id) throws DataException;

    List<Host> findAll() throws DataException;

    Host findByEmail(String hostEmail) throws DataException;
}
