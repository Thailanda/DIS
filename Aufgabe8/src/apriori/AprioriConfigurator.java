package apriori;

import com.google.inject.Guice;
import javax.websocket.server.ServerEndpointConfig;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-07-07
 */
public class AprioriConfigurator extends ServerEndpointConfig.Configurator {
    @Override
    public <T> T getEndpointInstance(Class<T> endpointClass) throws InstantiationException {
        return Guice.createInjector(new AprioriModule()).getInstance(endpointClass);
    }
}
