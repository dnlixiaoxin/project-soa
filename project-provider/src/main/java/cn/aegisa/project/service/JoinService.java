package cn.aegisa.project.service;

import cn.aegisa.project.vo.JoinInfoVo;

import java.util.List;

/**
 * Using IntelliJ IDEA.
 *
 * @author HNAyd.xian
 * @date 2018/3/1 17:42
 */
public interface JoinService {
    void saveJoin(JoinInfoVo infoVo);

    List<String> getCustomerHistory(Integer cid);
}