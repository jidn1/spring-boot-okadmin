layui.define(["element", "jquery", "form", "layer", "okUtils", "okMock", "okUpload", "layedit"], function (exprots) {
    let form = layui.form;
    let okMock = layui.okMock;
    let layeditGroup = layui.layedit;
    let $ = layui.jquery;
    let editIndexGroup;
    var chats = {

        init: function () {


            //设置上传图片接口
            layeditGroup.set({
                uploadImage: {
                    url: okMock.api.baseUrl + '/chat/chatImg',
                    type: 'POST',
                    size: 1024 * 5
                }
            });


            //创建一个编辑器
            editIndexGroup = layeditGroup.build('LAY_demo_editorGroup', {
                tool: ['face', //删除线
                    '|', //分割线
                    'image',//图片
                    '|', //分割线
                    'mike',//录音
                    '|', //分割线
                    'strong', //加粗
                    '|', //分割线
                    'italic', //斜体
                    '|', //分割线
                    'underline', //下划线
                    '|', //分割线
                ],
                height: 120 //设置编辑器高度
            });

            form.verify({
                content: function () {
                    layeditGroup.sync(editIndexGroup)
                }
            });
            form.render();


            document.onkeydown = function (event) {
                var e = event || window.event;
                if (e && e.keyCode == 13) { //回车键的键值为13
                    app.send();
                }
            };


            $("#createGroup").click(function(){
                var groupName = $("input[name='groupName']").val();
                $.ajax({
                    url: okMock.api.baseUrl + "chat/createGroup",
                    type: "post",
                    data: {groupName: groupName},
                    dataType: "json", //回调
                    success: function (data) {
                        if (data.success) {
                            layer.msg('创建成功！', {
                                time: 1500,
                                icon: 0,
                                offset: '50px'
                            });
                        }
                    }
                });

            })


        }
    };
    exprots("group", group);


    var vueChatGroup = new Vue({
        el: '#VueGroupChat',
        data() {
            return {
                groupList: [],
                listGroupMessage: [],
                userId: sessionStorage.getItem("userId"),
                groupName: "",
            }
        },
        watch: {
            //监听消息记录，有变动就滚动至底部
            listGroupMessage: function () {
                var container = $("#msgContentGroup");
                this.$nextTick(() => {
                    container.scrollTop = container.scrollHeight;
                })
            }
        },
        mounted: function () {
            this.getGroupList();

            //连接WebSocket节点
            this.ws = new WebSocket(okMock.api.socketGroupUrl+"?group="+this.groupName);
            this.ws.onopen = this.onopen;
            this.ws.onmessage = this.onmessage;
            this.ws.onclose = this.onclose;
            this.ws.onerror = this.onerror;

            window.activeuser = this.activeuser;
            window.appendmsg = this.appendmsg;
            window.onbeforeunload = this.onbeforeunload;
        },
        methods: {
            socket:function(){

            },

            onopen: function (e) {
                var msg = JSON.stringify({cmd: 'enter_group_chat'});
                this.ws.send(msg);
            },
            onmessage: function (e) {
                console.log("e.data==" + e.data)
                var chatRecord = JSON.parse(e.data);
                that = this;
                that.listGroupMessage.push({
                    msgType: chatRecord.msgType,
                    fromUserId: chatRecord.fromUserId,
                    fromUserName: chatRecord.fromUserName,
                    sendtext: chatRecord.sendtext
                });
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

            /*添加聊天记录*/
            appendmsg(mstype, revive, send, text) {
                var that = this;
                //that.listmessage.push({msgType: mstype, fromUserId: revive, toUserId: send, sendtext: text});
                setTimeout(function () {
                    document.getElementById("msg_endGroup").scrollIntoView();
                }, 500)
            },
            /*设置点击左侧的列表的时候切换样式同时查找聊天记录*/
            selectGroup: function (item, groupName) {
                this.$nextTick(function () {
                    this.groupList.forEach(function (item) {
                        Vue.set(item, 'active', false);
                    });
                    Vue.set(item, 'active', true);
                });
                this.getGroupMessageList(groupName);
                this.groupName = groupName;
                this.socket();
            },

            /*获取群聊*/
            getGroupList: function () {
                var that = this;
                $.ajax({
                    url: okMock.api.baseUrl + "/chat/groups",
                    type: "post",
                    dataType: "json",
                    success: function (data) {
                        if (data.success) {
                            that.groupList = data.obj;
                            document.getElementById('leftcGroup').style.display = 'block';
                            document.getElementById('appLoadingGroup').style.display = 'none';
                        }
                    },
                    error: function (err) {
                        console.log("err:", err);
                    }
                });
            },
            /*获取聊天记录*/
            getGroupMessageList: function (groupName) {
                document.getElementById('waitsGroup').style.display = 'none';
                document.getElementById('wordsGroup').style.display = 'none';
                document.getElementById('appLoading2Group').style.display = 'block';
                var that = this;
                $.ajax({
                    type: 'post',
                    url: okMock.api.baseUrl + '/chat/findGroupChatHistory',
                    data: {groupName: groupName},
                    dataType: 'json',
                    success: function (data) {
                        if (data.success) {
                            that.listGroupMessage = data.obj;
                            document.getElementById('wordsGroup').style.display = 'block';
                            document.getElementById('appLoading2Group').style.display = 'none';
                            $('#msgcontentGroup').ready(
                                function () {
                                    setTimeout(function () {
                                        document.getElementById("msg_endGroup").scrollIntoView();
                                    }, 500)
                                }
                            );
                        }
                    }
                })
            },

            sendGroup: function () {
                var layeditGroup = layui.layedit;
                var message = layeditGroup.getContent(editIndexGroup);
                if (message.length == 0) {
                    layer.msg("请输入发送的内容", {
                        time: 2500,
                        icon: 2,
                        offset: "300px"
                    });
                    return;
                }
                // 将内容设置为空
                layeditGroup.setContent(editIndexGroup, "", false);
                var object = new Object();
                object["cmd"] = 'chat_group';
                object["groupName"] = this.groupName;
                object["fromUserId"] = this.userId;
                object["msgType"] = 0;
                object["sendtext"] = message;
                var jsonData = JSON.stringify(object);
                this.ws.send(jsonData);//websocket发送数据
                appendmsg("0", this.userId, this.friendUserId, message);
                document.getElementById("msg_endGroup").scrollIntoView();
            },

            //发送语音消息
            sendaudio: function (mp3) {
                if (mp3 != "" && mp3 != undefined) {
                    var object = new Object();
                    object["cmd"] = 'chat_group';
                    object["groupName"] = this.groupName;
                    object["fromUserId"] = this.userId;
                    object["msgType"] = 1;
                    object["sendtext"] = "<audio controls class=\"audio-player\"><source src=\"" + mp3 + "\" type=\"audio/mp3\"></audio>";
                    var jsonData = JSON.stringify(object);
                    this.ws.send(jsonData);
                    appendmsg("1", this.userId, this.friendUserId, "<audio controls class=\"audio-player\"><source src=\"" + mp3 + "\" type=\"audio/mp3\"></audio>");
                    document.getElementById("msg_endGroup").scrollIntoView();
                }
            },

            //将消息显示在网页上
            setMessageInnerHTML: function (innerHTML) {
                layer.msg('当前用户不在线', {
                    time: 1500,
                    icon: 0,
                    offset: '50px'
                });
                document.getElementById("msg_endGroup").scrollIntoView();
            },
            logout: function () {
                sessionStorage.removeItem("userId");
                window.location = "/logout";

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



