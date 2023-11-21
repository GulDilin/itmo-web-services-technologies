package guldilin.exceptions;

public class EntryNotFound extends Exception{
    public EntryNotFound() {
        super(ErrorMessages.NOT_FOUND);
    }

    public EntryNotFound(String m) {
        super(m);
    }
}
