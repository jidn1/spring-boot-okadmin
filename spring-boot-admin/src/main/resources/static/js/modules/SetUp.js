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

    var setup = {

        modify:function () {

            okUpload.imgUpload();

            let initData;
            $.ajax({
                url: okMock.api.baseUrl + "/config/baseConfig",
                type: "post",
                dataType: "json",
                success: function (data) {
                    if (data.success) {
                        initData = JSON.parse(data.obj);
                        okUpload.initImg(initData.headportrait);
                        form.val("filter", initData);
                    }
                }
            });

            form.on("submit(edit)", function (data) {
                okUtils.ajax(okMock.api.baseUrl + "config/baseConfigModify","post",data.field,true).done(function (response) {
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

    exprots("setup", setup);
})
