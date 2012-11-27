define(
[
  'dojo',
  'dojo/_base/declare',
  'openinnovation/organic/gridpad/Controller'
],
function(dojo, declare, Controller)
{
  return declare("openinnovation.contacta.Phone", [ Controller ],
  {
    classCode:"phone",

    constructor: function(args)
    {
      this.structure = [{ cells: [[
       { name:this._i18n.titleCID, field:'accounts', width:'15em', formatter:dojo.hitch(this, this.formatPhoneAccounts) },
       { name:'Cfg', field:'hasConfig', width:'2em', formatter:dojo.hitch(this, this.formatYn), cellStyles:'text-align:center;' },
       { name:this._i18n.titleIpAddress, field:'ipAddress', width:'6em', formatter:dojo.hitch(this, this.formatPhoneIps) },
       { name:'lastBoot', field:'lastBoot', width:'5em', formatter:dojo.hitch(this, this.formatRegseconds) },
       { name:this._i18n.titleMac, field:'macAddress', width:'11em', cellStyles:'font-family:monospace;' },
       //{ name:this._i18n.titleVendor, field:'vendor', width:'6em' },
       { name:this._i18n.titleProduct, field:'product', width:'6em' },
       //{ name:this._i18n.titleId, field:'info', width:'auto' },
       { name:this._i18n.titleLocation, field:'location', width:'auto' }
      ]] } ];
    },


    formatRegseconds:function(attribute, index) /*String*/
    {
      if (!attribute || attribute === '' || attribute === '0')
      {
        attribute = '-';
      }
      else
      {
        //attribute = dojo.date.stamp.toISOString(new Date(regseconds*1000));
        //console.log('---------', regseconds, attribute);
      }
      return attribute;
    },


    formatPhoneIps:function(attribute, index) /*String*/
    {
      if (attribute !== undefined)
      {
        /*var ip = ui.phone.grid.getItem(row).ipAddress;*/
        var ip = attribute;
        if (ip === null || ip === '' || ip === '0.0.0.0')
        {
          attribute = '<div class="ico02" style="padding-left:20px;">-</div>';
        }
        else
        {
          attribute = '<div class="ico01" style="padding-left:20px;"><a target="_blank" href="http://'+ip+'">'+ip+'</a></div>';
        }
      }
      return attribute;
    },


    formatPhoneAccounts:function(attribute, index) /*String*/
    {
      var text = "N/A";
      if (attribute !== undefined)
      {
        var accounts = ui.phone.grid.getItem(index).accounts;
        text = "";
        for(var i in accounts)
        {
          var obj = accounts[i];

          //text += '<div style="white-space:nowrap;">'+obj.login+' ('+obj.fullName+')</div>';
          text += '<div style="white-space:nowrap;">'+obj+'</div>';
        }
        //text = text.substring(0, text.length-2);
      }
      return text;
    },


    convertForm2Json:function(formValue)
    {
      var phone = this.inherited(arguments);
      phone.hasConfig = this._fixCheckboxToBoolean(phone.hasConfig); // TODO bella roba x i checkbox, sistemare
      //console.log('#############################', formValue, phone);
      return phone;
    },


    convertJson2Form:function(formValue)
    {
      var phone = this.inherited(arguments);
      this._ui.productSelect.set('store', contacta.product._store);
      return phone;
    },


















    wizardPhonePersonShow:function()
    {
      ui.wizardPhonePersonForm.reset();

      //watchCheckbox(ui.vmInputWizard, 'vmDescWizard', ui.vmPinInputWizard)
      watchCheckbox(ui.cocInputWizard, 'cocPinDescriptionWizard', ui.cocPinInputWizard);
      watchCheckbox(ui.cocInputWizard, 'cocLoginDescriptionWizard', ui.cocLoginInputWizard);

      ui.wizardPhonePersonDialog.show();
    },


    wizardPhonePersonSubmit:function()
    {
      if(!ui.wizardPhonePersonForm.validate())
      {
        return;
      }

      var phone = ui.wizardPhonePersonForm.getValues();
      phone.account.vmEnabled = organic.util._fixCheckboxToBoolean(phone.account.vmEnabled); // TODO bella roba x i checkbox, sistemare
      contacta.contactaService.wizardPhonePerson(phone).addCallbacks(function(result)
      {
        ui.wizardPhonePersonDialog.hide();

        this.load();
        contacta.sip.load();
      },
      function(result)
      {
        ui.wizardPhonePersonDialog.hide();
        this.onSubmitError(result);
      });
    },


    wizardAssignPhone:function()
    {
      var accounts = [];
      ui.sipGrid.store.fetch( { onItem:function(item) { if (item.phone[0] === null) { accounts.push(item); } } });
      if(accounts.length === 0)
      {
        organic.util.dojoAlertShow('${m.t("short.phone.assign.error")?js_string}');
        return;
      }

      var phones = [];
      ui.phone.grid.store.fetch( { onItem:function(item) { if (item.accounts === 'N/A') { phones.push(item); } } });
      if(phones.length === 0)
      {
        organic.util.dojoAlertShow('${m.t("short.phone.assign.error2")?js_string}');
        return;
      }

      ui.wizardAssignPhoneForm.reset();
      //ui.wizardAccountSelect.set('value', null);
      //ui.wizardPhoneSelect.set('value', null);
      ui.wizardAccountSelect.set('store', ui.sipGrid.store);
      ui.wizardAccountSelect.set('query', { phone:null });
      ui.wizardPhoneSelect.set('store', ui.phone.grid.store);
      ui.wizardPhoneSelect.set('query', { accounts:'N/A' });
      ui.wizardAssignPhoneDialog.show();
    },


    wizardAssignPhoneSubmit:function()
    {
      if(!ui.wizardAssignPhoneForm.validate())
      {
        return;
      }

      console.log("wizardPhoneInput, wizardAccountSelect", ui.wizardPhoneInput.get('value'), ui.wizardAccountSelect.get('value'));

      contacta.contactaService.phoneAddAccount(ui.wizardPhoneInput.get('value'), ui.wizardAccountSelect.get('value')).addCallbacks(function(result)
      {
        ui.wizardAssignPhoneDialog.hide();
        if (result)
        {
          this.load();
          contacta.sip.load();
        }
        else
        {
          organic.util.dojoAlertShow('${m.t("short.phone.notfound")?js_string}');
        }
      },
      function(result)
      {
        ui.wizardAssignPhoneDialog.hide();
        this.onSubmitError(result);
      });
    },


    paging:function(searchValue)
    {
      if (searchValue)
      {
        ui.phonePagination.set('value',searchValue);
      }
      else
      {
        searchValue = ui.phonePagination.getValue();
      }

      switch(searchValue)
      {
      case '*':
        ui.phone.grid.setQuery(null);
        break;

      case '-':
        break;

      case 'N/A':
        ui.phone.grid.setQuery({ accounts:'N/A' });
        break;

      default:
        ui.phone.quickFilter.set('value', '');
        ui.phone.grid.setQuery({ accounts: searchValue+'*' }, { ignoreCase:true });
      }
    },


    quickSearch:function(text)
    {
      ui.phonePagination.set('value','-');

      var searchField = ui.phone.quickFilterSelect.getValue();
      text = '*'+text+'*';
      var argsObj = null;
      eval("argsObj = { " + searchField + ": '" + text + "' };");
      ui.phone.grid.setQuery(argsObj, { ignoreCase:true });
    },


    addAccountCancel:function()
    {
      ui.phoneAddAccountDialog.hide();
    },


    addAccountSubmit:function()
    {
      //var grid = ui.phoneAddAccountGrid;
      //var rows = grid.selection.getSelected();
      //var ids = [];
      //for (var i = 0; i < rows.length; i++)
      //{
      //  ids[i] = rows[i].id[0];
      //}
      var phoneItem = this.currentJson;
      //console.log('phoneItem', phoneItem);
      var form = ui.phoneAddAccountForm.getValues();
      contacta.contactaService.phoneAddAccount(phoneItem.id, form.login).addCallbacks(function(result)
      {
        //ui.phoneAddAccountGrid.selection.clear();
        ui.phoneAddAccountDialog.hide();

        if (result)
        {
          ui.phone.detailPane.set('href', 'phone-detail.action?code='+result.phone);
          ui.sip.detailPane.set('href', '../sip/sip-detail.action?code='+result.login);

          this.load();
        }
        else
        {
          organic.util.dojoAlertShow('${m.t("short.sip.error")?js_string} '+form.login);
        }
      },
      function(result)
      {
        ui.phoneAddAccountDialog.hide();
        this.onSubmitError(result);
      });
    },


    addAccountShow:function(/*[opt] int*/phoneId)
    {
      //console.log('phoneId', phoneId);
      if (phoneId)
      {
        this.currentJson = { id:phoneId };
      }
      if (!this.currentJson)
      {
        organic.util.dojoAlertShow('${m.t("short.phone.select")?js_string}');
      }
      else
      {
        //ui.phoneAddAccountGrid.setStore(ui.sipGrid.store);
        //ui.phoneAddAccountGrid.selection.clear();
        ui.phoneAddAccountForm.reset();
        ui.phoneAddAccountDialog.show();
      }
    },


    setVal1:function(val)
    {
      ui.wizardPhoneInput.set('value', val);
    },


    setVal2:function(val)
    {
      ui.wizardAccountSelect.set('value', val);
    },


    endOfLib: null
  });
});


