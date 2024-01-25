//package guldilin.cli;
//
//import guldilin.RestApplication;
//import guldilin.config.PropertyKey;
//import io.netty.handler.logging.LogLevel;
//import io.netty.handler.logging.LoggingHandler;
//import io.netty.util.internal.logging.InternalLoggerFactory;
//import io.netty.util.internal.logging.Log4JLoggerFactory;
//import jakarta.annotation.PostConstruct;
//import jakarta.ejb.Singleton;
//import jakarta.ejb.Startup;
//import jakarta.enterprise.context.ApplicationScoped;
//import jakarta.enterprise.inject.spi.CDI;
//import jakarta.inject.Inject;
//import jakarta.ws.rs.core.Application;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.jboss.resteasy.cdi.CdiInjectorFactory;
//import org.jboss.resteasy.core.ResteasyDeploymentImpl;
//import org.jboss.resteasy.plugins.server.netty.NettyJaxrsServer;
//import org.jboss.resteasy.plugins.server.netty.cdi.CdiNettyJaxrsServer;
//import org.jboss.resteasy.spi.ResteasyDeployment;
//
//import java.util.Map;
//
//@ApplicationScoped
//public class ServerApp {
//    /**
//     * Just logger.
//     */
//    private static final Logger LOGGER = LogManager.getLogger(ServerApp.class);
//
//    @Inject
//    private RestApplication application;
//    /**
//     * Startup.
//     */
//    public void init(final Map<PropertyKey, String> params) {
//        LOGGER.info("init Server");
////        ResteasyDeployment deployment = new ResteasyDeploymentImpl();
////        deployment.setApplicationClass(RestApplication.class.getName());
//
//        InternalLoggerFactory.setDefaultFactory(new Log4JLoggerFactory());
//
////        NettyJaxrsServer netty = new NettyJaxrsServer();
//////        netty.setDeployment(deployment);
//////        netty.setDeployment(deployment);
////        netty.getDeployment().setApplication(this.application);
////        netty.setPort(8089);
////        netty.setRootResourcePath("");
////        netty.setSecurityDomain(null);
////
////        netty.getDeployment().start();
////        netty.getDeployment().registration();
////        netty.start();
////        netty.deploy();
//
////        LoadPathsExtension paths = CDI.current().select(LoadPathsExtension.class).get();
//        CdiNettyJaxrsServer netty = new CdiNettyJaxrsServer();
////        ResteasyDeployment rd = new ResteasyDeploymentImpl();
////        rd.setApplication(this.application);
////        rd.setActualResourceClasses(paths.getResources());
////        rd.setInjectorFactoryClass(CdiInjectorFactory.class.getName());
////        netty.setDeployment(rd);
//        netty.getDeployment().setApplication(this.application);
////        netty.getDeployment().setInjectorFactoryClass(CdiInjectorFactory.class.getName());
//        netty.setPort(8089);
//        netty.setRootResourcePath("");
//        netty.setSecurityDomain(null);
//        netty.start();
//
////        netty.setChannelHandlers(new LoggingHandler(LogLevel.INFO));
//
//        LOGGER.info("Server started");
//
//    }
//}
