<%@ page contentType="text/html;charset=UTF-8" %>

<script type="text/template" id="tpl">
  {@each list as it}
  <tr data-account-id="&{it.id}" data-account-status="&{it.status}">
    <td>&{it.vipGrade}</td>
    <td>&{it.name}</td>
    <td>&{it.phone}</td>
    <td>&{it.cardNumber}</td>
    <td>&{it.balance}</td>
    <td>&{it.integral}</td>
    <td>&{it.totalConsumption}</td>
    <td>&{it.usedCreditAmount}</td>
    {@if it.status == 0}
    <td>停用</td>
    <td>
      <a href="javascript:;" class="label-info J_change"><i class="fa fa-check"></i>&nbsp;启用</a>
    </td>
    {@else}
    <td>启用</td>
    <td>
      <a href="javascript:;" class="label-info J_change"><i class="fa fa-circle"></i>&nbsp;停用</a>
    </td>
    {@/if}
  </tr>
  {@/each}
</script>
<script type="text/javascript">
  KISSY.use('page/vip-management/vip-account-info-management', function (S) {
    PW.page.VipManagement.VipAccountInfoManagement({
      renderTo: '.J_pagination',
      juicerRender: '#tpl',
      dataRender: '#J_template',
      url: '/admin/vip/account/ajax/list',
      pageSize: 10,
      configUrl: function (url, page, me, prevPaginationData) {
      //  return url;
        return url + '/' + page;
      }
    });
  });
</script>

<script>
  KISSY.add('page/vip-management/vip-account-info-management', function (S, List) {
    PW.namespace('page.VipManagement.VipAccountInfoManagement');
    PW.page.VipManagement.VipAccountInfoManagement = function (param) {
      new List(param);
    }
  }, {
    requires: [
      'vip-account-info-management/list'
    ]
  });
  /* ---------------------------------------------------------------------------*/
  KISSY.add('vip-account-info-management/list', function (S) {
    var
            DOM = S.DOM, get = DOM.get, query = DOM.query, $ = S.all,
            on = S.Event.on, delegate = S.Event.delegate,
            TablePagi = PW.widget.TablePagi,
            Dialog = PW.widget.Dialog,
            VipManagementIO = PW.io.VipManagement,
            config = {},
            el = {
              //改变触发器
              changeTrigger: '.J_change',
              //数据渲染模板
              template: '#J_template'
            },
            DATA_ACCOUNT_ID = 'data-account-id',
            DATA_ACCOUNT_STATUS = 'data-account-status',
            newVal = {
              0: 1,
              1: 0
            },
            nowStatus = {
              0: '启用',
              1: '停用'
            },
            nowValues = {
              0: ['停用', 'fa-circle', '启用'],
              1: ['启用', 'fa-check', '停用']
            },
            TIP = [
              '确定要删除该方案吗？',
              '删除成功！',
              '删除失败！',
              '保存成功！',
              '保存失败！'
            ];

    function List(param) {
      this.opts = S.merge(config, param);
      this.pagination;
      this._init();
    }

    S.augment(List, {
      _init: function () {
        this._initPagi();
        this._addEventListner();
      },
      /**
       * 添加事件监听
       */
      _addEventListner: function () {
        var
                that = this;

        delegate(el.template, 'click', el.changeTrigger, function (e) {
          that._changeAccountStatus(e.target);
        });
      },
      /**
       * 初始化分页
       * @return {[type]} [description]
       */
      _initPagi: function () {
        var
                that = this;

        that.pagination = TablePagi.client({
          pagi: that.opts,
          formSet: {
            hasForm: false,
            formRender: ''
          }
        });
      },
      /**
       * 点击停用、启用时，改变会员账户状态
       * @param  {[type]} ev [description]
       * @return {[type]}    [description]
       */
      _changeAccountStatus: function (ev) {
        var
                that = this,
                tr = $(ev).parent('tr'),
                changedId = tr.attr(DATA_ACCOUNT_ID),
                statusEl = get(el.changeTrigger, tr),
                status = S.trim(DOM.text(statusEl)),
                statusVal = tr.attr(DATA_ACCOUNT_STATUS),
                info = {
                  id: changedId,
                  status: newVal[statusVal]
                };

        Dialog.confirm('确定' + status + '该方案吗？', function (e, me) {
          VipManagementIO.changeAccountStatus(info, function (rs, errMsg) {
            if (rs) {
              Dialog.alert(nowStatus[statusVal] + '成功！');
              that._update(ev, statusVal);
            } else {
              Dialog.alert(errMsg);
            }
          });
        });
      },
      /**
       * 更新会员账户状态
       * @param  {[type]} ev        [description]
       * @param  {[type]} statusVal [description]
       * @return {[type]}           [description]
       */
      _update: function (ev, statusVal) {
        var
                that = this,
                currentTd = $(ev).parent('td'),
                currentA = get(el.changeTrigger, currentTd),
                vals = nowValues[statusVal],
                prevTd = currentTd.prev(),
                newhtml = '<i class="fa ' + vals[1] + '"></i>&nbsp;' + vals[0];

        $(ev).parent('tr').attr(DATA_ACCOUNT_STATUS, newVal[statusVal]);
        $(currentA).html(newhtml);
        prevTd.text(vals[2]);
      }
    });
    return List;
  }, {
    requires: [
      'widget/tablePagi',
      'widget/dialog',
      'pio/vip-management/vip-account-info-management'
    ]
  });
</script>