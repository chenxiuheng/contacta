// -*- js-var:organic,contacta,ui,console; -*-
// $Id$
dojo.provide("openinnovation.contacta.Coverage");

dojo.require("openinnovation.organic.Organic");

// ==============================================================================================
//  coverage
// ==============================================================================================
dojo.declare("openinnovation.contacta.Coverage", null,
{
  constructor: function()
  {
    // summary:
    //   bla bla bla...
    //
    //  args: object
    //         bla bla bla...
    //
    console.log('contacta.Coverage: constructor');
    this.structure = [{ cells: [[
     { name:'${m.t("label.cid.from")?js_string}', field:'fromCid', width:'18em' },
     { name:'${m.t("label.cid.to")?js_string}', field:'toCid', width:'18em' },
     { name:'${m.t("label.type")?js_string}', field:'type', width:'auto' },
     { name:'${m.t("label.timeout")?js_string}', field:'ringTimeout', width:'4em' },
     { name:'${m.t("label.rank")?js_string}', field:'rank', width:'4em' }
    ]] } ];

    ui.coverageGrid.set('structure', this.structure);
  },

  code:'coverage',
  currentItem:null,

  grid:null,
  store:null,
  structure:null,



  /*
   *
   */
  load:function()
  {
    this.store = new dojo.data.ItemFileWriteStore({ url:'${base}/smd/'+this.code+'-store.action', urlPreventCache:true });
    this.store.fetch(
    {
      // query: {name:"Egypt"}, queryOptions: {ignoreCase: true, deep: true},
      //onBegin:function() { console.log('fetch.onBegin'); },
      //onItem:function(item) { console.log('fetch.onItem', item); },
      //onError:function(errData, request) { console.log('fetch.onError', errData, request); }
      onError:organic.util.errback,
      onComplete:dojo.hitch(this,function(items, request)
      {
        //console.log('contacta.'+this.code+': complete', items, request);
        this.grid.selection.clear();
        this.grid.setStore(this.store);

        //project.visited = true;
      })
    });
  },


  /*
   *
   */
  showDetail:function(event)
  {
    this.currentItem = event.grid.getItem(event.rowIndex);
    ui.coverageDetail.setHref('${base}/s/coverage-detail.action?code='+this.currentItem.id[0]);

    event.grid.selection.clickSelectEvent(event);
  }
});

