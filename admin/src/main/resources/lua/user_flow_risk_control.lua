-- 设置用户请求频率限制参数
local username = KEYS[1]
local timeWindow = tonumber(ARGV[1])

-- Redis存储用户请求次数Key
local accessKey = "short-link:user-flow-risk-control:" .. username

-- 访问次数自增，获取自增后的值
local currentAccessCount = redis.call("INCR", accessKey)

-- 重置有效期
redis.call("EXPIRE", accessKey, timeWindow)

-- 返回当前访问次数
return currentAccessCount;