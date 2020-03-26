layui.define(["element", "jquery", "table", "form",  "okLayer", "okUtils", "okMock"], function (exprots) {
    let table = layui.table;
    let form = layui.form;
    let okLayer = layui.okLayer;
    let okUtils = layui.okUtils;
    let okMock = layui.okMock;
    let $ = layui.jquery;
    okLoading.close($);

    var topHot = {

        list : function(){
            let _table = table.render({
                elem: '#tableId',
                url: okMock.api.baseUrl + "topHot/list",
                limit: 20,
                page: true,
                toolbar: true,
                toolbar: "#toolbarTpl",
                size: "sm",
                cols: [[
                    {type: "checkbox", fixed: "left"},
                    {field: "id", title: "id", width: 100},
                    {field: "title", title: "热榜标题", width: 300},
                    {field: "url", title: "热榜外链", width: 300},
                    {field: "originalUrl", title: "原始链接", width: 300},
                    {field: "type", title: "热榜来源", width: 150, templet: "#type"},
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
                }
            });

            function batchDel() {
                okLayer.confirm("确定要批量删除吗？", function (index) {
                    layer.close(index);
                    let idsStr = okUtils.tableBatchCheck(table);
                    if (idsStr) {
                        okUtils.ajax(okMock.api.baseUrl + "topHot/remove", "post", {idsStr: idsStr}, true).done(function (response) {
                            okUtils.tableSuccessMsg(response.msg);
                        }).fail(function (error) {
                            console.log(error)
                        });
                    }
                });
            }

            function manual(){
                okUtils.ajax(okMock.api.baseUrl + "topHot/manualCraw", "post", {}, true).done(function (response) {
                    okUtils.tableSuccessMsg(response.msg);
                }).fail(function (error) {
                    console.log(error)
                });
            }

            function add() {
                okLayer.open("添加", "tophotadd.html", "90%", "90%", null, function () {
                    _table.reload();
                })
            }

            function edit(data) {
                okLayer.open("更新", "tophotmodify.html", "90%", "90%", function (layero) {
                    let iframeWin = window[layero.find("iframe")[0]["name"]];
                    iframeWin.initForm(data);
                }, function () {
                    _table.reload();
                })
            }

            function del(id) {
                okLayer.confirm("确定要删除吗？", function () {
                    okUtils.ajax(okMock.api.baseUrl + "topHot/remove", "post", {idsStr: id}, true).done(function (response) {
                        okUtils.tableSuccessMsg(response.msg);
                    }).fail(function (error) {
                        console.log(error)
                    });
                })
            }

        },



        add : function () {

            form.on("submit(add)", function (data) {
                okUtils.ajax(okMock.api.baseUrl + "topHot/save", "post", data.field, true).done(function (response) {
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
            form.val("filter", initData);

            form.on("submit(edit)", function (data) {
                okUtils.ajax(okMock.api.baseUrl + "topHot/modify", "post", data.field, true).done(function (response) {
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

    exprots("topHot", topHot);
})
