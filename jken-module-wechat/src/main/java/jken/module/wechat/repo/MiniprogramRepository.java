package jken.module.wechat.repo;

import jken.module.wechat.entity.Miniprogram;
import jken.support.data.jpa.QuerydslEntityRepository;

public interface MiniprogramRepository extends QuerydslEntityRepository<Miniprogram, Long> {

    Miniprogram findByAppid(String appid);

}
