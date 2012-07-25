// -*- js-var:contacta,ui,console,dojo,dijit,dojox; -*-
// $Id$
dojo.provide("openinnovation.contacta.Sip");

dojo.require("openinnovation.organic.Organic");

// ==============================================================================================
//  account
// ==============================================================================================
require(['dojo/_base/declare'], function(declare) { declare("openinnovation.contacta.Sip", openinnovation.organic.gridpad.Controller,
{
  classCode:"sip",

  constructor: function(args)
  {
    //console.log('openinnovation.contacta.Sip: constructor', arguments);
    this.structure = [{ cells: [[
     { name:this._i18n.titleId, field:'fullName', width:'auto' },
     { name:this._i18n.titleOn, field:'state', width:'2em', formatter:dojo.hitch(this, this.formatState) },
     { name:this._i18n.titleLogin, field:'login', width:'4em', cellStyles:'font-family:monospace;' },
     //{ name:this._i18n.titleId, field:'password', width:'4em', styles:'font-family:monospace;' },
     { name:this._i18n.titleProfile, field:'profileName', width:'13em' },
     //{ name:this._i18n.titleId, field:'profileOptions', width:'7em' },
     //{ name:this._i18n.titleContext, field:'context', width:'auto' },
     { name:this._i18n.titleVm, field:'vmEnabled', width:'2em', formatter:dojo.hitch(this, this.formatYn), cellStyles:'text-align:center;' },
     //{ name:this._i18n.titleId, field:'vmPin', width:'5em' },
     { name:this._i18n.titleCallGroup, field:'callgroup', width:'4em' },
     { name:this._i18n.titlePickupGroup, field:'pickupgroup', width:'4em' },
     //{ name:this._i18n.titleId, field:'cocLogin', width:'4em' }
     //{ name:this._i18n.titleId, field:'cocPin', width:'4em' }
     { name:'Reboot', field:'login', width:'4em', formatter:dojo.hitch(this,this.formatAction) }
    ]] } ];
  },

  formatAction: function(/*Object*/field, /*int*/rowPosition, /*Column*/column)
  {
    var text = '<div style="float:left;" class="ico46" title="SIP Notify check-cfg" onclick="contacta.contactaService.notifyCheckCfg(\''+ field + '\');"></div>';
    return text;
  },


  /*
   *
   */
  formatState:function(attribute, index) /*String*/
  {
    if (attribute !== undefined)
    {
      /*var ip = ui.phone.grid.getItem(row).ipAddress;*/
      var ip = attribute;
      if (ip == 'off')
      {
        attribute = '<div class="ico02" style="padding-left:20px;"></div>';
      }
      else
      {
        attribute = '<div class="ico01" style="padding-left:20px;"></div>';
      }
    }
    return attribute;
  },


  /*
   *
   */
  postscript: function()
  {
    this.inherited(arguments);
  },


  /*
   *
   */
  convertForm2Json:function(formValue)
  {
    var account = this.inherited(arguments);

    //account.id = parseInt(account.id, 10);
    account.vmEnabled = this._fixCheckboxToBoolean(account.vmEnabled); // TODO bella roba x i checkbox, sistemare
    account.vmSendEmail = this._fixCheckboxToBoolean(account.vmSendEmail);
    account.cocEnabled = this._fixCheckboxToBoolean(account.cocEnabled);

    if(!account.cocEnabled)
    {
      account.cocLogin = "";
      account.cocPin = "";
    }
    //console.log('#############################', account);
    return account;
  },


  /*
   *
   */
  convertJson2Form:function(json)
  {
    var p = this.inherited(arguments);
    //p = organic.util.normalizeStoreItem(this.currentJson);
    if (p.id == 0)
    {
      this._ui.loginInput.attr('readOnly', false);
    }
    else
    {
      //this._ui.loginInput.attr('readOnly', true);
    }
    p.vmEnabled = ''+p.vmEnabled; // TODO bella roba x i checkbox, sistemare
    p.vmSendEmail = ''+p.vmSendEmail; // TODO bella roba x i checkbox, sistemare

    p.cocEnabled = 'false';
    if(p.cocLogin && p.cocLogin.length > 0)
    {
      p.cocEnabled = 'true';
    }

    // TODO watchCheckbox(ui.cocInput, 'cocPinDescription', ui.cocPinInput);
    // TODO watchCheckbox(ui.cocInput, 'cocLoginDescription', ui.cocLoginInput);
    return p;
  },











  /*
   *
   *
  submit:function()
  {
    contacta.contactaService.accountCreateUpdate(account).addCallbacks((account.id==0) ? this.submitCreateCallback : this.submitUpdateCallback,
      function(result)
      {
        ui.sipEditDialog.hide();

        var errorMessage = result.errorObject.error.message;

        if(errorMessage == "Duplication of a unique field: login")
        {
          result.errorObject.error.message = "Esiste un altro account con la stessa SIP login,<br/> le modifiche non saranno eseguite.";
          this.onSubmitError(result);
        }
        else if(errorMessage == "Duplication of a unique field: cocLogin")
        {
          result.errorObject.error.message = "Esiste un altro account con la stessa COC login,<br/> le modifiche non saranno eseguite.";
          this.onSubmitError(result);
        }
        else
        {
          this.onSubmitError(result);
        }
      });
  },*/


  /*
   *
   */
  paging:function(searchValue)
  {
    if(searchValue)
    {
      ui.sip.filterInput.attr('value',searchValue);
    }
    else
    {
      searchValue = ui.sip.filterInput.getValue();
    }

    if(searchValue!='-')
    {
      ui.sip.quick.attr('value','');
      ui.sipGrid.setQuery({ lastName: searchValue+'*' }, { ignoreCase:true });
    }
  },


  /*
   *
   */
  quickSearch:function(text)
  {
    ui.sip.filterInput.attr('value','-');

    var searchField = ui.sip.quickSelect.getValue();
    text = '*'+text+'*';
    var argsObj = null;
    eval("argsObj = { " + searchField + ": '" + text + "' };");
    ui.sipGrid.setQuery(argsObj, { ignoreCase:true });
  },


  /*
   *
   */
  changeProfile:function(arg)
  {
    //console.log('changeProfile', arg);
    if (arg == 'Segretaria')
    {
      ui.profileOptions.attr('required', true);
      ui.profileOptions.attr('regExp', '\\d{4},\\d{4}');
      ui.profileOptions.attr('promptMessage', 'Interni del direttore controllati: privato,pubblico.  <br/>Il numero privato deve esistere');
      ui.profileOptions.attr('invalidMessage', 'Inserire gli interni XXXX,YYYY<br/>rispettivamente privato e pubblico.<br/>Il numero privato deve esistere');
    }
    else if (arg == 'Dial a cascata')
    {
      ui.profileOptions.attr('required', false);
      ui.profileOptions.attr('regExp', '\\d{4}(,\\d{4})*');
      ui.profileOptions.attr('promptMessage', 'Lista di interni');
      ui.profileOptions.attr('invalidMessage', 'Lista di interni a quattro cifre separati da virgola');
    }
    else
    {
      ui.profileOptions.attr('required', false);
      ui.profileOptions.attr('regExp', '.*');
      ui.profileOptions.attr('promptMessage', 'Questo campo viene ignorato');
      ui.profileOptions.attr('invalidMessage', null);
    }
    //ui.profileOptions.reset();
  },


  /*
   *
   */
  starDetail:function()
  {
    var login = this.currentJson.login;
    ui.starDetailDialog.show();
    ui.starDetailDialog.setHref('sip-star.action?code='+login);
  },


  /*
   *  ====== coverage =====
   */
  coverage:
  {
    /*
     *
     */
    removeAll:function()
    {
      var login = this.currentJson.login;
      contacta.contactaService.removeCoverage(login).addCallbacks(
        function(result)
        {
          //console.log('removeCoverage OK');
          ui.sipDetail.setHref('sip-detail.action?code='+login);
          if (result)
          {
            ui.sipGrid.store.setValue(this.currentJson, 'profileName', 'Dial');
          }
          else
          {
            organic.util.dojoAlertShow("Non e' stato possibile aggiungere l'assistente alla linea SIP.");
          }
        },
        dojo.hitch(this, this.onSubmitError));
    },

    /*
     *
     */
    show:function()
    {
      ui.coverageForm.reset();
      ui.coverageDialog.show();
    },

    /*
     *
     */
    submit:function()
    {
      var login = this.currentJson.login;
      var cvg = ui.coverageForm.getValues();
      contacta.contactaService.addCoverage(login, cvg.exten, cvg.type, cvg.options, cvg.ringTimeout).addCallbacks(
        function(result)
        {
          console.log('addCoverage OK', result);
          ui.sipDetail.setHref('sip-detail.action?code='+login);
          if (result)
          {
            ui.sipGrid.store.setValue(this.currentItem, 'profileName', 'Coverage');
          }
          else
          {
            organic.util.dojoAlertShow("Non e' stato possibile aggiungere l'assistente alla linea SIP.");
          }
        },
        dojo.hitch(this, this.onSubmitError));

      ui.coverageDialog.hide();
    },

    /*
     *
     */
    cancel:function()
    {
      ui.coverageDialog.hide();
    }
  },

  endOfLib: null
}); });


/*
 *
 */
function watchCheckbox(checkbox, descriptionId, vtb)
{
  var description = dojo.byId(descriptionId);

  if(!checkbox.getValue())
  {
    vtb._setDisabledAttr(true);
    dojo.style(description, "color", "grey");
  }
  else
  {
    vtb._setDisabledAttr(false);
    dojo.style(description, "color", "black");
  }

}


