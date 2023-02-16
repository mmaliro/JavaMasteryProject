package learn.data;

import learn.models.Host;

import java.util.List;

public interface HostRepository {

    Host findById(String host_id) throws DataException;

    List<Host> findAll() throws DataException;

    Host findByEmail(String hostEmail) throws DataException;
}
