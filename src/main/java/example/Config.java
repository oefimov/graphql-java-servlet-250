package example;

import graphql.kickstart.tools.SchemaParser;
import graphql.schema.GraphQLSchema;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "example")
public class Config {
    @Bean
    public GraphQLSchema schema() {
        return SchemaParser.newParser()
            .file("schema.graphqls")
            .resolvers(new QueryResolver(), new MyTypeResolver())
            .build()
            .makeExecutableSchema();
    }
}
