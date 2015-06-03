package web;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.mongodb.MongoClient;
import java.net.UnknownHostException;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-06-03
 */
public class MongoModule extends AbstractModule {

    @Override
    protected void configure() {

    }

    @Provides
    public MongoClient provideClient() throws UnknownHostException {
        return new MongoClient("127.0.0.1");
    }
}
