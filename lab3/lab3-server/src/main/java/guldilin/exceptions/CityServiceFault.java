package guldilin.exceptions;
public class CityServiceFault {

}

    protected String message;
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public static CityServiceFault defaultInstance() {
        CityServiceFault fault = new CityServiceFault();
        fault.message = ErrorMessages.NOT_FOUND;
        return fault;
    }
