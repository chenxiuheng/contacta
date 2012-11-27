define(
[
  'dojo',
  'dojo/_base/declare',
  'openinnovation/organic/gridpad/Controller'
],
function(dojo, declare, Controller)
{
  return declare("openinnovation.contacta.Coc", [ Controller ],
  {
    classCode:"coc",

    constructor: function(args)
    {
      this.structure = [{ cells: [[
       { field:'login', width:'8em' },
       { field:'pin', width:'8em' }
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

