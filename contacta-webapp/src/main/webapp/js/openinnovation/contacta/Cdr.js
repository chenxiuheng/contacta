define(
[
  'dojo',
  'dojo/_base/declare',
  'openinnovation/organic/gridpad/Controller'
],
function(dojo, declare, Controller)
{
  return declare("openinnovation.contacta.Cdr", [ Controller ],
  {
    classCode:"cdr",

    constructor: function(args)
    {
      this.structure = [{ cells: [[
       { field:'calldate', width:'10em' },
       { field:'src', width:'4em' },
       { field:'dst', width:'4em' },
       { field:'duration', width:'4em' },
       { field:'uniqueid', width:'6em' },
       { field:'userfield', width:'5em' }
      ]] } ];
    },


    showDetailGetUrl:function(json)
    {
      //var uniqueid = this._store.getValue(item, 'uniqueid');
      return this.classCode+'-detail.action?uniqueid='+json.uniqueid;
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
