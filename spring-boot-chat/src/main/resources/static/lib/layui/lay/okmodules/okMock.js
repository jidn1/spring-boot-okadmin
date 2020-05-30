"use strict";
layui.define([], function (exprots) {
    let okMock = {
        api: {
            baseUrl : "http://localhost:6001/chat/",
            fileUrl : "http://hry-exchange-public.oss-cn-beijing.aliyuncs.com/",
            socketUrl : "ws://localhost:6001/chat/chatSocket",
            socketGroupUrl : "ws://localhost:6001/chatGroupSocket",
        },



    };


    exprots("okMock", okMock);
});

