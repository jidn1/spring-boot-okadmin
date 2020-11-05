--
-- Created by IntelliJ IDEA.
-- User: jidening
-- Date: 2020/11/3
-- Time: 下午6:02
-- To change this template use File | Settings | File Templates.
--

if (redis.call('EXISTS', KEYS[1]) == 0) then
    return '';
end;
local result = {};
local min = redis.call('ZRANGE', KEYS[1], 0, 0, 'WITHSCORES');
local minScore = min[2];
local planIds = redis.call('ZRANGEBYSCORE', KEYS[1], minScore, ARGV[1])
if (#planIds == 0) then
    return '';
end
for key, value in pairs(planIds) do
    table.insert(result, value);
end
return table.concat(result, ',');

