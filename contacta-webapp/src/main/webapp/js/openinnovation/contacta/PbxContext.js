// -*- js-var:contacta,ui,console,dojo,dijit,dojox; -*-
// $Id: pbx.js 668 2010-07-20 22:33:12Z michele.bianchi $
dojo.provide("openinnovation.contacta.PbxContext");

dojo.require("openinnovation.organic.Organic");

// ==============================================================================================
//  PbxContext
// ==============================================================================================
dojo.declare("openinnovation.contacta.PbxContext", openinnovation.organic.gridpad.Controller,
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


  /*
   *
   */
  showDetailGetUrl:function(json)
  {
    //var code = this._store.getValue(item, 'login');
    return this.classCode+'-detail.action?code='+json.login;
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
//  PbxProfile
// ==============================================================================================
dojo.declare("openinnovation.contacta.PbxProfile", openinnovation.organic.gridpad.Controller,
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
//  Product
// ==============================================================================================
dojo.declare("openinnovation.contacta.Product", openinnovation.organic.gridpad.Controller,
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
//  Cdr
// ==============================================================================================
dojo.declare("openinnovation.contacta.Cdr", openinnovation.organic.gridpad.Controller,
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


  /*
   *
   */
  showDetailGetUrl:function(json)
  {
    //var uniqueid = this._store.getValue(item, 'uniqueid');
    return this.classCode+'-detail.action?uniqueid='+json.uniqueid;
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

