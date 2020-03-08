package jken.module.cms.service;

import jken.module.cms.entity.Channel;
import jken.support.service.TreeService;
import org.springframework.stereotype.Service;

@Service
public class ChannelService extends TreeService<Channel, Long> {
}
