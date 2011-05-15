// -*- js-var:contacta,ui,console,dojo,dijit,dojox; -*-
// $Id: pbx.js 655 2010-07-03 16:30:08Z michele.bianchi $


dojo.require("openinnovation.organic.Organic");

// ==============================================================================================
//  Phonebook
// ==============================================================================================
dojo.provide("openinnovation.contacta.Phonebook");
dojo.declare("openinnovation.contacta.Phonebook", openinnovation.organic.gridpad.Controller,
{
  classCode:'phonebook',
  quickField:'displayName',

  constructor: function(args)
  {
    this.structure = [{ cells: [[
     { name:'Nome completo', field:'displayName', width:'14em' },
     { name:'Nome', field:'firstName', width:'6em' },
     { name:'Cognome', field:'lastName', width:'6em' },
     { name:'Telefono', field:'phone', width:'6em' },
     { name:'Interno', field:'extension', width:'4em' },
     { name:'Azienda', field:'company', width:'6em' },
     { name:'Skype', field:'uri', width:'12em' }
    ]] } ];
  },


  /*
   *
   */
  convertForm2Json:function(formValue)
  {
    var json = this.inherited(arguments);
    //json.bornDate = dojo.date.locale.format(json.bornDate, {selector:'date',datePattern:"yyyy-MM-dd"});
    json.bornDate = json.bornDate ? dojo.date.stamp.toISOString(json.bornDate) : null;
    this._blankIsNull(json);
    return json;
  },


  /*
   *
   */
  convertJson2Form:function(json)
  {
    var form = this.inherited(arguments);
    json.bornDate = dojo.date.stamp.fromISOString(json.bornDate);
    //json.bornDate = new Date(json.bornDate);
    console.log('OOOOOOOOOOOOO', form);
    return form;
  },


  endOfLib:null
});


// ==============================================================================================
//  Calls
// ==============================================================================================
dojo.provide("openinnovation.contacta.Calls");
dojo.declare("openinnovation.contacta.Calls", openinnovation.organic.gridpad.Controller,
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


  /*
   *
   */
  _dialFormatter:function(value,index,widget)//(model)
  {
    /*var item = widget._props.grid.getItem(index)
    if(item != null)
    {
      var field = widget._props.field;
      var text = eval("item."+field+"Code[0]") + " - " + eval("item."+field+"[0]");
      return text;
    }*/
    if (value && value != '')
    {
      value = '<a onclick="contacta.dial(\''+value+'\');return false;">Call</a>';
    }
    else
    {
      value = '<a onclick="alert(\'todo\')");return false;">Add</a>';
    }
    return value;
  },


  /*
   *
   */
  convertForm2Json:function(formValue)
  {
    var json = this.inherited(arguments);
    return json;
  },


  /*
   *
   */
  convertJson2Form:function(json)
  {
    var form = this.inherited(arguments);
    return form;
  },


  endOfLib:null
});

