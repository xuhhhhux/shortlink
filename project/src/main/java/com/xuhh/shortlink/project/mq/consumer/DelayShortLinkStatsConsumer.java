package com.xuhh.shortlink.project.mq.consumer;

import com.xuhh.shortlink.project.common.convention.exception.ServiceException;
import com.xuhh.shortlink.project.dto.biz.ShortLinkStatsRecordDTO;
import com.xuhh.shortlink.project.mq.idempotent.MessageQueueIdempotentHandler;
import com.xuhh.shortlink.project.service.ShortLinkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingDeque;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.locks.LockSupport;

import static com.xuhh.shortlink.project.common.constant.RedisConstant.DELAY_QUEUE_STATS_KEY;


/**
 * 延迟记录短链接统计组件
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DelayShortLinkStatsConsumer implements InitializingBean {

    private final RedissonClient redissonClient;
    private final ShortLinkService shortLinkService;
    private final MessageQueueIdempotentHandler messageQueueIdempotentHandler;

    public void onMessage() {
        Executors.newSingleThreadExecutor(
                        runnable -> {
                            Thread thread = new Thread(runnable);
                            thread.setName("delay_short-link_stats_consumer");
                            thread.setDaemon(Boolean.TRUE);
                            return thread;
                        })
                .execute(() -> {
                    RBlockingDeque<ShortLinkStatsRecordDTO> blockingDeque = redissonClient.getBlockingDeque(DELAY_QUEUE_STATS_KEY);
                    RDelayedQueue<ShortLinkStatsRecordDTO> delayedQueue = redissonClient.getDelayedQueue(blockingDeque);
                    for (; ; ) {
                        try {
                            ShortLinkStatsRecordDTO statsRecord = delayedQueue.poll();
                            if (statsRecord != null) {
                                if (!messageQueueIdempotentHandler.isMessageProcessed(statsRecord.getMessageKey())) {
                                    if (messageQueueIdempotentHandler.isMessageProcessed(statsRecord.getMessageKey())) {
                                        return;
                                    }
                                    throw new ServiceException("消息未完成消费，需要消息队列重试");
                                }
                                try {
                                    shortLinkService.shortLinkStats(null, null, statsRecord);
                                } catch (Throwable ex) {
                                    messageQueueIdempotentHandler.delMessageProceed(statsRecord.getMessageKey());
                                    log.error("短链接监控消息队列消费出现异常: {}", ex.getMessage());
                                }
                                messageQueueIdempotentHandler.setAccomplish(statsRecord.getMessageKey());
                                continue;
                            }
                            LockSupport.parkUntil(500);
                        } catch (Throwable ignored) {
                        }
                    }
                });
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        onMessage();
    }
}
