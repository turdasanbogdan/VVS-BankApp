package ro.sd.a2.utils.factory;

/**
 * Class to retrieve a new Factory based on the chosen type of factory.
 */
public class FactoryProvider {

    public static AbstractFactory getFactory(String choice){
        if("Account".equalsIgnoreCase(choice)){
            return new AccountFactory();
        }
        return null;
    }
}
