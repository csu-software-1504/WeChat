package cn.csu.software.dto;/**
 * @description
 * @author oranges 864160262@qq.com
 * @date 2019/10/20
 */

import java.io.Serializable;

/**
 * @title : cn.csu.software.dto
 * @author : oranges
 * @date : 2019/10/20 23:51
 * @description :
 *  结果数据传输对象:
 *   服务端请求返回Dto
 *   isSuccess()  返回是否成功
 *   getMessage()  获取结果信息
 */
public class ResultDto implements Serializable {

    private static final int SUCCESS = 1;

    private static final int FAIL = 0;

    private static final long serialVersionUID = 3135572772012936117L;

    private int code;

    private String message;

    private ResultDto(int code) {
        this.code = code;
    }

    private ResultDto(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ResultDto SUCCESS() {
        return new ResultDto(SUCCESS);
    }

    public static ResultDto SUCCESS(String message) {
        return new ResultDto(SUCCESS, message);
    }

    public static ResultDto FAIL() {
        return new ResultDto(FAIL);
    }

    public static ResultDto FAIL(String message) {
        return new ResultDto(FAIL, message);
    }

    public boolean isSuccess() {
        return this.code == 1;
    }

    public String getMessage() {
        return this.message;
    }
}
