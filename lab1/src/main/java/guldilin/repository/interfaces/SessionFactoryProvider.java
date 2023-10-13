package guldilin.repository.interfaces;

import org.hibernate.SessionFactory;

public interface SessionFactoryProvider {
    SessionFactory provideSessionFactory();
}
