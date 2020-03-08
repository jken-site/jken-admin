layui.define(['form'], function (exports) {
  var $ = layui.$, form = layui.form;

  //自定义验证
  form.verify({
    phone: [
      /(^$)|(^1\d{10}$)/, '请输入正确的手机号'
    ], email: [
      /(^$)|(^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$)/
      , '邮箱格式不正确'
    ], url: [
      /(^$)|((^#)|(^http(s*):\/\/[^\s]+\.[^\s]+))/, '链接格式不正确'
    ], number: function (value) {
      if ((!value || value !== "") && isNaN(value)) return '只能填写数字'
    }, date: [
      /(^$)|(^(\d{4})[-\/](\d{1}|0\d{1}|1[0-2])([-\/](\d{1}|0\d{1}|[1-2][0-9]|3[0-1]))*$)/, '日期格式不正确'
    ], identity: [
      /(^$)|((^\d{15}$)|(^\d{17}(x|X|\d)$))/, '请输入正确的身份证号'
    ]
  });

  function isArrayFn(o) {
    return Object.prototype.toString.call(o) === '[object Array]';
  }

  //取值
  form.getValue = function (filter, itemForm) {
    itemForm = itemForm || $(ELEM + '[lay-filter="' + filter + '"]').eq(0);

    var nameIndex = {} //数组 name 索引
      , field = {}
      , fieldElem = itemForm.find('input,select,textarea') //获取所有表单域

    layui.each(fieldElem, function (_, item) {
      if (item.disabled) {
        return;
      }
      item.name = (item.name || '').replace(/^\s*|\s*&/, '');

      if (!item.name) return;

      if (/^checkbox|radio$/.test(item.type) && !item.checked) return;

      //用于支持数组 name
      if (field[item.name]) {
        if (Object.prototype.toString.call(field[item.name]) === '[object Array]') {
          field[item.name].push(item.value);
        } else {
          field[item.name] = [field[item.name]];
          field[item.name].push(item.value);
        }
      } else {
        field[item.name] = item.value;
      }
    });

    return field;
  };

  //对外暴露的接口
  exports('edit', {});
});