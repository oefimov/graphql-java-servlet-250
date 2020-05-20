package example;

import graphql.execution.AsyncExecutionStrategy;
import graphql.kickstart.execution.GraphQLObjectMapper;
import graphql.kickstart.execution.GraphQLQueryInvoker;
import graphql.kickstart.execution.config.DefaultExecutionStrategyProvider;
import graphql.kickstart.servlet.GraphQLConfiguration;
import graphql.kickstart.servlet.GraphQLHttpServlet;
import graphql.kickstart.servlet.input.GraphQLInvocationInputFactory;
import graphql.schema.GraphQLSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class GraphQlController {
    private final HttpServlet delegateServlet;

    @Autowired
    public GraphQlController(GraphQLSchema schema) {
        GraphQLInvocationInputFactory inputFactory = GraphQLInvocationInputFactory.newBuilder(schema).build();
        GraphQLQueryInvoker queryInvoker = GraphQLQueryInvoker.newBuilder()
            .withExecutionStrategyProvider(new DefaultExecutionStrategyProvider(new AsyncExecutionStrategy()))
            .build();
        GraphQLObjectMapper objectMapper = GraphQLObjectMapper.newBuilder().build();
        GraphQLConfiguration configuration = GraphQLConfiguration
            .with(inputFactory)
            .with(queryInvoker)
            .with(objectMapper)
            .build();
        delegateServlet = GraphQLHttpServlet.with(configuration);
    }

    @PostMapping(value = "/graphql", produces = APPLICATION_JSON_VALUE)
    public void graphQlOperation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        delegateServlet.service(request, response);
    }
}
