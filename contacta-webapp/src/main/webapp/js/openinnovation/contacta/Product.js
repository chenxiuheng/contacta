define(
[
  'dojo',
  'dojo/_base/declare',
  'openinnovation/organic/gridpad/Controller'
],
function(dojo, declare, Controller)
{
  return declare("openinnovation.contacta.Product", [ Controller ],
  {
    classCode:"product",

    constructor: function(args)
    {
      this.structure = [{ cells: [[
       { name:this._i18n.titleCode, field:'code', width:'5em' },
       { name:this._i18n.titleLabel, field:'vendor', width:'5em' },
       { name:this._i18n.titleCommand, field:'model', width:'5em' },
       { name:this._i18n.titleLabel, field:'version', width:'5em' },
       { name:this._i18n.titleCommand, field:'userAgent', width:'20em' }
      ]] } ];
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

