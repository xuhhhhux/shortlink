package com.xuhh.shortlink.project.mq.idempotent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
public class MessageQueueIdempotentHandler {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    private static final String IDEMPOTENT_KEY_PREFIX = "short-link:idempotent:";

    /**
     * 幂等标识，判断消息是否消费过
     * @param id
     * @return
     */
    public boolean isMessageProcessed(String id) {
        Boolean ok = stringRedisTemplate.opsForValue().setIfAbsent(IDEMPOTENT_KEY_PREFIX + id, "0", 2, TimeUnit.MINUTES);
        return Boolean.TRUE.equals(ok);
    }

    /**
     * 判断消息是否完成
     * @param id
     * @return
     */
    public boolean isAccomplish(String id) {
        String ok = stringRedisTemplate.opsForValue().get(IDEMPOTENT_KEY_PREFIX + id);
        return Objects.equals(ok, "1");
    }

    /**
     * 设置消息完成
     * @param id
     */
    public void setAccomplish(String id) {
        stringRedisTemplate.opsForValue().set(IDEMPOTENT_KEY_PREFIX + id, "1", 2, TimeUnit.MINUTES);
    }

    /**
     * 删除幂等标识
     * @param id
     */
    public void delMessageProceed(String id) {
        stringRedisTemplate.delete(IDEMPOTENT_KEY_PREFIX + id);
    }
}
