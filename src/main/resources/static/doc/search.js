let api = [];
api.push({
    alias: 'api',
    order: '1',
    desc: '',
    link: '',
    list: []
})
api[0].list.push({
    order: '1',
    desc: '全局检索，使用 BeanSearch 框架',
});
api[0].list.push({
    order: '2',
    desc: '向记账表当中插入一条元素',
});
api[0].list.push({
    order: '3',
    desc: '获取记账表当中某一天的所有支出或者收入情况',
});
api[0].list.push({
    order: '4',
    desc: '获取记账表当中某一月的所有支出或者收入情况',
});
api[0].list.push({
    order: '5',
    desc: '获取某一天的支出或者收入的总金额',
});
api[0].list.push({
    order: '6',
    desc: '获取某一月的支出或者收入的总金额',
});
api[0].list.push({
    order: '7',
    desc: '统计某月份支出或者收入情况有多少条',
});
api[0].list.push({
    order: '8',
    desc: '获取某一年的支出或者收入的总金额',
});
api[0].list.push({
    order: '9',
    desc: '根据传入的id，删除accounttb表当中的一条数据',
});
api[0].list.push({
    order: '10',
    desc: '根据传入的id，修改accounttb中的一条数据',
});
api[0].list.push({
    order: '11',
    desc: '查询记账的表当中有几个年份信息',
});
api[0].list.push({
    order: '12',
    desc: '查询指定年份和月份的收入或者支出每一种类型的总钱数',
});
api[0].list.push({
    order: '13',
    desc: '某月单日最大支出/收入额',
});
api[0].list.push({
    order: '14',
    desc: '根据指定月份每一日收入或者支出的总钱数的集合',
});
api[0].list.push({
    order: '15',
    desc: '根据备注搜索收入/支出的情况列表',
});
api[0].list.push({
    order: '16',
    desc: '根据类型搜索收入/支出的情况列表',
});
api[0].list.push({
    order: '17',
    desc: '获取预算',
});
api[0].list.push({
    order: '18',
    desc: '插入预算',
});
api.push({
    alias: 'CachingController',
    order: '2',
    desc: '',
    link: '',
    list: []
})
api[1].list.push({
    order: '1',
    desc: '',
});
document.onkeydown = keyDownSearch;
function keyDownSearch(e) {
    const theEvent = e;
    const code = theEvent.keyCode || theEvent.which || theEvent.charCode;
    if (code === 13) {
        const search = document.getElementById('search');
        const searchValue = search.value;
        let searchArr = [];
        for (let i = 0; i < api.length; i++) {
            let apiData = api[i];
            const desc = apiData.desc;
            if (desc.toLocaleLowerCase().indexOf(searchValue) > -1) {
                searchArr.push({
                    order: apiData.order,
                    desc: apiData.desc,
                    link: apiData.link,
                    alias: apiData.alias,
                    list: apiData.list
                });
            } else {
                let methodList = apiData.list || [];
                let methodListTemp = [];
                for (let j = 0; j < methodList.length; j++) {
                    const methodData = methodList[j];
                    const methodDesc = methodData.desc;
                    if (methodDesc.toLocaleLowerCase().indexOf(searchValue) > -1) {
                        methodListTemp.push(methodData);
                        break;
                    }
                }
                if (methodListTemp.length > 0) {
                    const data = {
                        order: apiData.order,
                        desc: apiData.desc,
                        alias: apiData.alias,
                        link: apiData.link,
                        list: methodListTemp
                    };
                    searchArr.push(data);
                }
            }
        }
        let html;
        if (searchValue === '') {
            const liClass = "";
            const display = "display: none";
            html = buildAccordion(api,liClass,display);
            document.getElementById('accordion').innerHTML = html;
        } else {
            const liClass = "open";
            const display = "display: block";
            html = buildAccordion(searchArr,liClass,display);
            document.getElementById('accordion').innerHTML = html;
        }
        const Accordion = function (el, multiple) {
            this.el = el || {};
            this.multiple = multiple || false;
            const links = this.el.find('.dd');
            links.on('click', {el: this.el, multiple: this.multiple}, this.dropdown);
        };
        Accordion.prototype.dropdown = function (e) {
            const $el = e.data.el;
            let $this = $(this), $next = $this.next();
            $next.slideToggle();
            $this.parent().toggleClass('open');
            if (!e.data.multiple) {
                $el.find('.submenu').not($next).slideUp("20").parent().removeClass('open');
            }
        };
        new Accordion($('#accordion'), false);
    }
}

function buildAccordion(apiData, liClass, display) {
    let html = "";
    if (apiData.length > 0) {
         for (let j = 0; j < apiData.length; j++) {
            html += '<li class="'+liClass+'">';
            html += '<a class="dd" href="' + apiData[j].alias + '.html#header">' + apiData[j].order + '.&nbsp;' + apiData[j].desc + '</a>';
            html += '<ul class="sectlevel2" style="'+display+'">';
            let doc = apiData[j].list;
            for (let m = 0; m < doc.length; m++) {
                html += '<li><a href="' + apiData[j].alias + '.html#_' + apiData[j].order + '_' + doc[m].order + '_' + doc[m].desc + '">' + apiData[j].order + '.' + doc[m].order + '.&nbsp;' + doc[m].desc + '</a> </li>';
            }
            html += '</ul>';
            html += '</li>';
        }
    }
    return html;
}