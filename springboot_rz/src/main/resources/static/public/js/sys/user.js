$(function(){
    var option = {
        url: '../sys/user/list',
        pagination: true,	//显示分页条
        sidePagination: 'server',//服务器端分页
        showRefresh: true,  //显示刷新按钮
        search: true,//是否显示搜索框
        toolbar: '#toolbar',//是否显示工具条
        striped : true,     //设置为true会有隔行变色效果
        //idField: 'menuId',
        columns: [
            {
                field: 'userId',
                title: '序号',
                width: 40,
                formatter: function(value, row, index) {
                    var pageSize = $('#table').bootstrapTable('getOptions').pageSize;
                    var pageNumber = $('#table').bootstrapTable('getOptions').pageNumber;
                    return pageSize * (pageNumber - 1) + index + 1;
                }
            },
            {checkbox:true},//复选框
            { title: '用户ID', field: 'userId'},
            {field:'username', title:'用户名'},
            { title: '密码', field: 'password',formatter:function(v){
                return "******";
                }
            },
            { title:'邮箱', field: 'email'},
            { title: '联系方式', field: 'mobile'},
            { title: '创建人', field: 'createName'},
            { title: '状态', field: 'status',formatter:function (value) {
                    if (value==1)
                        return '正常';
                    else
                        return "禁用";
                }},
            { title: '创建时间', field: 'createTime'}
        ]};
    $('#table').bootstrapTable(option);
});
var ztree;

var vm = new Vue({
	el:'#dtapp',
    //定义vue元素范围内的变量
    data:{
        showList: true,
        title: null,
        user:{}
    },
    methods:{
        del: function(){
            //得到用户选择的所有行
            //common.js
            var rows = getSelectedRows();
            if(rows == null){
                return ;
            }
            var id = 'userId';
            //提示确认框
            //layer  专业做弹出框插件
            //var f = confirm();
            layer.confirm('您确定要删除所选数据吗？', {
                btn: ['确定', '取消'] //可以无限个按钮
            }, function(index, layero){
                var ids = new Array();
                //遍历所有选择的行数据，取每条数据对应的ID
                //遍历 jquery  for(String s:list){}
                $.each(rows, function(i, row) {
                    ids[i] = row[id];
                });

               // ids = [1,2];
                $.ajax({
                    type: "POST",
                    url: "/sys/user/del",//sys/menu/del
                    data: JSON.stringify(ids),
                    success : function(r) {
                        if(r.code === 0){
                            layer.alert('删除成功');
                            $('#table').bootstrapTable('refresh');
                        }else{
                            layer.alert(r.msg);
                        }
                    },
                    error : function() {
                        layer.alert('服务器没有返回数据，可能服务器忙，请重试');
                    }
                });
            });
        },
        add: function(){
            vm.showList = false;
            vm.title = "新增";
        },
        update: function (event) {
            var id = 'userId';
            var userId = getSelectedRow()[id];// common.js定义
            if(userId == null){
                return ;
            }
            //?menuId=1
            $.get("../sys/user/info/"+userId, function(r){
                vm.showList = false;
                vm.title = "修改用户";
                vm.user = r.user;
            });
        },
        //保存或者修改
        //有菜单id则是修改 无菜单id则是新增
        saveOrUpdate: function (event) {
            var url = vm.user.userId == null ? "../sys/user/save" : "../sys/user/update";
            $.ajax({
                type: "POST",
                url: url,
                data: JSON.stringify(vm.user),//转成json字符串
                success: function(r){
                    if(r.code === 0){
                        layer.alert('操作成功', function(index){
                            layer.close(index);
                            vm.reload();
                        });
                    }else{
                        layer.alert(r.msg);
                    }
                }
            });
        },
        reload: function (event) {
            vm.showList = true;
            $("#table").bootstrapTable('refresh');
        }
    }
});