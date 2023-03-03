package committee.nova.match;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.function.Consumer;
import java.util.function.Predicate;

@SuppressWarnings({"unchecked", "unused"})
public class Match<T> {
    private final Queue<Case<? extends T>> cases = new ArrayDeque<>();
    private final T object;

    private Match(T object) {
        this.object = object;
    }

    public static <U> Match<U> from(U object) {
        return new Match<>(object);
    }

    /**
     * Adds a case into this {@link Match}, whose execution will end if the predicate returns true.
     *
     * @param <U>         The target type of pattern matching.
     * @param targetClass The class of the target type.
     * @param predicate   A predicate of the target type. If the object matches the type, the predicate will run.
     * @return The {@link Match} instance with this case added.
     */
    public <U extends T> Match<T> inCase(Class<U> targetClass, Predicate<U> predicate) {
        cases.add(new Case<>(targetClass, predicate));
        return this;
    }

    /**
     * Adds a "break" case into this {@link Match}, whose execution will end if the matching is success.
     *
     * @param <U>         The target type of pattern matching.
     * @param targetClass The class of the target type.
     * @param consumer    A consumer of the target type. If the object matches the type, the consumer will run.
     * @return The {@link Match} instance with this "break" case added.
     */
    public <U extends T> Match<T> brk(Class<U> targetClass, Consumer<U> consumer) {
        return inCase(targetClass, t -> {
            consumer.accept(t);
            return false;
        });
    }

    /**
     * Adds a "pass" case into this {@link Match}, whose execution will continue after the matching.
     *
     * @param <U>         The target type of pattern matching.
     * @param targetClass The class of the target type.
     * @param consumer    A consumer of the target type. If the object matches the type, the consumer will run.
     * @return The {@link Match} instance with this "pass" case added.
     */
    public <U extends T> Match<T> pass(Class<U> targetClass, Consumer<U> consumer) {
        return inCase(targetClass, t -> {
            consumer.accept(t);
            return true;
        });
    }

    /**
     * Execute the {@link Case}s on the target. If the predicate in a Case returns false, the execution ends.
     */
    public void exec() {
        for (Case<? extends T> $case : cases) if (!$case.run(object)) return;
    }

    /**
     * A Case stores a type-specific Class and a Predicate of the type.
     */
    static class Case<T> {
        final Class<T> cls;
        final Predicate<T> predicate;

        Case(Class<T> cls, Predicate<T> predicate) {
            this.cls = cls;
            this.predicate = predicate;
        }

        /**
         * @param target The target object to be tested by the predicate
         * @return The result of the predicate.
         */
        boolean run(Object target) {
            if (!target.getClass().isAssignableFrom(cls)) return true;
            return predicate.test((T) target);
        }
    }
}
