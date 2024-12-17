package pers.jhshop.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@MapperScan(basePackages = "pers.jhshop.user.mapper")
@EnableFeignClients(basePackages = {"pers.jhshop.fapi"})
@EnableDiscoveryClient
@SpringBootApplication
public class JhShopUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(JhShopUserServiceApplication.class, args);
    }

}
