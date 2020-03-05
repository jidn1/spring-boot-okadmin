<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>添加</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="../../css/oksub.css">
    <link rel="stylesheet" href="../../lib/css/layui.css" >
    <script type="text/javascript" src="../../lib/loading/okLoading.js"></script>
</head>
<body>
<div class="ok-body">
    <!--form表单-->
    <form class="layui-form layui-form-pane ok-form">


        <#if columns ?? >
            <#list columns as c >
                <#if c.key!="PRI">
                <div class="layui-form-item">
                    <label class="layui-form-label">${(c.comments)!(c.column)!}</label>
                    <div class="layui-input-block">
                        <input type="text" name="${(c.column)!}" autocomplete="off" class="layui-input" lay-verify="required">
                    </div>
                </div>
                </#if>
            </#list>
        </#if>

        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn" lay-submit lay-filter="add">立即提交</button>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
            </div>
        </div>
    </form>
</div>
<!--js逻辑-->
<script src="../../lib/layui/layui.js"></script>
<script>
    layui.config({base:'/js/modules/'}).use(['${CODE_MODEL_SMALL!}'],function () {
        let ${CODE_MODEL_SMALL!} = layui.${CODE_MODEL_SMALL!};
        ${CODE_MODEL_SMALL!}.add();
    });
</script>
</body>
</html>
