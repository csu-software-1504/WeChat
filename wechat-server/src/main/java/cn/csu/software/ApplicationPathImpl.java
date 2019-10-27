package cn.csu.software;/**
 * @description
 * @author oranges 864160262@qq.com
 * @date 2019/10/27
 */

import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;


/**
 * @title : cn.csu.software
 * @author : oranges
 * @date : 2019/10/27 1:50
 * @description :
 * resteasy入口
 */
@Component
@ApplicationPath("/")
public class ApplicationPathImpl extends Application {

}
