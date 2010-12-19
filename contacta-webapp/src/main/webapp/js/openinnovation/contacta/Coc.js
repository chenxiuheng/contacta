
// ==============================================================================================
//  Cdr
// ==============================================================================================
dojo.declare("openinnovation.contacta.Coc", openinnovation.organic.gridpad.Controller,
{
  classCode:"coc",

  constructor: function(args)
  {
    this.structure = [{ cells: [[
     { field:'login', width:'8em' },
     { field:'pin', width:'8em' }
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

