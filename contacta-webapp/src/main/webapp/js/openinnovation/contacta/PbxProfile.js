define(
[
  'dojo',
  'dojo/_base/declare',
  'openinnovation/organic/gridpad/Controller'
],
function(dojo, declare, Controller)
{
  return declare("openinnovation.contacta.PbxProfile", [ Controller ],
  {
    classCode:"pbxprofile",

    constructor: function(args)
    {
      this.structure = [{ cells: [[
       { name:this._i18n.titleCode, field:'code', width:'10em' },
       { name:this._i18n.titleLabel, field:'label', width:'16em' },
       { name:this._i18n.titleCommand, field:'command', width:'20em' },
       { name:this._i18n.titleId, field:'id', width:'4em' }
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

