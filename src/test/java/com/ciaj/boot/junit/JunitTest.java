package com.ciaj.boot.junit;

import com.alibaba.fastjson.JSON;
import com.ciaj.boot.AdminApplication;
import com.ciaj.boot.component.config.redis.RedisUtil;
import com.ciaj.boot.modules.sys.entity.po.SysUserPo;
import com.ciaj.boot.modules.sys.service.SysUserService;
import com.ciaj.comm.utils.Page;
import com.ciaj.comm.utils.PageUtis;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminApplication.class)
public class JunitTest {
    @Autowired
    SysUserService sysUserService;
    @Autowired
    RedisUtil redisUtil;

    @Test
    public void select() {
        Page page = new Page();
        page.setPageSize(2);
        page.setPageEnabled(true);
        page.setOrderByEnabled(true);
        page.setOrderBy("id desc");
        PageUtis.putPageToThreadLocal(page);
        Page pag = sysUserService.selectPOPage(null);
        final Page sysUserDtoPage = sysUserService.selectDTOPage(null);

        System.out.println(pag.getList().size());
        System.out.println(sysUserDtoPage.getList().size());

        final List<SysUserPo> select = sysUserService.select(new SysUserPo());
        System.out.println(JSON.toJSON(select).toString());
    }

    @Test
    public void redis(){
        redisUtil.set("my","12312");
    }
}
