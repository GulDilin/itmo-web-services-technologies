//package guldilin.repository.factory;
//
//import guldilin.repository.impl.CityRepositoryImpl;
//import guldilin.repository.impl.SessionFactoryBuilderImpl;
//import guldilin.repository.interfaces.CityRepository;
//import guldilin.repository.interfaces.SessionFactoryBuilderA;
////import jakarta.inject.Named;
////import jakarta.inject.Singleton;
//import lombok.NoArgsConstructor;
//
//import javax.enterprise.context.ApplicationScoped;
//import javax.enterprise.inject.New;
//import javax.enterprise.inject.Produces;
//import javax.inject.Singleton;
//
//import java.io.Serializable;
//import java.lang.annotation.ElementType;
//import java.lang.annotation.Retention;
//import java.lang.annotation.RetentionPolicy;
//import java.lang.annotation.Target;
//
//@Singleton
//@NoArgsConstructor
//public class CityRepositoryFactory implements Serializable {
//
////    @Inject
////    @New
////    private SessionFactoryBuilderA sessionFactoryBuilder;
//
////    @Named("CityRepository")
//    @Produces
//    public CityRepository getCityRepository() {
//        return new CityRepositoryImpl(new SessionFactoryBuilderImpl());
//    }
//}
