layui.define(["element", "jquery", "table", "form",  "okLayer", "okUtils", "okMock","okUpload"], function (exprots) {
    let table = layui.table;
    let form = layui.form;
    let okLayer = layui.okLayer;
    let okUtils = layui.okUtils;
    let okMock = layui.okMock;
    let okUpload = layui.okUpload;
    let $ = layui.jquery;
    okLoading.close($);

    var chatUserInfo = {

        list : function(){
            let _table = table.render({
                elem: '#tableId',
                url: okMock.api.baseUrl + "chatUserInfo/list",
                limit: 20,
                page: true,
                toolbar: true,
                toolbar: "#toolbarTpl",
                size: "sm",
                cols: [[
                    {type: "checkbox", field: "id", fixed: "left"},
                    {field: "username", title: "用户账号", width: 150},
                    {field: "nickName", title: "昵称", width: 150},
                    {field: "avatarImg", title: "头像", width: 300},
                    {field: "signature", title: "签名", width: 200},
                    {field: "gender", title: "性别", width: 80, templet: "#genderFilter"},
                    {field: "expirationTime", title: "到期时间", width: 150},
                    {field: "ifdelete", title: "是否过期", width: 100,templet: "#ifDeleteFilter"},
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
                        okUtils.ajax(okMock.api.baseUrl + "chatUserInfo/remove", "post", {idsStr: idsStr}, true).done(function (response) {
                            okUtils.tableSuccessMsg(response.msg);
                        }).fail(function (error) {
                            console.log(error)
                        });
                    }
                });
            }

            function add() {
                okLayer.open("添加", "chatuserinfoadd.html", "90%", "90%", null, function () {
                    _table.reload();
                })
            }

            function edit(data) {
                okLayer.open("更新", "chatuserinfomodify.html", "90%", "90%", function (layero) {
                    let iframeWin = window[layero.find("iframe")[0]["name"]];
                    iframeWin.initForm(data);
                }, function () {
                    _table.reload();
                })
            }

            function del(id) {
                okLayer.confirm("确定要删除吗？", function () {
                    okUtils.ajax(okMock.api.baseUrl + "chatUserInfo/remove", "post", {idsStr: id}, true).done(function (response) {
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
                okUtils.ajax(okMock.api.baseUrl + "chatUserInfo/save", "post", data.field, true).done(function (response) {
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
            okUpload.initImg(initData.avatarImg);
            okUpload.imgUpload();
            form.val("filter", initData);

            form.on("submit(edit)", function (data) {
                okUtils.ajax(okMock.api.baseUrl + "chatUserInfo/modify", "post", data.field, true).done(function (response) {
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

    exprots("chatUserInfo", chatUserInfo);
})
