"use strict";
layui.define([], function (exprots) {
    let okMock = {
        api: {
            baseUrl : "https://www.zjjtv.top/chat/",
            fileUrl : "https://hry-exchange-public.oss-cn-beijing.aliyuncs.com/",
            socketUrl : "wss://www.zjjtv.top/chat/chatSocket",
            socketGroupUrl : "wss://www.zjjtv.top/chatGroupSocket",
        }
    };
    exprots("okMock", okMock);
});
