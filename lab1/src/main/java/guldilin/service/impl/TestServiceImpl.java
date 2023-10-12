package guldilin.service.impl;

import guldilin.repository.impl.SessionFactoryBuilderImpl;
import guldilin.repository.interfaces.TestRepo;
import guldilin.service.interfaces.TestService;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.jws.WebMethod;
import javax.jws.WebService;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.naming.NamingException;


@WebService(serviceName = "TestService", targetNamespace = "http://localhost:8080/TestService")
@NoArgsConstructor
public class TestServiceImpl implements TestService {
//    static class TestRepoProvider {
//        @Inject static TestRepo testRepo;
//    }
    @Inject
    private TestRepo testRepo;
    private static final Logger logger = LogManager.getLogger(SessionFactoryBuilderImpl.class);

    @PostConstruct
    void init() throws NamingException {
        logger.info("Post Contruct TestServiceImpl");
//        InitialContext ctx = new InitialContext();
//        this.testRepo = (TestRepo) ctx.lookup("java:comp/env/guldilin/repository/interfaces/TestRepo");
        testRepo.getData();
//        logger.info("Repo get data " + testRepo.getData());
    }
    @Override
     @WebMethod
    public String getTestData() {
        return testRepo.getData();
    }
}
