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
     { name:'Nome completo', field:'displayName', width:'14em' },
     { name:'Telefono', field:'phone', width:'6em' },
     { name:'Interno', field:'extension', width:'4em' },
     { name:'Skype', field:'uri', width:'12em' }
    ]] } ];
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

