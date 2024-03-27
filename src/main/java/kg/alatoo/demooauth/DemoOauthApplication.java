package kg.alatoo.demooauth;

import kg.alatoo.demooauth.entity.User;
import kg.alatoo.demooauth.repository.UserRepository;
import kg.alatoo.demooauth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoOauthApplication {


    public static void main(String[] args) {
        SpringApplication.run(DemoOauthApplication.class, args);

    }

}
