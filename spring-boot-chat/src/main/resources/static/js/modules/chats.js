layui.define(["element", "jquery", "form", "okLayer", "okUtils", "okMock", "okUpload", "layedit"], function (exprots) {
    let table = layui.table;
    let form = layui.form;
    let layer = layui.okLayer;
    let okUtils = layui.okUtils;
    let okMock = layui.okMock;
    let okUpload = layui.okUpload;
    let layedit = layui.layedit;
    let $ = layui.jquery;

    var chats = {

        init: function () {

            form.on('submit(lookuser)', function (data) {
                $.ajax({
                    url: okMock.api.baseUrl + "/chat/lkuser/" + data.field.username,
                    data: "",
                    contentType: "application/json;charset=UTF-8", //发送数据的格式
                    type: "post",
                    dataType: "json", //回调
                    beforeSend: function () {
                        layer.load(1, {
                            content: '查询中...',
                            success: function (layero) {
                                layero.find('.layui-layer-content').css({
                                    'padding-top': '39px',
                                    'width': '60px'
                                });
                            }
                        });
                    },
                    complete: function () {
                        layer.closeAll('loading');
                    },
                    success: function (data) {
                        if (data.status != 200) {
                            layer.msg(data.message, {
                                time: 1500,
                                icon: 2,
                                offset: '350px'
                            });
                        } else {
                            setUserInfo(data.data.userinfo);
                        }
                    }
                });
                return false;
            });

            var editIndex;
            //设置上传图片接口
            layedit.set({
                uploadImage: {
                    url: okMock.api.baseUrl + '/chat/upimg' //接口url
                    , type: 'POST' //默认post
                    , size: 1024 * 5
                }
            });
            //创建一个编辑器
            editIndex = layedit.build('LAY_demo_editor', {
                tool: ['face' //删除线
                    , 'image'
                    , 'strong' //加粗
                    , 'italic' //斜体
                    , 'underline' //下划线
                    , '|' //分割线
                    , '|' //分割线
                    , '|' //分割线
                    , '|' //分割线
                    , '|' //分割线
                    , '|' //分割线
                ],
                height: 120 //设置编辑器高度
            });

            form.verify({
                content: function () {
                    layedit.sync(editIndex)
                }
            });
            form.render();


            document.onkeydown = function (event) {
                var e = event || window.event;
                if (e && e.keyCode == 13) { //回车键的键值为13
                    send();
                }
            };


        }
    };
    exprots("chats", chats);


    var info = new Vue({
        el: '#userinfo',
        data() {
            return {
                userinfo: []
            }
        },
        mounted: function () {
            window.setUserInfo = this.setUserInfo;
        },
        methods: {
            /**添加好友查询用户信息*/
            setUserInfo: function (info) {
                var that = this;
                that.userinfo = info;
                $("#info").show();
            }, adduser: function (uid) {
                $.ajax({
                    url: okMock.api.baseUrl + "/chat/adduser/" + uid,
                    data: "",
                    contentType: "application/json;charset=UTF-8", //发送数据的格式
                    type: "post",
                    dataType: "json", //回调
                    beforeSend: function () {
                        layer.load(1, {
                            content: '添加中...',
                            success: function (layero) {
                                layero.find('.layui-layer-content').css({
                                    'padding-top': '39px',
                                    'width': '60px'
                                });
                            }
                        });
                    },
                    complete: function () {
                        layer.closeAll('loading');
                    },
                    success: function (data) {
                        if (data.status != 200) {
                            layer.msg(data.message, {
                                time: 1500,
                                icon: 2,
                                offset: '350px'
                            });
                        } else {
                            layer.msg(data.message, {
                                time: 1000,
                                icon: 1,
                                offset: '350px'
                            }, function () {
                                getlistnickname();//刷新好友列表
                            });
                        }
                    }
                });
            }
        }
    })


    var actuserid = null;
    var app = new Vue({
        el: '#vuechat',
        data() {
            return {
                listnickname: [],
                listmessage: [],
                loginusername: ""
            }
        },
        mounted: function () {
            this.getlistnickname();
            window.alertnote = this.alertnote;
            window.activeuser = this.activeuser;
            window.appendmsg = this.appendmsg;
            window.getlistnickname = this.getlistnickname;
        },
        methods: {
            /**点击预览图片*/
            openimg: function (id) {
                var imgs = document.getElementById(id).getElementsByTagName("img");
                var pho = "";
                for (var i = 0; i < imgs.length; i++) {
                    var img = '<img src="' + $(imgs[i]).attr("src") + '" style="width:100%;">'
                    layer.open({
                        type: 1,
                        title: false, //不显示标题
                        closeBtn: 0, //不显示关闭按钮
                        shade: 0.6,//遮罩透明度
                        shadeClose: true, //开启遮罩关闭
                        anim: 0,
                        content: img
                    });
                }
            },
            /*新信息提示*/
            alertnote: function (msgtouid) {
                var that = this;
                for (var i = 0; i < that.listnickname.length; i++) {
                    console.log(that.listnickname[i].userid);
                    if (that.listnickname[i].userid === msgtouid) {
                        layer.msg(that.listnickname[i].nickname + '发来一条信息', {
                            time: 1500,
                            icon: 0,
                            offset: '50px'
                        });
                    }
                }
            },
            /*添加聊天记录*/
            appendmsg(mstype, revive, send, text) {
                var that = this;
                that.listmessage.push({msgtype: mstype, reciveuserid: revive, senduserid: send, sendtext: text});
                setTimeout(function () {
                    document.getElementById("msg_end").scrollIntoView();
                }, 500)
            },
            /*设置点击左侧的列表的时候切换样式同时查找聊天记录*/
            selectStyle: function (item, acuserid) {
                this.$nextTick(function () {
                    this.listnickname.forEach(function (item) {
                        Vue.set(item, 'active', false);
                    });
                    Vue.set(item, 'active', true);
                });
                this.getMessageList(acuserid);
                actuserid = acuserid;
            },
            /*获取左侧的聊天窗口*/
            getlistnickname: function () {
                var that = this;
                $.ajax({
                    url: okMock.api.baseUrl + "/chat/lkfriends",
                    data: "",
                    contentType: "application/json;charset=UTF-8",
                    type: "post",
                    dataType: "json",
                    success: function (data) {
                        that.listnickname = data;
                        document.getElementById('leftc').style.display = 'block';
                        document.getElementById('appLoading').style.display = 'none';
                    },
                    error: function (err) {
                        console.log("err:", err);
                    }
                });
            },
            /*获取聊天记录*/
            getMessageList: function (username) {
                document.getElementById('waits').style.display = 'none';
                document.getElementById('words').style.display = 'none';
                document.getElementById('appLoading2').style.display = 'block';
                var that = this;
                $.ajax({
                    type: 'post',
                    url: okMock.api.baseUrl + '/chat/lkuschatmsg/' + username,
                    dataType: 'json',
                    contentType: "application/json;charset=UTF-8",
                    success: function (msg) {
                        that.listmessage = msg;
                        document.getElementById('words').style.display = 'block';
                        document.getElementById('appLoading2').style.display = 'none';
                        $('#msgcontent').ready(
                            function () {
                                setTimeout(function () {
                                    document.getElementById("msg_end").scrollIntoView();
                                }, 500)
                            }
                        );
                    },
                    error: function (err) {
                        console.log("err:", err);
                    }
                })
            }
        }
    })


})

/*控制鼠标移动到div显示或者隐藏div的滚动*/
function leftscroll(id) {
    if (id == 1) {
        document.getElementById("leftscroll").style.overflowY = "auto";
        document.getElementById("leftscroll").style.overflowX = "hidden";
    } else if (id == 2) {
        document.getElementById("leftscroll").style.overflowY = "hidden";
        document.getElementById("leftscroll").style.overflowX = "hidden";
    } else if (id == 3) {
        document.getElementById("msgcontent").style.overflowY = "auto";
        document.getElementById("msgcontent").style.overflowX = "hidden";
    } else if (id == 4) {
        document.getElementById("msgcontent").style.overflowY = "hidden";

        document.getElementById("msgcontent").style.overflowX = "hidden";
    }
}
