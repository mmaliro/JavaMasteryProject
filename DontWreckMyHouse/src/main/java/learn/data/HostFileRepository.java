package learn.data;

import learn.models.Host;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HostFileRepository implements HostRepository{
    @Override
    public Host findById(String host_id) throws DataException {
        return null;
    }

    @Override
    public List<Host> findAll() throws DataException {
        return null;
    }

    @Override
    public Host findByEmail(String hostEmail) throws DataException {
        return null;
    }
}
