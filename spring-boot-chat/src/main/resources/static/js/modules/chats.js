layui.define(["element", "jquery", "form", "layer", "okUtils", "okMock", "okUpload", "layedit"], function (exprots) {
    let form = layui.form;
    let okMock = layui.okMock;
    let layedit = layui.layedit;
    let $ = layui.jquery;
    let editIndex;
    var chats = {

        init: function () {

            //设置上传图片接口
            layedit.set({
                uploadImage: {
                    url: okMock.api.baseUrl + '/chat/chatImg',
                    type: 'POST',
                    size: 1024 * 5
                }
            });
            //创建一个编辑器
            editIndex = layedit.build('LAY_demo_editor', {
                tool: ['face', //表情
                    '|',
                    'image',//图片
                    '|',
                    'mike',//录音
                    '|',
                    'video',//录音
                    '|',
                    'strong', //加粗
                    '|',
                    'italic', //斜体
                    '|',
                    'underline', //下划线
                    '|',
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
                    app.send();
                }
            };


        }
    };
    exprots("chats", chats);

    var info = new Vue({
        el: '#userinfo',
        data() {
            return {
                userinfo: Object,
                pathImg: "",
            }
        },
        mounted: function () {
            window.setUserInfo = this.setUserInfo;
        },
        methods: {
            lookuser: function () {
                var username = $("input[name='searchUserName']").val();
                $.ajax({
                    url: okMock.api.baseUrl + "chat/searchFriend",
                    type: "post",
                    data: {username: username},
                    dataType: "json", //回调
                    success: function (data) {
                        if (data.success) {
                            setUserInfo(data.obj);
                        } else {
                            setUserInfo(null);
                        }
                    }
                });
                return false;
            },
            /**添加好友查询用户信息*/
            setUserInfo: function (info) {
                var that = this;
                that.userinfo = info;
                $("#info").show();
            },
            adduser: function (username) {
                $.ajax({
                    url: okMock.api.baseUrl + "chat/addFriend",
                    data: {username: username},
                    type: "post",
                    dataType: "json", //回调
                    success: function (data) {
                        if (!data.success) {
                            layer.msg(data.msg, {
                                time: 1500,
                                icon: 2,
                                offset: '350px'
                            });
                        } else {
                            layer.msg(data.msg, {
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

//好友一对一聊天
    var app = new Vue({
        el: '#vuechat',
        data() {
            return {
                listnickname: [],
                listmessage: [],
                userId: sessionStorage.getItem("userId"),
                friendUserId: '',
            }
        },
        watch: {
            //监听消息记录，有变动就滚动至底部
            listmessage: function () {
                var container = $("#msgcontent");
                this.$nextTick(() => {
                    container.scrollTop = container.scrollHeight;
                })
            }
        },
        mounted: function () {
            this.getlistnickname();
            //连接WebSocket节点
            this.ws = new WebSocket(okMock.api.socketUrl);
            this.ws.onopen = this.onopen;
            this.ws.onmessage = this.onmessage;
            this.ws.onclose = this.onclose;
            this.ws.onerror = this.onerror;

            window.alertnote = this.alertnote;
            window.activeuser = this.activeuser;
            window.appendmsg = this.appendmsg;
            window.getlistnickname = this.getlistnickname;
            window.onbeforeunload = this.onbeforeunload;
            window.sendaudio = this.sendaudio;
            window.sendVideoPing = this.sendVideoPing;
            window.getFriendUserId = this.getFriendUserId;
        },
        methods: {
            onopen: function (e) {
                var msg = JSON.stringify({cmd: 'enter_chatting'});
                this.ws.send(msg);
            },
            onmessage: function (e) {
                if (e.data != "当前用户不在线") {
                    if(e.data == "video"){
                        that = this;
                        that.videoCall();
                    } else {
                        var chatRecord = JSON.parse(e.data);
                        that = this;
                        that.listmessage.push({
                            msgType: chatRecord.msgType,
                            fromUserId: chatRecord.fromUserId,
                            toUserId: chatRecord.toUserId,
                            sendtext: chatRecord.sendtext
                        });
                    }
                } else {
                    that = this;
                    that.setMessageInnerHTML(e.data)
                }

            },
            onclose: function (e) {

            },
            onerror: function (e) {

            },
            onbeforeunload: function () {
                //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
                this.ws.close();
            },
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
                //that.listmessage.push({msgType: mstype, fromUserId: revive, toUserId: send, sendtext: text});
                setTimeout(function () {
                    document.getElementById("msg_end").scrollIntoView();
                }, 500)
            },
            /*设置点击左侧的列表的时候切换样式同时查找聊天记录*/
            selectStyle: function (item, friendUserId) {
                this.$nextTick(function () {
                    this.listnickname.forEach(function (item) {
                        Vue.set(item, 'active', false);
                    });
                    Vue.set(item, 'active', true);
                });
                this.getMessageList(friendUserId);
                this.friendUserId = friendUserId;
            },
            /*获取左侧的聊天窗口*/
            getlistnickname: function () {
                var that = this;
                $.ajax({
                    url: okMock.api.baseUrl + "/chat/friends",
                    type: "post",
                    dataType: "json",
                    success: function (data) {
                        if (data.success) {
                            that.listnickname = data.obj;
                            document.getElementById('leftc').style.display = 'block';
                            document.getElementById('appLoading').style.display = 'none';
                        }
                    },
                    error: function (err) {
                        console.log("err:", err);
                    }
                });
            },
            /*获取聊天记录*/
            getMessageList: function (userId) {
                document.getElementById('waits').style.display = 'none';
                document.getElementById('words').style.display = 'none';
                document.getElementById('appLoading2').style.display = 'block';
                var that = this;
                $.ajax({
                    type: 'post',
                    url: okMock.api.baseUrl + '/chat/findChatHistory',
                    data: {friendUserId: userId},
                    dataType: 'json',
                    success: function (data) {
                        if (data.success) {
                            that.listmessage = data.obj;
                            document.getElementById('words').style.display = 'block';
                            document.getElementById('appLoading2').style.display = 'none';
                            $('#msgcontent').ready(
                                function () {
                                    setTimeout(function () {
                                        document.getElementById("msg_end").scrollIntoView();
                                    }, 500)
                                }
                            );
                        }
                    }
                })
            },

            send: function () {
                var layedit = layui.layedit;
                var message = layedit.getContent(editIndex);
                if (message.length == 0) {
                    layer.msg("请输入发送的内容", {
                        time: 2500,
                        icon: 2,
                        offset: "300px"
                    });
                    return;
                }
                // 将内容设置为空
                layedit.setContent(editIndex, "", false);
                var object = new Object();
                object["cmd"] = 'chatting';
                object["toUserId"] = this.friendUserId;
                object["fromUserId"] = this.userId;
                object["msgType"] = 0;
                object["sendtext"] = message;
                var jsonData = JSON.stringify(object);
                this.ws.send(jsonData);//websocket发送数据
                appendmsg("0", this.userId, this.friendUserId, message);
                document.getElementById("msg_end").scrollIntoView();
            },

            //发送语音消息
            sendaudio: function (mp3) {
                if (mp3 != "" && mp3 != undefined) {
                    var object = new Object();
                    object["cmd"] = 'chatting';
                    object["toUserId"] = this.friendUserId;
                    object["fromUserId"] = this.userId;
                    object["msgType"] = 1;
                    object["sendtext"] = "<video controls class=\"audio-player\" style='height: 56px;width: 300px'><source src=\"" + okMock.api.fileUrl + mp3 + "\" type=\"audio/mp3\"></video>";
                    var jsonData = JSON.stringify(object);
                    this.ws.send(jsonData);
                    appendmsg("1", this.userId, this.friendUserId, "<video controls class=\"audio-player\" style='height: 56px;width: 300px'><source src=\"" + okMock.api.fileUrl + mp3 + "\" type=\"audio/mp3\"></video>");
                    document.getElementById("msg_end").scrollIntoView();
                }
            },

            //将消息显示在网页上
            setMessageInnerHTML: function (innerHTML) {
                layer.msg('当前用户不在线', {
                    time: 1500,
                    icon: 0,
                    offset: '50px'
                });
                document.getElementById("msg_end").scrollIntoView();
            },
            videoCall:function(){
                layer.confirm('对方请求视频通话?', function(index){
                    layer.open({
                        title: "视频",
                        type: 2,
                        area: ["90%", "90%"],
                        content: "/chat/video?type=answer",
                        zIndex: layer.zIndex,
                    });
                    layer.close(index);
                });

            },
            sendVideoPing:function(){
                var object = new Object();
                object["cmd"] = 'chat_video';
                object["toUserId"] = this.friendUserId;
                object["fromUserId"] = this.userId;
                object["msgType"] = 0;
                object["sendtext"] = "video";
                var jsonData = JSON.stringify(object);
                this.ws.send(jsonData);//websocket发送数据
            },

            getFriendUserId:function(){
                return this.friendUserId;
            },

            logout: function () {
                $.ajax({
                    type: 'post',
                    url: okMock.api.baseUrl + '/destroy',
                    dataType: 'json',
                    success: function (data) {
                        if (data.success) {
                            sessionStorage.removeItem("userId");
                            window.location = okMock.api.baseUrl + "login";
                        }
                    }
                })
            }


// method end
        }
    })


//layui面板刷新保留在当前面板
    $(".layui-tab-title li").click(function () {
        var picTabNum = $(this).index();
        sessionStorage.setItem("picTabNum", picTabNum);
    });
    $(function () {
        var getPicTabNum = sessionStorage.getItem("picTabNum");
        $(".layui-tab-title li").eq(getPicTabNum).addClass("layui-this").siblings().removeClass("layui-this");
        $(".layui-tab-content>div").eq(getPicTabNum).addClass("layui-show").siblings().removeClass("layui-show");
    })

})



