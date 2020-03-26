layui.define(["element", "jquery", "table", "form", "laydate", "okLayer", "okUtils", "okMock","okUpload"], function (exprots) {
    let table = layui.table;
    let form = layui.form;
    let laydate = layui.laydate;
    let okLayer = layui.okLayer;
    let okUtils = layui.okUtils;
    let okMock = layui.okMock;
    let okUpload = layui.okUpload;
    let $ = layui.jquery;
    okLoading.close($);

    var movie = {

        list : function(){
            let _table = table.render({
                elem: '#tableId',
                url: okMock.api.baseUrl + "movie/list",
                limit: 20,
                page: true,
                toolbar: true,
                toolbar: "#toolbarTpl",
                size: "sm",
                cols: [[
                    {type: "checkbox", fixed: "left"},
                    {field: "id", title: "ID", width: 64, sort: true},
                    {field: "moviceName", title: "电影名称", width: 200},
                    {field: "mainCharacter", title: "主要演员", width: 150},
                    {field: "director", title: "导演", width: 100},
                    {field: "moviceReleaseTime", title: "上映时间", width: 100},
                    {field: "language", title: "语言", width: 100},
                    {field: "country", title: "国别", width: 100},
                    {field: "movicePlayerUrl", title: "播放源", width: 200},
                    {field: "movicePictureUrl", title: "原海报", width: 200},
                    {field: "moviceLocalUrl", title: "本地海报", width: 200},
                    {field: "duration", title: "电影时长(分钟)", width: 150},
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
                    case "refRedis":
                        refreshRedis();
                        break;
                    case "manual":
                        manual();
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
                        ckplayer(data);
                        break;
                }
            });

            function batchDel() {
                okLayer.confirm("确定要批量删除吗？", function (index) {
                    layer.close(index);
                    let idsStr = okUtils.tableBatchCheck(table);
                    if (idsStr) {
                        okUtils.ajax(okMock.api.baseUrl + "movie/remove", "post", {idsStr: idsStr}, true).done(function (response) {
                            okUtils.tableSuccessMsg(response.msg);
                        }).fail(function (error) {
                            console.log(error)
                        });
                    }
                });
            }



            function add() {
                okLayer.open("添加", "movieadd.html", "90%", "90%", null, function () {
                    _table.reload();
                })
            }

            function ckplayer(data) {
                okLayer.open("播放中...", "ckplayer.html", "100%", "100%", function (layero) {
                    let iframeWin = window[layero.find("iframe")[0]["name"]];
                    iframeWin.initForm(data);
                }, null)
            }

            function refreshRedis(){
                okUtils.ajax(okMock.api.baseUrl + "movie/refreshRedis", "post", {}, true).done(function (response) {
                    okUtils.tableSuccessMsg(response.msg);
                }).fail(function (error) {
                    console.log(error)
                });
            }

            function manual(){
                okUtils.ajax(okMock.api.baseUrl + "movie/manualCraw", "post", {}, true).done(function (response) {
                    okUtils.tableSuccessMsg(response.msg);
                }).fail(function (error) {
                    console.log(error)
                });
            }

            function edit(data) {
                okLayer.open("更新", "moviemodify.html", "90%", "90%", function (layero) {
                    let iframeWin = window[layero.find("iframe")[0]["name"]];
                    iframeWin.initForm(data);
                }, function () {
                    _table.reload();
                })
            }

            function del(id) {
                okLayer.confirm("确定要删除吗？", function () {
                    okUtils.ajax(okMock.api.baseUrl + "movie/remove", "post", {idsStr: id}, true).done(function (response) {
                        okUtils.tableSuccessMsg(response.msg);
                    }).fail(function (error) {
                        console.log(error)
                    });
                })
            }

        },



        add : function () {
            okUpload.imgUpload();
            laydate.render({elem: "#moviceReleaseTime", type: "datetime"});

            form.verify({
                moviceReleaseTimeVerify: [/^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-8]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))(\s(([01]\d{1})|(2[0123])):([0-5]\d):([0-5]\d))?$/, '日期格式不正确']
            });

            form.on("submit(add)", function (data) {
                okUtils.ajax(okMock.api.baseUrl + "movie/save", "post", data.field, true).done(function (response) {
                    console.log(response);
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

            okUpload.initImg(initData.moviceLocalUrl);
            okUpload.imgUpload();
            form.val("filter", initData);

            laydate.render({elem: "#moviceReleaseTime", type: "datetime"});

            form.verify({
                moviceReleaseTimeVerify: [/^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-8]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))(\s(([01]\d{1})|(2[0123])):([0-5]\d):([0-5]\d))?$/, '日期格式不正确']
            });

            form.on("submit(edit)", function (data) {
                okUtils.ajax(okMock.api.baseUrl + "movie/modify", "put", data.field, true).done(function (response) {
                    console.log(response);
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

    exprots("movie", movie);
})
