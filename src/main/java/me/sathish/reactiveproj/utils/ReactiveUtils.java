package me.sathish.reactiveproj.utils;

import java.util.function.Consumer;

public class ReactiveUtils {
    public static Consumer<Object> onNext() {
        return o -> System.out.println("Recieved: " + o);
    }

    public static Consumer<Throwable> onError() {
        return e -> System.out.println("ERROR: " + e.getMessage());
    }

    public static Runnable onComplete() {
        return () -> System.out.println("Completed");
    }
}
