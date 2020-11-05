--
-- Created by IntelliJ IDEA.
-- User: jidening
-- Date: 2020/11/3
-- Time: 下午6:20
-- To change this template use File | Settings | File Templates.
--
if (redis.call('EXISTS', KEYS[1]) == 0) then
    return '';
end;
local result = 0;
local q = redis.call('ZREM',KEYS[1],ARGV[1]);
local c = redis.call('ZREM',KEYS[1],ARGV[2]);
result = q + c;
return c + q;

