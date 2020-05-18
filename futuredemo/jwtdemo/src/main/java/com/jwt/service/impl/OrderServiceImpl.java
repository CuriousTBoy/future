package com.jwt.service.impl;

import com.jwt.service.OrderService;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @Author Tanyu
 * @Date 2020/5/14 14:33
 * @Version 1.0
 */
@Service
public class OrderServiceImpl implements OrderService {

//  @Autowired
//  private Redisson redisson;

  @Autowired
  private StringRedisTemplate stringRedisTemplate;

  @Override
  public String getSerialNumber(){

//    RLock lockKey = redisson.getLock("lockKey");
//    try {
//    lockKey.lock();
//    Integer key = Integer.parseInt(stringRedisTemplate.opsForValue().get("key"));
//      System.out.println(key);
//      Integer nowKey=key+1;
//      stringRedisTemplate.opsForValue().set("key",nowKey+"");
//      return key+"";
//    } finally {
//    lockKey.unlock();
//    }
    return null;
  }

  @Override
  public String getSerialKey() {
    String lockKey="lockKey";
    Integer key=0;
    String clientId = UUID.randomUUID().toString();
    try {
      Boolean result = stringRedisTemplate.opsForValue()
          .setIfAbsent(lockKey, clientId, 30, TimeUnit.SECONDS);
      if (!result){
        return "error";
      }
      key = Integer.parseInt(stringRedisTemplate.opsForValue().get("key"));
      Integer newKey=key+1;
      stringRedisTemplate.opsForValue().set("key",newKey+"");
      System.out.println(key);
    } finally {
      if (clientId.equals(stringRedisTemplate.opsForValue().get(lockKey))){
        stringRedisTemplate.delete(lockKey);
      }
    }
    return key+"";
  }
}
