layui.define(["element", "jquery", "table", "form",  "okLayer", "okUtils", "okMock","laydate","okMd5"], function (exprots) {
    let table = layui.table;
    let form = layui.form;
    let okLayer = layui.okLayer;
    let okUtils = layui.okUtils;
    let okMock = layui.okMock;
    let laydate = layui.laydate;
    let md5 = layui.okMd5;
    let $ = layui.jquery;
    okLoading.close($);

    var desktop = {

        init:function () {
             table.render({
                elem: '#tableId',
                url: okMock.api.baseUrl + "topHot/list?type=3",
                limit: 10,
                page: true,
                size: "sm",
                cols: [[
                    {field: "title", title: "知乎", width: 700,
                        templet: function(d){
                            return '<a target="_blank" href="'+d.url+'">'+d.title+'</a>'
                        }
                    },
                ]],
                done: function (res, curr, count) {
                }
            });


            $.ajax({
                url: okMock.api.baseUrl + "/system/desktop",
                type: "post",
                data: {},
                dataType: "json",
                success: function (data) {
                    if (data.success) {
                        $(".fans-num").html(data.obj.personNumber);
                        $(".incomes-num").html(data.obj.movieNumber);
                        $(".goods-num").html(data.obj.hotNumber);
                    }
                }
            });
        }

    };

    exprots("desktop", desktop);
})
