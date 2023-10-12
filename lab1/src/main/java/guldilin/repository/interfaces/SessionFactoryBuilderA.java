package guldilin.repository.interfaces;

import org.hibernate.SessionFactory;

import javax.ejb.Local;

@Local
public interface SessionFactoryBuilderA {
    SessionFactory getSessionFactory();
}
