package learn.domain;

import learn.data.DataException;
import learn.data.HostRepository;
import learn.models.Guest;
import learn.models.Host;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HostService {
    private final HostRepository hostRepository;

    @Autowired
    public HostService(HostRepository hostRepository) {
        this.hostRepository = hostRepository;

    }

    public Host findByEmail(String hostEmail) throws DataException {
        return hostRepository.findHostByEmail(hostEmail);
    }
}
