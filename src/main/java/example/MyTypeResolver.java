package example;

import graphql.kickstart.tools.GraphQLResolver;

import java.util.*;

import static java.util.Arrays.asList;

public class MyTypeResolver implements GraphQLResolver<MyType> {
    private static final List<Long> VALUES = asList(42L, 24L);

    public Collection<Long> values(MyType object) {
        final boolean fail = object.getId() == 0;
        return new AbstractCollection<Long>() {
            @Override
            public Iterator<Long> iterator() {
                return fail ? failingIterator() : VALUES.iterator();
            }

            @Override
            public int size() {
                if (fail) {
                    throw new IllegalArgumentException("Lazy collection failed to fetch");
                }
                return VALUES.size();
            }
        };
    }

    private Iterator<Long> failingIterator() {
        return new Iterator<Long>() {
            @Override
            public boolean hasNext() {
                throw new IllegalArgumentException("Lazy collection failed to fetch");
            }

            @Override
            public Long next() {
                throw new NoSuchElementException();
            }
        };
    }
}
