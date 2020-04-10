"use strict";
layui.define([], function (exprots) {
    let okMock = {
        api: {
            //172.16.10.245 39.96.203.150
            baseUrl : "http://localhost:6002/",
            fileUrl : "http://hry-exchange-public.oss-cn-beijing.aliyuncs.com/",
            socketUrl : "ws://localhost:6002/chatSocket",
        }
    };
    exprots("okMock", okMock);
});
