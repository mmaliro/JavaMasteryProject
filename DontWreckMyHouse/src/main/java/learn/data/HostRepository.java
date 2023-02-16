package learn.data;

import learn.models.Host;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HostRepository {

    Host findById(String host_id) throws DataException;

    List<Host> findAll() throws DataException;

    Host findByEmail(String hostEmail) throws DataException;
}
