package entity;

import java.util.function.Supplier;

public class Optional<T> {
    private T object;

    public Optional(T object) {
        set(object);
    }

    public Optional() {
        this(null);
    }

    public T get() {
        return this.object;
    }

    public void set(T object) {
        this.object = object;
    }

    public boolean isPresent() {
        return this.object != null;
    }

    public boolean isEmpty() {
        return this.object == null;
    }

    public T orElseThrow(Supplier<? extends RuntimeException> exceptionSupplier) {
        if (isPresent()) {
            return object;
        } else {
            throw exceptionSupplier.get();
        }
    }
}
