package example;

import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class QueryResolver implements GraphQLQueryResolver {
    public MyType test(long id) {
        if (id == 1) {
            throw new IllegalArgumentException("This error is processed OK");
        }
        MyType myType = new MyType();
        myType.setId(id);
        return myType;
    }
}
