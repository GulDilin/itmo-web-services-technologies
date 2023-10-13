package guldilin.service.impl;

import guldilin.repository.impl.SessionFactoryBuilderImpl;
import guldilin.repository.interfaces.TestRepo;
import guldilin.service.interfaces.TestService;
import jakarta.inject.Inject;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebService(serviceName = "TestService", targetNamespace = "http://localhost:8080/TestService")
@NoArgsConstructor
public class TestServiceImpl implements TestService {
    @Inject
    private TestRepo testRepo;

    private static final Logger logger = LogManager.getLogger(SessionFactoryBuilderImpl.class);

    @Override
    @WebMethod
    public String getTestData() {
        return testRepo.getData();
    }
}
