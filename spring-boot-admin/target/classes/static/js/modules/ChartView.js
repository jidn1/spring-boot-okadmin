layui.define(["element", "jquery", "table", "laypage", "okMock","okLayer"], function (exprots) {
    let table = layui.table;
    let okLayer = layui.okLayer;
    let okMock = layui.okMock;
    let laypage = layui.laypage;
    let $ = layui.jquery;
    okLoading.close($);

    var chartview = {

        list:function () {

            var page=1; //设置首页页码
            var limit=18;  //设置一页显示的条数
            var total = 100;    //总条数
                $.ajax({
                    type:"post",
                    url: okMock.api.baseUrl + "pornHub/list",//对应controller的URL
                    async:false,
                    dataType: 'json',
                    data:{
                        "page":page,
                        "limit":limit,
                    },
                    success:function(res){
                        total=res.count;  //设置总条数
                        console.log("total=="+total)
                        var list=res.data;
                        var html='';
                        for(var i=0;i<list.length;i++){
                            html+= '<div class="layui-col-md2 layui-col-sm4">';
                            html+='<div class="cmdlist-container" style="height: 214px;">';
                            html+='<a href="javascript:;">';
                            html+='<img src="'+list[i].picture+'" onerror="nofind(this)">';
                            html+='</a>';
                            html+='<a href="javascript:;">';
                            html+='<div class="cmdlist-text">';
                            html+='<p class="info">'+list[i].name+'</p>';
                            html+='<div class="price">'+list[i].last+'</p>';
                            //html+='<span class="flow"><i class="layui-icon layui-icon-rate"></i>433</span>';
                            html+='</div>';
                            html+='</div>';
                            html+='</a>';
                            html+='</div>';
                            html+='</div>';
                        }
                        $(".orderList").empty().append(html)
                    }
                });


                laypage.render({
                    elem: 'pageLay',
                    limit: 18,
                    count: total,
                    jump: function(obj, first) {
                        //obj包含了当前分页的所有参数，比如：
                        page = obj.curr;  //改变当前页码
                        limit = obj.limit;
                        //首次不执行
                        if (!first) {

                            $.ajax({
                                type:"post",
                                url: okMock.api.baseUrl + "pornHub/list",//对应controller的URL
                                async:false,
                                dataType: 'json',
                                data:{
                                    "page":page,
                                    "limit":limit,
                                },
                                success:function(res){
                                    total=res.count;  //设置总条数
                                    var list=res.data;
                                    var html='';
                                    for(var i=0;i<list.length;i++){
                                        html+= '<div class="layui-col-md2 layui-col-sm4">';
                                        html+='<div class="cmdlist-container" style="height: 214px;">';
                                        html+='<a href="javascript:;">';
                                        html+='<img src="'+list[i].picture+'" onerror="nofind(this)">';
                                        html+='</a>';
                                        html+='<a href="javascript:;">';
                                        html+='<div class="cmdlist-text">';
                                        html+='<p class="info">'+list[i].name+'</p>';
                                        html+='<div class="price">'+list[i].last+'</p>';
                                        //html+='<span class="flow"><i class="layui-icon layui-icon-rate"></i>433</span>';
                                        html+='</div>';

                                        html+='</div>';
                                        html+='</a>';
                                        html+='</div>';
                                        html+='</div>';
                                    }
                                    $(".orderList").empty().append(html)
                                }
                            });
                        }
                    }
                });




        }

    };

    exprots("chartview", chartview);
})
