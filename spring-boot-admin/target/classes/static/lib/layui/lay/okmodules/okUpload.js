layui.define(["layer","upload","okMock"], function (exprots) {
    var $ = layui.jquery;
    let upload = layui.upload;
    let okMock = layui.okMock;

    var okUpload = {

        /**
         * upload 二次封装
         * @param type
         * @param params
         * @param load
         */
        imgUpload: function () {
            upload.render({
                elem: '#submitpicture',
                url: okMock.api.baseUrl + "file/upload",
                size: 2048,
                bindAction: '#submitpicture',
                before: function(obj){
                    //预读本地文件示例，不支持ie8
                    obj.preview(function(index, file, result){
                        $('.okadmin_images').attr('src', result); //图片链接（base64）
                    });
                },
                done: function(res){
                    //如果上传失败
                    if(res.code > 0){
                        return layer.msg('上传失败');
                    }
                    //上传成功
                    $('.upload_filePath').val(res.obj);
                },
                // error: function(){
                //     //演示失败状态，并实现重传
                //     var demoText = $('#upload_error');
                //     demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
                //     demoText.find('.demo-reload').on('click', function(){
                //         imgUpload.upload();
                //     });
                // }
            });
        },

        fileUpload:function(){

        },

        initImg : function (url) {
            if(url.indexOf("http") >= 0){
                $(".okadmin_images").attr('src', url);
            } else {
                $(".okadmin_images").attr('src', okMock.api.fileUrl +url);
            }
        }

    };
    exprots("okUpload", okUpload);
});
