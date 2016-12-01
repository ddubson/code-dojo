package amqp;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Created by ddubs on 11/29/2016.
 */
@SpringBootApplication
public class Application {
    public static final String NOTIFICATIONS = "notifications";

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public InitializingBean initQueues(AmqpAdmin amqpAdmin) {
        return () -> {
            Queue queue = new Queue(NOTIFICATIONS, true);
            DirectExchange exchange = new DirectExchange(NOTIFICATIONS);
            Binding binding = BindingBuilder.bind(queue).to(exchange).with(NOTIFICATIONS);
            amqpAdmin.declareQueue(queue);
            amqpAdmin.declareExchange(exchange);
            amqpAdmin.declareBinding(binding);
        };
    }


}
