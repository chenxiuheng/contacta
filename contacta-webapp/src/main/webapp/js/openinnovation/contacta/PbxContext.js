define(
[
  'dojo',
  'dojo/_base/declare',
  'openinnovation/organic/gridpad/Controller'
],
function(dojo, declare, Controller)
{
  return declare("openinnovation.contacta.PbxContext", [ Controller ],
  {
    classCode:"pbxcontext",

    constructor: function(args)
    {
      this.structure = [{ cells: [[
       { name:this._i18n.titleCode, field:'code', width:'12em' },
       { name:this._i18n.titleLabel, field:'label', width:'12em' },
       //{ name:this._i18n.titleName, field:'vmEnabled', width:'2em', formatter:dojo.hitch(this, this.formatYn), cellStyles:'text-align:center;' },
       //{ name:this._i18n.titleName, field:'callgroup', width:'4em' },
       { name:this._i18n.titleId, field:'id', width:'4em' }
      ]] } ];
    },


    showDetailGetUrl:function(json)
    {
      //var code = this._store.getValue(item, 'login');
      return this.classCode+'-detail.action?code='+json.login;
    },


    convertForm2Json:function(formValue)
    {
      var json = this.inherited(arguments);
      return json;
    },


    convertJson2Form:function(json)
    {
      var form = this.inherited(arguments);
      return form;
    },


    endOfLib: null
  });
});

