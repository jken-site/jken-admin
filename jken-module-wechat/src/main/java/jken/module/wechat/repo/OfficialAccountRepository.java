package jken.module.wechat.repo;

import jken.module.wechat.entity.OfficialAccount;
import jken.support.data.jpa.QuerydslEntityRepository;

public interface OfficialAccountRepository extends QuerydslEntityRepository<OfficialAccount, Long> {

    OfficialAccount findByAppid(String appid);

}
