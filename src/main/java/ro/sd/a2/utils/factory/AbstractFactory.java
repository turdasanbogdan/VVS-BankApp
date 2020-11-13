package ro.sd.a2.utils.factory;

/**
 * Abstract factory interface.
 */
public interface AbstractFactory<T> {
    T create(String type);
}
