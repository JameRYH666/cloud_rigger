webpackJsonp([14],{"87+x":function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var l=a("FkTg"),o=a("aqKW"),r=a("xR4V"),s=a("PES7"),i={data:function(){return{showCommit:!0,dialogVisible:!1,dialogTitle:"",pageSize:10,currentPage:1,treeData:[],queryData:{},formData:{},title:"组织树",rules:{},tableColumns:[{prop:"cloudOrg.orgName",label:"组织名称",width:"150px"},{prop:"orgLeader",label:"负责人",width:""},{prop:"cloudOrg.orgTelNumber",label:"联系电话",width:"200px"},{prop:"phoneNumer",label:"单位电话",width:""},{prop:"count",label:"党员人数",width:""},{prop:"cloudOrg.orgAddress",label:"地址",width:""}],multipleSelection:null,tableData:[]}},mounted:function(){this.getTableData(),this.getPartyTree()},components:{AppTree:o.a,AppUpload:l.a},methods:{getPartyTree:function(){var e=this;Object(r.c)().then(function(t){var a=t.value;a&&"0000"==a.code?(e.treeData=[],e.treeData.push(a.data)):e.$message.error(a.message)})},handleCurrentChange:function(e){console.log(e)},treeClickHandle:function(e){console.log(e)},getTableData:function(){var e=this;Object(s.h)({}).then(function(t){var a=t.value;a&&"0000"==a.code?e.tableData=a.data.records:e.$message.error(a.message)})},add:function(){this.dialogVisible=!0,this.dialogTitle="新增党组织",this.showCommit=!0},get:function(){var e=this;if(this.multipleSelection){var t={orgId:this.multipleSelection.cloudOrg.id};Object(s.e)(t).then(function(t){var a=t.value;a&&"0000"==a.code?(e.formData=a.data,e.dialogTitle="查看"+e.multipleSelection.cloudOrg.orgName,e.showCommit=!1,e.dialogVisible=!0):e.$message.error(a.messsage)})}else this.$message.warning("请选择要查看的行")},edit:function(){var e=this;if(this.multipleSelection){var t={orgId:this.multipleSelection.cloudOrg.id};Object(s.e)(t).then(function(t){var a=t.value;a&&"0000"==a.code?(e.formData=a.data,e.dialogTitle="修改"+e.multipleSelection.cloudOrg.orgName,e.showCommit=!0,e.dialogVisible=!0):e.$message.error(a.messsage)})}else this.$message.warning("请选择要修改的行")},commit:function(){var e=this;this.formData.id?Object(s.i)(this.formData).then(function(t){var a=t.value;a&&"0000"==a.code?(e.$message.success("修改成功"),e.handleClose()):e.$message.error(a.message)}):Object(s.a)(this.formData).then(function(t){var a=t.value;a&&"0000"==a.code?(e.$message.success("新增成功"),e.handleClose()):e.$message.error(a.message)})},del:function(){var e=this;this.multipleSelection?this.$confirm("确定删除该组织吗?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(function(){Object(s.c)().then(function(t){var a=t.value;a&&"0000"==a.code?e.$message.success("删除成功!"):e.$message.error(a.message)})}).catch(function(){e.$message.info("已取消删除!")}):this.$message.warning("请选择要删除的行")},rowClickBack:function(e,t,a){this.$refs.multipleTable.clearSelection(),this.$refs.multipleTable.toggleRowSelection(e),this.multipleSelection=e},selectChange:function(e,t){this.$refs.multipleTable.clearSelection(),this.$refs.multipleTable.toggleRowSelection(t),this.multipleSelection=t},handleClose:function(){this.formData={},this.dialogVisible=!1},searchHandleClick:function(){this.get}}},n={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"containerWrap"},[a("el-container",{staticClass:"container"},[a("el-aside",{staticClass:"appTree",attrs:{width:"200px"}},[a("app-tree",{attrs:{"tree-data":e.treeData,title:e.title},on:{appTreeClick:e.treeClickHandle}})],1),e._v(" "),a("el-main",{staticClass:"rightMain"},[a("el-container",[a("el-header",{staticClass:"dzy-main-header"},[a("div",{staticClass:"btn-group-el"},[a("el-button",{attrs:{type:"primary",icon:"el-icon-plus"},on:{click:e.add}},[e._v("新增")]),e._v(" "),a("el-button",{attrs:{type:"success",icon:"el-icon-view"},on:{click:e.get}},[e._v("查看")]),e._v(" "),a("el-button",{attrs:{type:"danger",icon:"el-icon-delete"},on:{click:e.del}},[e._v("删除")]),e._v(" "),a("el-button",{attrs:{type:"warning",icon:"el-icon-edit"},on:{click:e.edit}},[e._v("修改")])],1),e._v(" "),a("div",{staticClass:"dzy-custom-search-box"},[a("div",[e._v("\n              组织名称 :\n              "),a("el-input",{staticClass:"dzy-custom-input",attrs:{size:"small",placeholder:"请输入姓名",clearable:""},model:{value:e.queryData.name,callback:function(t){e.$set(e.queryData,"name",t)},expression:"queryData.name"}})],1),e._v(" "),a("div",[a("el-button",{attrs:{size:"small",type:"primary",icon:"el-icon-search"},on:{click:e.searchHandleClick}},[e._v("查询")])],1)])]),e._v(" "),a("el-main",{staticClass:"tableMain"},[a("el-table",{ref:"multipleTable",staticStyle:{width:"100%"},attrs:{data:e.tableData,"header-cell-style":{backgroundColor:"#CF4B34",color:"#fff"},"max-height":430,stripe:""},on:{select:e.selectChange,"row-click":e.rowClickBack}},[a("el-table-column",{attrs:{type:"selection",width:"55"}}),e._v(" "),e._l(e.tableColumns,function(e,t){return a("el-table-column",{key:t,attrs:{prop:e.prop,width:e.width,label:e.label}})})],2),e._v(" "),a("el-pagination",{attrs:{background:"","current-page":e.currentPage,"page-size":e.pageSize,layout:"total, prev, pager, next, jumper",total:e.tableData.total},on:{"current-change":e.handleCurrentChange,"update:currentPage":function(t){e.currentPage=t},"update:current-page":function(t){e.currentPage=t}}})],1)],1)],1)],1),e._v(" "),a("el-dialog",{attrs:{title:e.dialogTitle,visible:e.dialogVisible,width:"50%","before-close":e.handleClose},on:{"update:visible":function(t){e.dialogVisible=t}}},[a("el-form",{ref:"formData",attrs:{model:e.formData,rules:e.rules,"label-width":"100px",size:"small"}},[a("el-divider",{attrs:{"content-position":"left"}},[e._v("基本信息")]),e._v(" "),a("el-row",{attrs:{gutter:24}},[a("el-col",{attrs:{span:8}},[a("el-form-item",{attrs:{label:"党组织名称 :",prop:"name"}},[a("el-input",{attrs:{placeholder:"党组织名称"},model:{value:e.formData.orgName,callback:function(t){e.$set(e.formData,"orgName",t)},expression:"formData.orgName"}})],1)],1),e._v(" "),a("el-col",{attrs:{span:8}},[a("el-form-item",{attrs:{label:"党组织类型 :",prop:"source"}},[a("el-input",{attrs:{placeholder:"党组织类型"},model:{value:e.formData.source,callback:function(t){e.$set(e.formData,"source",t)},expression:"formData.source"}})],1)],1),e._v(" "),a("el-col",{attrs:{span:8}},[a("el-form-item",{attrs:{label:"上级党组织 :",prop:"source"}},[a("el-input",{attrs:{placeholder:"上级党组织"},model:{value:e.formData.source,callback:function(t){e.$set(e.formData,"source",t)},expression:"formData.source"}})],1)],1)],1),e._v(" "),a("el-row",{attrs:{gutter:24}},[a("el-col",{attrs:{span:8}},[a("el-form-item",{attrs:{label:"创建时间 :",prop:"name"}},[a("el-input",{attrs:{placeholder:"创建时间"},model:{value:e.formData.orgName,callback:function(t){e.$set(e.formData,"orgName",t)},expression:"formData.orgName"}})],1)],1),e._v(" "),a("el-col",{attrs:{span:8}},[a("el-form-item",{attrs:{label:"最近修改时间 :",prop:"source"}},[a("el-input",{attrs:{placeholder:"最近修改时间"},model:{value:e.formData.source,callback:function(t){e.$set(e.formData,"source",t)},expression:"formData.source"}})],1)],1)],1),e._v(" "),a("el-divider",{attrs:{"content-position":"left"}},[e._v("位置信息")]),e._v(" "),a("el-row",{attrs:{gutter:24}},[a("el-col",{attrs:{span:24}},[a("el-form-item",{attrs:{label:"地址 :",prop:"name"}},[a("el-input",{attrs:{placeholder:"地址"},model:{value:e.formData.orgName,callback:function(t){e.$set(e.formData,"orgName",t)},expression:"formData.orgName"}})],1)],1)],1),e._v(" "),a("el-divider",{attrs:{"content-position":"left"}},[e._v("组织信息")]),e._v(" "),a("el-row",{attrs:{gutter:24}},[a("el-col",{attrs:{span:8}},[a("el-form-item",{attrs:{label:"书记姓名 :",prop:"name"}},[a("el-input",{attrs:{placeholder:"书记姓名"},model:{value:e.formData.orgName,callback:function(t){e.$set(e.formData,"orgName",t)},expression:"formData.orgName"}})],1)],1),e._v(" "),a("el-col",{attrs:{span:8}},[a("el-form-item",{attrs:{label:"副书记姓名 :",prop:"source"}},[a("el-input",{attrs:{placeholder:"副书记姓名"},model:{value:e.formData.source,callback:function(t){e.$set(e.formData,"source",t)},expression:"formData.source"}})],1)],1),e._v(" "),a("el-col",{attrs:{span:8}},[a("el-form-item",{attrs:{label:"党组织联系人 :",prop:"source"}},[a("el-input",{attrs:{placeholder:"党组织联系人"},model:{value:e.formData.source,callback:function(t){e.$set(e.formData,"source",t)},expression:"formData.source"}})],1)],1)],1),e._v(" "),a("el-row",{attrs:{gutter:24}},[a("el-col",{attrs:{span:8}},[a("el-form-item",{attrs:{label:"党组织电话 :",prop:"name"}},[a("el-input",{attrs:{placeholder:"党组织联系电话"},model:{value:e.formData.orgName,callback:function(t){e.$set(e.formData,"orgName",t)},expression:"formData.orgName"}})],1)],1),e._v(" "),a("el-col",{attrs:{span:8}},[a("el-form-item",{attrs:{label:"组织委员 :",prop:"source"}},[a("el-input",{attrs:{placeholder:"组织委员"},model:{value:e.formData.source,callback:function(t){e.$set(e.formData,"source",t)},expression:"formData.source"}})],1)],1),e._v(" "),a("el-col",{attrs:{span:8}},[a("el-form-item",{attrs:{label:"宣传委员 :",prop:"source"}},[a("el-input",{attrs:{placeholder:"宣传委员"},model:{value:e.formData.source,callback:function(t){e.$set(e.formData,"source",t)},expression:"formData.source"}})],1)],1)],1),e._v(" "),a("el-row",{attrs:{gutter:24}},[a("el-col",{attrs:{span:8}},[a("el-form-item",{attrs:{label:"纪检委员 :",prop:"name"}},[a("el-input",{attrs:{placeholder:"纪检委员"},model:{value:e.formData.orgName,callback:function(t){e.$set(e.formData,"orgName",t)},expression:"formData.orgName"}})],1)],1),e._v(" "),a("el-col",{attrs:{span:8}},[a("el-form-item",{attrs:{label:"青年委员 :",prop:"source"}},[a("el-input",{attrs:{placeholder:"青年委员"},model:{value:e.formData.source,callback:function(t){e.$set(e.formData,"source",t)},expression:"formData.source"}})],1)],1),e._v(" "),a("el-col",{attrs:{span:8}},[a("el-form-item",{attrs:{label:"党员人数 :",prop:"source"}},[a("el-input",{attrs:{placeholder:"党员人数",disabled:""},model:{value:e.formData.source,callback:function(t){e.$set(e.formData,"source",t)},expression:"formData.source"}})],1)],1)],1)],1),e._v(" "),a("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[e.showCommit?a("el-button",{on:{click:e.handleClose}},[e._v("取 消")]):e._e(),e._v(" "),e.showCommit?a("el-button",{attrs:{type:"primary"},on:{click:e.commit}},[e._v("提 交")]):e._e()],1)],1)],1)},staticRenderFns:[]};var c=a("VU/8")(i,n,!1,function(e){a("vnuZ")},"data-v-6fb6d148",null);t.default=c.exports},vnuZ:function(e,t){}});
//# sourceMappingURL=14.f34e95f1825c49e26072.js.map