layui.define(["element", "jquery", "form", "okLayer", "okUtils", "okMock","okMd5"], function (exprots) {
    let form = layui.form;
    let okLayer = layui.okLayer;
    let okUtils = layui.okUtils;
    let okMock = layui.okMock;
    let md5 = layui.okMd5;
    let $ = layui.jquery;
    okLoading.close($);

    var systemuser = {

        setPassword : function () {

            form.verify({
                pass: [
                    /^[\S]{6,16}$/
                    , '密码必须6到16位，且不能出现空格'
                ],
                confirmPwd: function (value, item) {
                    if (!new RegExp($("#oldPwd").val()).test(value)) {
                        return "两次输入密码不一致，请重新输入！";
                    }
                },
            });

            form.on("submit(edit)", function (data) {
                data.field.oldPwd = md5(data.field.oldPwd);
                data.field.newPwd = md5(data.field.newPwd);
                data.field.confirmNewPwd = md5(data.field.confirmNewPwd);
                okUtils.ajax(okMock.api.baseUrl + "system/setPassword", "post", data.field, true).done(function (response) {
                    okLayer.greenTickMsg("修改密码成功", function () {
                        window.location = "/logout";
                    });
                }).fail(function (error) {
                    console.log(error)
                });
                return false;
            });
        }


    };

    exprots("systemuser", systemuser);
})