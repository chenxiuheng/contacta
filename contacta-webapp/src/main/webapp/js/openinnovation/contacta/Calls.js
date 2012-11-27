define(
[
  'dojo',
  'dojo/_base/declare',
  'openinnovation/organic/gridpad/Controller'
],
function(dojo, declare, Controller)
{
  return declare("openinnovation.contacta.Calls", [ Controller ],
  {
    classCode:'calls',

    constructor: function(args)
    {
      this.structure = [{ cells: [[
       { name:'Chiamante', field:'clid', width:'20em' },
       { name:'Skype ID', field:'src', width:'12em', cellStyles:'text-align:center;' },
       { name:'disposition', field:'disposition', width:'10em', cellStyles:'text-align:center;' },
       { name:'Data', field:'calldate', width:'12em', cellStyles:'text-align:center;' },
       { name:'Chiamato', field:'dst', width:'16em' },
       { name:'Chiama', field:'exten', width:'5em', cellStyles:'text-align:center;', formatter:dojo.hitch(this, this._dialFormatter) }
      ]] } ];
    },


    _dialFormatter:function(value,index,widget)//(model)
    {
      /*var item = widget._props.grid.getItem(index)
      if(item != null)
      {
        var field = widget._props.field;
        var text = eval("item."+field+"Code[0]") + " - " + eval("item."+field+"[0]");
        return text;
      }*/
      if (value && value !== '')
      {
        value = '<a onclick="contacta.dial(\''+value+'\');return false;">Call</a>';
      }
      else
      {
        value = '<a onclick="alert(\'todo\')");return false;">Add</a>';
      }
      return value;
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

