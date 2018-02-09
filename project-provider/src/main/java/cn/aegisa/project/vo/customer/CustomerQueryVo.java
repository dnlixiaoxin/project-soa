package cn.aegisa.project.vo.customer;

import lombok.Getter;
import lombok.Setter;

/**
 * Using IntelliJ IDEA.
 *
 * @author HNAyd.xian
 * @date 2018/2/9 14:47
 */
@Setter
@Getter
public class CustomerQueryVo {
    private Integer page;
    private Integer limit;
    private String keyword;
}