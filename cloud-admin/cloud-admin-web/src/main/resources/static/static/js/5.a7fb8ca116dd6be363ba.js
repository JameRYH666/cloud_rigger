webpackJsonp([5],{"162o":function(t,e,a){(function(t){var s=void 0!==t&&t||"undefined"!=typeof self&&self||window,r=Function.prototype.apply;function n(t,e){this._id=t,this._clearFn=e}e.setTimeout=function(){return new n(r.call(setTimeout,s,arguments),clearTimeout)},e.setInterval=function(){return new n(r.call(setInterval,s,arguments),clearInterval)},e.clearTimeout=e.clearInterval=function(t){t&&t.close()},n.prototype.unref=n.prototype.ref=function(){},n.prototype.close=function(){this._clearFn.call(s,this._id)},e.enroll=function(t,e){clearTimeout(t._idleTimeoutId),t._idleTimeout=e},e.unenroll=function(t){clearTimeout(t._idleTimeoutId),t._idleTimeout=-1},e._unrefActive=e.active=function(t){clearTimeout(t._idleTimeoutId);var e=t._idleTimeout;e>=0&&(t._idleTimeoutId=setTimeout(function(){t._onTimeout&&t._onTimeout()},e))},a("mypn"),e.setImmediate="undefined"!=typeof self&&self.setImmediate||void 0!==t&&t.setImmediate||this&&this.setImmediate,e.clearImmediate="undefined"!=typeof self&&self.clearImmediate||void 0!==t&&t.clearImmediate||this&&this.clearImmediate}).call(e,a("DuR2"))},"I4K/":function(t,e){},IWTM:function(t,e){},dAOO:function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var s=a("Xxa5"),r=a.n(s),n=a("exGp"),o=a.n(n),i={name:"JeeCard",props:{icon:{type:String,default:""},title:{type:String,default:"标题"}},data:function(){return{show:!0}}},c={render:function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("el-card",{staticClass:"box-card"},[a("div",{staticClass:"box-card-header clearfix",attrs:{slot:"header"},slot:"header"},[a("div",{staticClass:"fl"},[a("span",{class:[t.icon]},[t._v(t._s(t.title))])]),t._v(" "),a("div",{staticClass:"fr"},[a("el-button",{directives:[{name:"show",rawName:"v-show",value:t.show,expression:"show"}],staticClass:"fa fa-minus",attrs:{type:"text"},on:{click:function(e){t.show=!1}}}),t._v(" "),a("el-button",{directives:[{name:"show",rawName:"v-show",value:!t.show,expression:"!show"}],staticClass:"fa fa-plus",attrs:{type:"text"},on:{click:function(e){t.show=!0}}})],1)]),t._v(" "),a("div",{staticClass:"box-card-content",style:{height:t.show?"auto":"0"}},[t._t("default")],2)])},staticRenderFns:[]};var v=a("VU/8")(i,c,!1,function(t){a("I4K/")},"data-v-c7f4ad8c",null).exports,d=a("5XFP"),_=a("162o"),l={name:"ServerStatus",components:{JeeCard:v},data:function(){return{diskInfos:[],cpu:{},mem:{},jvm:{},systemInfo:{},javaArgs:[],timer:null}},created:function(){var t=this;this.getMonitorDiskInfo(),this.getMonitorSystemInfo(),this.getMonitorServerInfo(),this.timer=Object(_.setInterval)(function(){t.getMonitorServerInfo()},2e3)},beforeDestroy:function(){Object(_.clearInterval)(this.timer)},methods:{getMonitorDiskInfo:function(){var t=this;return o()(r.a.mark(function e(){var a;return r.a.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,Object(d.b)();case 2:"0000"===(a=e.sent).code&&(t.diskInfos=a.data);case 4:case"end":return e.stop()}},e,t)}))()},getMonitorServerInfo:function(){var t=this;return o()(r.a.mark(function e(){var a,s,n,o,i;return r.a.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,Object(d.c)();case 2:"0000"===(a=e.sent).code&&(s=a.data,n=s.cpu,o=s.mem,i=s.jvm,t.cpu=n||{},t.mem=o||{},t.jvm=i||{});case 4:case"end":return e.stop()}},e,t)}))()},getMonitorSystemInfo:function(){var t=this;return o()(r.a.mark(function e(){var a,s;return r.a.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,Object(d.d)();case 2:"0000"===(a=e.sent).code&&(s=(s=a.data.javaArgs).slice(1).slice(0,-1),t.javaArgs=s.split(", "),t.systemInfo=a.data);case 4:case"end":return e.stop()}},e,t)}))()}}},u={render:function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"jee-page server-status"},[a("el-row",{staticClass:"first-section",attrs:{gutter:20}},[a("el-col",{attrs:{span:8}},[a("jee-card",{attrs:{title:"cpu",icon:"icon-speedometer"}},[a("table",{staticClass:"table table-striped table-hover table-center"},[a("tbody",[a("tr",[a("th",{attrs:{width:"50%"}},[t._v("属性")]),t._v(" "),a("th",{attrs:{width:"50%"}},[t._v("值")])]),t._v(" "),a("tr",[a("td",[t._v("核心数")]),t._v(" "),a("td",{staticClass:"rtInfo",attrs:{"data-key":"cpu.cpuNum"}},[t._v(t._s(t.cpu.cpuNum))])]),t._v(" "),a("tr",[a("td",[t._v("最大功率")]),t._v(" "),a("td",{staticClass:"rtInfo",attrs:{"data-key":"cpu.maxGhz"}},[t._v(t._s(t.cpu.maxGhz))])]),t._v(" "),a("tr",[a("td",[t._v("实时频率")]),t._v(" "),a("td",{staticClass:"rtInfo",attrs:{"data-key":"cpu.currGhz"}},[t._v(t._s(t.cpu.currGhz))])]),t._v(" "),a("tr",[a("td",[t._v("使用率")]),t._v(" "),t.cpu.usedPerc?a("td",{staticClass:"rtInfo",attrs:{"data-key":"cpu.usedPerc"}},[1*t.cpu.usedPerc.slice(0,-1)>80?a("font",{attrs:{color:"red"}},[t._v(t._s(t.cpu.usedPerc))]):a("font",{attrs:{color:"green"}},[t._v(t._s(t.cpu.usedPerc))])],1):t._e()])])])])],1),t._v(" "),a("el-col",{attrs:{span:8}},[a("jee-card",{attrs:{title:"内存",icon:"icon-fire"}},[a("table",{staticClass:"table table-striped table-hover table-center"},[a("tbody",[a("tr",[a("th",{attrs:{width:"33%"}},[t._v("属性")]),t._v(" "),a("th",{attrs:{width:"33%"}},[t._v("内存")]),t._v(" "),a("th",{attrs:{width:"33%"}},[t._v("JVM")])]),t._v(" "),a("tr",[a("td",[t._v("总内存")]),t._v(" "),a("td",{staticClass:"rtInfo",attrs:{"data-key":"mem.total"}},[t._v(t._s(t.mem.total))]),t._v(" "),a("td",{staticClass:"rtInfo",attrs:{"data-key":"jvm.total"}},[t._v(t._s(t.jvm.total))])]),t._v(" "),a("tr",[a("td",[t._v("剩余内存")]),t._v(" "),a("td",{staticClass:"rtInfo",attrs:{"data-key":"mem.free"}},[t._v(t._s(t.mem.free))]),t._v(" "),a("td",{staticClass:"rtInfo",attrs:{"data-key":"jvm.free"}},[t._v(t._s(t.jvm.free))])]),t._v(" "),a("tr",[a("td",[t._v("已用内存")]),t._v(" "),a("td",{staticClass:"rtInfo",attrs:{"data-key":"mem.used"}},[t._v(t._s(t.mem.used))]),t._v(" "),a("td",{staticClass:"rtInfo",attrs:{"data-key":"jvm.used"}},[t._v(t._s(t.jvm.used))])]),t._v(" "),a("tr",[a("td",[t._v("使用率")]),t._v(" "),t.mem.usedPerc?a("td",{staticClass:"rtInfo",attrs:{"data-key":"mem.usedPerc"}},[1*t.mem.usedPerc.slice(0,-1)>80?a("font",{attrs:{color:"red"}},[t._v(t._s(t.mem.usedPerc))]):a("font",{attrs:{color:"green"}},[t._v(t._s(t.mem.usedPerc))])],1):t._e(),t._v(" "),t.jvm.usedPerc?a("td",{staticClass:"rtInfo",attrs:{"data-key":"jvm.usedPerc"}},[1*t.jvm.usedPerc.slice(0,-1)>80?a("font",{attrs:{color:"red"}},[t._v(t._s(t.jvm.usedPerc))]):a("font",{attrs:{color:"green"}},[t._v(t._s(t.jvm.usedPerc))])],1):t._e()])])])])],1),t._v(" "),a("el-col",{attrs:{span:8}},[a("jee-card",{attrs:{title:"堆/非堆",icon:"icon-puzzle"}},[a("table",{staticClass:"table table-striped table-hover table-center"},[a("tbody",[a("tr",[a("th",{attrs:{width:"33%"}},[t._v("属性")]),t._v(" "),a("th",{attrs:{width:"33%"}},[t._v("堆")]),t._v(" "),a("th",{attrs:{width:"33%"}},[t._v("非堆")])]),t._v(" "),a("tr",[a("td",[t._v("初始大小")]),t._v(" "),a("td",{staticClass:"rtInfo",attrs:{"data-key":"jvm.heapInit"}},[t._v(t._s(t.jvm.heapInit))]),t._v(" "),a("td",{staticClass:"rtInfo",attrs:{"data-key":"jvm.nonHeapInit"}},[t._v(t._s(t.jvm.nonHeapInit))])]),t._v(" "),a("tr",[a("td",[t._v("最大内存")]),t._v(" "),a("td",{staticClass:"rtInfo",attrs:{"data-key":"jvm.heapMax"}},[t._v(t._s(t.jvm.heapMax))]),t._v(" "),a("td",{staticClass:"rtInfo",attrs:{"data-key":"jvm.nonHeapMax"}},[t._v(t._s(t.jvm.nonHeapMax))])]),t._v(" "),a("tr",[a("td",[t._v("已用内存")]),t._v(" "),a("td",{staticClass:"rtInfo",attrs:{"data-key":"jvm.heapUsed"}},[t._v(t._s(t.jvm.heapUsed))]),t._v(" "),a("td",{staticClass:"rtInfo",attrs:{"data-key":"jvm.nonHeapUsed"}},[t._v(t._s(t.jvm.nonHeapUsed))])]),t._v(" "),a("tr",[a("td",[t._v("可用内存")]),t._v(" "),a("td",{staticClass:"rtInfo",attrs:{"data-key":"jvm.heapAvailable"}},[t._v(t._s(t.jvm.heapAvailable))]),t._v(" "),a("td",{staticClass:"rtInfo",attrs:{"data-key":"jvm.nonHeapAvailable"}},[t._v(t._s(t.jvm.nonHeapAvailable))])])])])])],1)],1),t._v(" "),a("jee-card",{attrs:{title:"服务器信息",icon:"icon-screen-tablet"}},[a("table",{staticClass:"table table-striped table-hover"},[a("tbody",[a("tr",[a("td",{attrs:{width:"15%"}},[t._v("服务器名称")]),t._v(" "),a("td",{attrs:{width:"30%"}},[t._v(t._s(t.systemInfo.hostName))]),t._v(" "),a("td",{attrs:{width:"15%"}},[t._v("操作系统")]),t._v(" "),a("td",[t._v(t._s(t.systemInfo.osName))])]),t._v(" "),a("tr",[a("td",[t._v("服务器IP")]),t._v(" "),a("td",[t._v(t._s(t.systemInfo.hostAddress))]),t._v(" "),a("td",[t._v("系统架构")]),t._v(" "),a("td",[t._v(t._s(t.systemInfo.osArch))])])])])]),t._v(" "),a("jee-card",{attrs:{title:"Java虚拟机信息",icon:"icon-cpu"}},[a("table",{staticClass:"table table-striped table-hover"},[a("tbody",[a("tr",[a("td",{attrs:{width:"15%"}},[t._v("Java名称")]),t._v(" "),a("td",{attrs:{width:"30%"}},[t._v(t._s(t.systemInfo.javaName))]),t._v(" "),a("td",{attrs:{width:"15%"}},[t._v("Java版本")]),t._v(" "),a("td",[t._v(t._s(t.systemInfo.javaVersion+", 供应商 "+t.systemInfo.javaVendor))])]),t._v(" "),a("tr",[a("td",[t._v("启动时间")]),t._v(" "),a("td",[t._v(t._s(t.systemInfo.startTime))]),t._v(" "),a("td",[t._v("运行时长")]),t._v(" "),a("td",[t._v(t._s(t.systemInfo.uptime))])]),t._v(" "),a("tr",[a("td",[t._v("安装路径")]),t._v(" "),a("td",{attrs:{colspan:"3"}},[t._v(t._s(t.systemInfo.javaHome))])]),t._v(" "),a("tr",[a("td",[t._v("启动参数")]),t._v(" "),a("td",{attrs:{colspan:"3"}},t._l(t.javaArgs,function(e,s){return a("p",{key:s,staticClass:"args"},[t._v(t._s(e))])}))])])])]),t._v(" "),a("jee-card",{attrs:{title:"平台参数",icon:"icon-eyeglass"}},[a("table",{staticClass:"table table-striped table-hover"},[a("tbody",[a("tr",[a("td",{attrs:{width:"15%"}},[t._v("当前工作路径")]),t._v(" "),a("td",[t._v(t._s(t.systemInfo.userDir))])]),t._v(" "),a("tr",[a("td",[t._v("日志存放路径")]),t._v(" "),a("td",[t._v(t._s(t.systemInfo.logPath))])]),t._v(" "),a("tr",[a("td",[t._v("上传文件路径")]),t._v(" "),a("td",[t._v(t._s(t.systemInfo.userfilesDir))])])])])]),t._v(" "),a("jee-card",{attrs:{title:"磁盘状态",icon:"icon-drawer"}},[a("table",{staticClass:"table table-striped table-hover table-center"},[a("tbody",[a("tr",[a("th",[t._v("#")]),t._v(" "),a("th",[t._v("盘符路径")]),t._v(" "),a("th",[t._v("文件系统")]),t._v(" "),a("th",[t._v("盘符类型")]),t._v(" "),a("th",[t._v("总大小")]),t._v(" "),a("th",[t._v("可用大小")]),t._v(" "),a("th",[t._v("已用大小")]),t._v(" "),a("th",[t._v("已用百分比")])]),t._v(" "),t._l(t.diskInfos,function(e,s){return a("tr",{key:s,staticClass:"center"},[a("td",[t._v(t._s(s+1))]),t._v(" "),a("td",[t._v(t._s(e.dirName))]),t._v(" "),a("td",[t._v(t._s(e.typeName))]),t._v(" "),a("td",[t._v(t._s(e.description))]),t._v(" "),a("td",[t._v(t._s(e.total))]),t._v(" "),a("td",[t._v(t._s(e.free))]),t._v(" "),a("td",[t._v(t._s(e.used))]),t._v(" "),a("td",[t._v(t._s(e.usedPerc))])])})],2)])])],1)},staticRenderFns:[]};var f=a("VU/8")(l,u,!1,function(t){a("IWTM")},"data-v-0b0c8436",null);e.default=f.exports},mypn:function(t,e,a){(function(t,e){!function(t,a){"use strict";if(!t.setImmediate){var s,r,n,o,i,c=1,v={},d=!1,_=t.document,l=Object.getPrototypeOf&&Object.getPrototypeOf(t);l=l&&l.setTimeout?l:t,"[object process]"==={}.toString.call(t.process)?s=function(t){e.nextTick(function(){f(t)})}:!function(){if(t.postMessage&&!t.importScripts){var e=!0,a=t.onmessage;return t.onmessage=function(){e=!1},t.postMessage("","*"),t.onmessage=a,e}}()?t.MessageChannel?((n=new MessageChannel).port1.onmessage=function(t){f(t.data)},s=function(t){n.port2.postMessage(t)}):_&&"onreadystatechange"in _.createElement("script")?(r=_.documentElement,s=function(t){var e=_.createElement("script");e.onreadystatechange=function(){f(t),e.onreadystatechange=null,r.removeChild(e),e=null},r.appendChild(e)}):s=function(t){setTimeout(f,0,t)}:(o="setImmediate$"+Math.random()+"$",i=function(e){e.source===t&&"string"==typeof e.data&&0===e.data.indexOf(o)&&f(+e.data.slice(o.length))},t.addEventListener?t.addEventListener("message",i,!1):t.attachEvent("onmessage",i),s=function(e){t.postMessage(o+e,"*")}),l.setImmediate=function(t){"function"!=typeof t&&(t=new Function(""+t));for(var e=new Array(arguments.length-1),a=0;a<e.length;a++)e[a]=arguments[a+1];var r={callback:t,args:e};return v[c]=r,s(c),c++},l.clearImmediate=u}function u(t){delete v[t]}function f(t){if(d)setTimeout(f,0,t);else{var e=v[t];if(e){d=!0;try{!function(t){var e=t.callback,s=t.args;switch(s.length){case 0:e();break;case 1:e(s[0]);break;case 2:e(s[0],s[1]);break;case 3:e(s[0],s[1],s[2]);break;default:e.apply(a,s)}}(e)}finally{u(t),d=!1}}}}}("undefined"==typeof self?void 0===t?this:t:self)}).call(e,a("DuR2"),a("W2nU"))}});