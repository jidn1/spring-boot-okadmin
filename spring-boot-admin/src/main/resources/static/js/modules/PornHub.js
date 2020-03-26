layui.define(["element", "jquery", "table", "form",  "okLayer", "okUtils", "okMock","okUpload"], function (exprots) {
    let table = layui.table;
    let form = layui.form;
    let okLayer = layui.okLayer;
    let okUtils = layui.okUtils;
    let okMock = layui.okMock;
    let okUpload = layui.okUpload;
    let $ = layui.jquery;
    okLoading.close($);

    var pornHub = {
        list : function(){
            let _table = table.render({
                elem: '#tableId',
                url: okMock.api.baseUrl + "pornHub/list",
                limit: 20,
                page: true,
                toolbar: true,
                toolbar: "#toolbarTpl",
                size: "sm",
                cols: [[
                    {type: "checkbox", fixed: "left"},
                    {field: "id", title: "id", width: 100},
                    {field: "name", title: "名称", width: 300},
                    {field: "type", title: "类型", width: 200},
                    {field: "picture", title: "图片", width: 100},
                    {field: "m3u8", title: "播放源", width: 100},
                    {field: "fire", title: "热度", width: 100},
                    {field: "last", title: "更新时间", width: 100},
                    {title: "操作", width: 100, align: "center", fixed: "right", templet: "#operationTpl"}
                ]],
                done: function (res, curr, count) {
                    //console.info(res, curr, count);
                }
            });


            form.on("submit(search)", function (data) {
                userTable.reload({
                    where: data.field,
                    page: {curr: 1}
                });
                return false;
            });

            table.on("toolbar(tableFilter)", function (obj) {
                let data = obj.data;
                switch (obj.event) {
                    case "add":
                        add();
                        break;
                    case "batchDel":
                        batchDel();
                        break;
                    case "manual":
                        manual();
                        break;
                    case "chartview":
                        chartview();
                        break;
                    case "refRedis":
                        refreshRedis();
                        break;
                }
            });

            table.on("tool(tableFilter)", function (obj) {
                let data = obj.data;
                switch (obj.event) {
                    case "edit":
                        edit(data);
                        break;
                    case "del":
                        del(data.id);
                        break;
                    case "player":
                        player(data);
                        break;
                }
            });

            function batchDel() {
                okLayer.confirm("确定要批量删除吗？", function (index) {
                    layer.close(index);
                    let idsStr = okUtils.tableBatchCheck(table);
                    if (idsStr) {
                        okUtils.ajax(okMock.api.baseUrl + "pornHub/remove", "post", {idsStr: idsStr}, true).done(function (response) {
                            okUtils.tableSuccessMsg(response.msg);
                        }).fail(function (error) {
                            console.log(error)
                        });
                    }
                });
            }

            function manual(){
                okUtils.ajax(okMock.api.baseUrl + "pornHub/manualCraw", "post", {}, true).done(function (response) {
                    okUtils.tableSuccessMsg(response.msg);
                }).fail(function (error) {
                    console.log(error)
                });
            }

            function chartview() {
                window.location = "/video/chartview.html";
            }

            function refreshRedis(){
                okUtils.ajax(okMock.api.baseUrl + "pornHub/refreshRedis", "post", {}, true).done(function (response) {
                    okUtils.tableSuccessMsg(response.msg);
                }).fail(function (error) {
                    console.log(error)
                });
            }

            function add() {
                okLayer.open("添加", "pornhubadd.html", "90%", "90%", null, function () {
                    _table.reload();
                })
            }

            function player(data) {
                okLayer.open("播放中...", "video.html", "100%", "100%", function (layero) {
                    let iframeWin = window[layero.find("iframe")[0]["name"]];
                    iframeWin.initForm(data);
                }, null)
            }

            function edit(data) {
                okLayer.open("更新", "pornhubmodify.html", "90%", "90%", function (layero) {
                    let iframeWin = window[layero.find("iframe")[0]["name"]];
                    iframeWin.initForm(data);
                }, function () {
                    _table.reload();
                })
            }

            function del(id) {
                okLayer.confirm("确定要删除吗？", function () {
                    okUtils.ajax(okMock.api.baseUrl + "pornHub/remove", "post", {idsStr: id}, true).done(function (response) {
                        okUtils.tableSuccessMsg(response.msg);
                    }).fail(function (error) {
                        console.log(error)
                    });
                })
            }

        },



        add : function () {
            okUpload.imgUpload();
            form.on("submit(add)", function (data) {
                okUtils.ajax(okMock.api.baseUrl + "pornHub/save", "post", data.field, true).done(function (response) {
                    okLayer.greenTickMsg("添加成功", function () {
                        parent.layer.close(parent.layer.getFrameIndex(window.name));
                    });
                }).fail(function (error) {
                    console.log(error)
                });
                return false;
            });
        },


        modify:function () {

            okUpload.initImg(initData.picture);
            okUpload.imgUpload();
            form.val("filter", initData);

            form.on("submit(edit)", function (data) {
                okUtils.ajax(okMock.api.baseUrl + "pornHub/modify", "post", data.field, true).done(function (response) {
                    okLayer.greenTickMsg("编辑成功", function () {
                        parent.layer.close(parent.layer.getFrameIndex(window.name));
                    });
                }).fail(function (error) {
                    console.log(error)
                });
                return false;
            });
        }
    };

    exprots("pornHub", pornHub);
})
