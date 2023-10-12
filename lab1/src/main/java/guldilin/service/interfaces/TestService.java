package guldilin.service.interfaces;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface TestService {
    @WebMethod
    String getTestData();
}
