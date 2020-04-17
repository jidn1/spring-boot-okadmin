"use strict";
layui.define([], function (exprots) {
    let okMock = {
        api: {
            baseUrl : "https://39.96.203.150:6002/",
            fileUrl : "https://hry-exchange-public.oss-cn-beijing.aliyuncs.com/",
            socketUrl : "wss://39.96.203.150:6002/chatSocket",
            socketGroupUrl : "ws://39.96.203.150:6002/chatGroupSocket",
        }
    };
    exprots("okMock", okMock);
});
