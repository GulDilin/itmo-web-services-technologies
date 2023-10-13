package guldilin.service.interfaces;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;

@WebService
public interface TestService {
    @WebMethod
    String getTestData();
}
