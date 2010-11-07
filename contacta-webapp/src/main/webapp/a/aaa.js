// -*- js-var:organic,ui,console,dojo,dijit,dojox; -*-
// $Id: aaa.js 671 2010-07-22 22:06:44Z michele.bianchi $
dojo.provide("openinnovation.organic.Person");

dojo.require("openinnovation.organic.Organic");


// ==============================================================================================
//  Person
// ==============================================================================================
dojo.declare("openinnovation.organic.Person", openinnovation.organic.gridpad.Controller,
{
  classCode:'person',
  quickField:'displayName',

  constructor: function(args)
  {
    this.structure = [{ cells: [[
     { name:'Nome completo', field:'displayName', width:'16em' },
     { name:'Nome', field:'firstName', width:'6em' },
     { name:'Cognome', field:'lastName', width:'6em' },
     { name:'Telefono', field:'phone', width:'6em' },
     { name:'Interno', field:'extension', width:'4em' },
     { name:'Azienda', field:'company', width:'6em' }
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


// ==============================================================================================
//  Organization
// ==============================================================================================
dojo.declare("openinnovation.organic.Organization", openinnovation.organic.gridpad.Controller,
{
  classCode:'organization',
  quickField:'displayName',

  constructor: function(args)
  {
    this.structure = [{ cells: [[
     { name:'Nome completo', field:'displayName', width:'16em' },
     { name:'Nome', field:'firstName', width:'6em' },
     { name:'Cognome', field:'lastName', width:'6em' },
     { name:'Telefono', field:'phone', width:'6em' },
     { name:'Interno', field:'extension', width:'4em' },
     { name:'Azienda', field:'company', width:'6em' }
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


// ==============================================================================================
//  Account
// ==============================================================================================
dojo.declare("openinnovation.organic.Account", openinnovation.organic.gridpad.Controller,
{
  classCode:'account',
  quickField:'displayName',

  constructor: function(args)
  {
    this.structure = [{ cells: [[
     { name:'Nome completo', field:'displayName', width:'16em' },
     { name:'Nome', field:'firstName', width:'6em' },
     { name:'Cognome', field:'lastName', width:'6em' },
     { name:'Telefono', field:'phone', width:'6em' },
     { name:'Interno', field:'extension', width:'4em' },
     { name:'Azienda', field:'company', width:'6em' }
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


// ==============================================================================================
//  Role
// ==============================================================================================
dojo.declare("openinnovation.organic.Role", openinnovation.organic.gridpad.Controller,
{
  classCode:'role',
  quickField:'displayName',

  constructor: function(args)
  {
    this.structure = [{ cells: [[
     { name:'Nome completo', field:'displayName', width:'16em' },
     { name:'Nome', field:'firstName', width:'6em' },
     { name:'Cognome', field:'lastName', width:'6em' },
     { name:'Telefono', field:'phone', width:'6em' },
     { name:'Interno', field:'extension', width:'4em' },
     { name:'Azienda', field:'company', width:'6em' }
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


// ==============================================================================================
//  Group
// ==============================================================================================
dojo.declare("openinnovation.organic.Group", openinnovation.organic.gridpad.Controller,
{
  classCode:'group',
  quickField:'displayName',

  constructor: function(args)
  {
    this.structure = [{ cells: [[
     { name:'Nome completo', field:'displayName', width:'16em' },
     { name:'Nome', field:'firstName', width:'6em' },
     { name:'Cognome', field:'lastName', width:'6em' },
     { name:'Telefono', field:'phone', width:'6em' },
     { name:'Interno', field:'extension', width:'4em' },
     { name:'Azienda', field:'company', width:'6em' }
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


// ==============================================================================================
//  Policy
// ==============================================================================================
dojo.declare("openinnovation.organic.Policy", openinnovation.organic.gridpad.Controller,
{
  classCode:'policy',
  quickField:'displayName',

  constructor: function(args)
  {
    this.structure = [{ cells: [[
     { name:'Nome completo', field:'displayName', width:'16em' },
     { name:'Nome', field:'firstName', width:'6em' },
     { name:'Cognome', field:'lastName', width:'6em' },
     { name:'Telefono', field:'phone', width:'6em' },
     { name:'Interno', field:'extension', width:'4em' },
     { name:'Azienda', field:'company', width:'6em' }
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


// ==============================================================================================
//  AAA
// ==============================================================================================
dojo.declare("openinnovation.organic.Aaa", null,
{
  constructor: function(args)
  {
    //this.organicService = new dojo.rpc.JsonService("${base}/smd/organic.action");

    this.person = new openinnovation.organic.Person();
    this.organization = new openinnovation.organic.Organization();
    this.account = new openinnovation.organic.Account();
    this.role = new openinnovation.organic.Role();
    this.group = new openinnovation.organic.Group();
    this.policy = new openinnovation.organic.Policy();

    //this.person.refresh();
    //this.organization.refresh();
    //this.account.refresh();
    //this.role.refresh();
    //this.group.refresh();
    //this.policy.refresh();
  },

});
