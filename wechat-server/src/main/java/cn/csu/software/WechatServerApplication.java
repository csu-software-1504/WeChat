package cn.csu.software;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.csu.software.dao")
public class WechatServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WechatServerApplication.class, args);
	}
}
