layui.define(["element", "jquery", "table", "form",  "okLayer", "okUtils", "okMock", "tree"], function (exprots) {
    let table = layui.table;
    let form = layui.form;
    let okLayer = layui.okLayer;
    let okUtils = layui.okUtils;
    let okMock = layui.okMock;
    let tree = layui.tree;
    let $ = layui.jquery;
    okLoading.close($);


    var systemMenu = {

        list : function(){

            okUtils.ajax(okMock.api.baseUrl + "systemMenu/tree", "post", null, true).done(function (response) {
                tree.render({
                    elem: '#permissionTree',
                    data: response.data,
                    showCheckbox: true,
                    id: 'demoId1',
                    isJump: true,
                    click: function (obj) {
                        var data = obj.data;
                        initPermissionTable(data.mkey);

                    }
                });
            }).fail(function (error) {
                console.log(error)
            });

            function initPermissionTable(key) {
                let _table = table.render({
                    elem: '#tableId',
                    url: okMock.api.baseUrl + "systemMenu/list",
                    limit: 20,
                    page: true,
                    toolbar: true,
                    toolbar: "#toolbarTpl",
                    size: "sm",
                    where:{"mkey":key},
                    cols: [[
                        {type: "checkbox", fixed: "left"},
                        {field: "name", title: "名称", width: 224},
                        {field: "resourceType", title: "类型", width: 93, templet: "#resourceType"},
                        {field: "url", title: "路径", width: 395},
                        {field: "icon", title: "图标", width: 100,templet: "#icon"},
                        {title: "操作", width: 100, align: "center", fixed: "right", templet: "#operationTpl"}
                    ]],
                    done: function (res, curr, count) {
                        //console.info(res, curr, count);
                    }
                });
                $("input[name='parentKey']").val(key);
            }

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
                        okUtils.ajax(okMock.api.baseUrl + "systemMenu/remove", "post", {idsStr: idsStr}, true).done(function (response) {
                            okUtils.tableSuccessMsg(response.msg);
                        }).fail(function (error) {
                            console.log(error)
                        });
                    }
                });
            }

            function add() {
                okLayer.open("添加", "systemmenuadd.html", "90%", "90%", null, null)
            }

            function edit(data) {
                okLayer.open("更新", "systemmenumodify.html", "90%", "90%", function (layero) {
                    let iframeWin = window[layero.find("iframe")[0]["name"]];
                    iframeWin.initForm(data);
                }, function () {
                    _table.reload();
                })
            }

            function del(id) {
                okLayer.confirm("确定要删除吗？", function () {
                    okUtils.ajax(okMock.api.baseUrl + "systemMenu/remove", "post", {idsStr: id}, true).done(function (response) {
                        okUtils.tableSuccessMsg(response.msg);
                    }).fail(function (error) {
                        console.log(error)
                    });
                })
            }

        },



        add : function () {
            var parentKey = $("input[name='parentKey']").value;
            console.log("parentKey"+parentKey)
            $("input[name='pkey']").val(parentKey);
            form.on("submit(add)", function (data) {
                okUtils.ajax(okMock.api.baseUrl + "systemMenu/save", "post", data.field, true).done(function (response) {
                    okLayer.greenTickMsg("添加成功", function () {
                        parent.layer.close(parent.layer.getFrameIndex(window.name));
                    });
                }).fail(function (error) {
                    console.log(error)
                });
                return false;
            });

            $("input[name='icon']").click(function () {
                layer.open({
                    type: 1,
                    title :"图标",
                    area: ['95%',  "80%"],
                    content: $("#selectIcon_div"),
                    closeBtn : 1,
                    maxmin: true,
                    shadeClose : false,
                    success: function(layero, index){
                        $("#selectIcon_div").empty();
                        $.ajax({
                            type : 'get',
                            url : 'icon.html',
                            dataType : "html",
                            success : function(data) {
                                //1，渲染弹出面板
                                $("#selectIcon_div").append($(data));
                                $("ul li").on("click",function(){
                                    $("input[name='icon']").val($(this).find('.code-name').text());
                                    layer.closeAll();
                                    $("#selectIcon_div").css("display","none");
                                    $("#selectIcon_div").empty();
                                })
                            },
                            error : function() {
                                layer.closeAll();
                            }
                        });
                    },
                    cancel : function(){
                        $("#selectIcon_div").addClass("hide")
                    }
                });

            })

        },


        modify:function () {
            form.val("filter", initData);

            form.on("submit(edit)", function (data) {
                okUtils.ajax(okMock.api.baseUrl + "systemMenu/modify", "post", data.field, true).done(function (response) {
                    okLayer.greenTickMsg("编辑成功", function () {
                        parent.layer.close(parent.layer.getFrameIndex(window.name));
                    });
                }).fail(function (error) {
                    console.log(error)
                });
                return false;
            });


            $("input[name='icon']").click(function () {
                layer.open({
                    type: 1,
                    title :"图标",
                    area: ['95%',  "80%"],
                    content: $("#selectIcon_div"),
                    closeBtn : 1,
                    maxmin: true,
                    shadeClose : false,
                    success: function(layero, index){
                        $("#selectIcon_div").empty();
                        $.ajax({
                            type : 'get',
                            url : 'icon.html',
                            dataType : "html",
                            success : function(data) {
                                //1，渲染弹出面板
                                $("#selectIcon_div").append($(data));
                                $("ul li").on("click",function(){
                                    $("input[name='icon']").val($(this).find('.code-name').text());
                                    layer.closeAll();
                                    $("#selectIcon_div").attr("style","display:none");
                                    $("#selectIcon_div").empty();
                                })
                            },
                            error : function() {
                                layer.closeAll();
                            }
                        });
                    },
                    cancel : function(){
                        $("#selectIcon_div").addClass("hide")
                    }
                });

            })
        }
    };

    exprots("systemMenu", systemMenu);
})

pkey = "";
