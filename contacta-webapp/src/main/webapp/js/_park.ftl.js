var a =
{
  /*
   *
   */
  __XXX_load:function()
  {
    //contacta.contactaService.findDatastore('phone').addCallbacks(this.loadCallback, organic.util.errback);
    this.store = new dojo.data.ItemFileWriteStore({ url:'${base}/smd/'+this.code+'-store.action', urlPreventCache:true });
    this.store.fetch(
    {
      // query: {name:"Egypt"}, queryOptions: {ignoreCase: true, deep: true},
      //onBegin:function() { console.log('fetch.onBegin'); },
      //onItem:function(item) { console.log('fetch.onItem', item); },
      //onError:organic.util.errback, // alert('${m.t("label.error")?js_string}');
      onError:dojo.hitch(this,function(errData, request)
      {
        //console.log('contacta.'+this.code+': fetch.onError', errData, request);
        ui.organicErrorDialog.show();

        // errData = { fileName, lineNumber, message, name, stack }
        ui.organicErrorForm.setValues(errData);
        //ui.organicErrorStack.attr('content', errData.stack);
      }),
      onComplete:dojo.hitch(this,function(items, request)
      {
        //console.log('contacta.'+this.code+': fetch.complete', items, request);
        this.grid.selection.clear();
        this.grid.setStore(this.store);
      })
    });
  },

    /*
    contacta.contactaService.phoneCreateUpdate(phone).addCallbacks(function(result)
    {
      if(phone.id==0)
      {
        ui.phone.grid.store.newItem(result);
      }
      else
      {
        try
        {
          // FIXME questo fa schifo, ma prima era peggio:(
          var cell = ui.phone.grid.selection.getSelected()[0];
          ui.phone.grid.store.setValues(cell, 'ipAddress', result.ipAddress);
          ui.phone.grid.store.setValues(cell, 'macAddress', result.macAddress);
          ui.phone.grid.store.setValues(cell, 'model', result.model);
          ui.phone.grid.store.setValues(cell, 'vendor', result.vendor);
          ui.phone.grid.store.setValues(cell, 'regseconds', result.regseconds);
          ui.phone.grid.store.setValues(cell, 'hasConfig', result.hasConfig.toString());
          ui.phone.grid.store.setValues(cell, 'location', result.location);
          ui.phone.grid.store.setValues(cell, 'info', result.info);
          //organic.util.updateGridItem(result, cell[0]);
        }
        catch(e)
        {
          console.warn('this.submit', e);
        }
      }

      ui.phone.crudDialog.hide();
    },
    function(result)
    {
      ui.phone.crudDialog.hide();

      var errorMessage = result.errorObject.error.message;

      if(errorMessage == 'Duplication of a unique field: macaddress')
      {
        result.errorObject.error.message = '${m.t("short.phone.duplicate.mac")?js_string}';
        organic.util.errback(result);
      }
      else
      {
        organic.util.errback(result);
      }
    });
    */

  /*
   *
   */
  phone_show:function(/*boolean*/create)
  {
    if(create)
    {
      var title = '${m.t("label.phone.create")?js_string}';

      ui.phone.crudForm.reset();

      var phone = { id:0 };

      ui.phone.crudForm.setValues(phone);
      ui.phone.crudDialog.titleNode.innerHTML = title;
      ui.phone.crudDialog.show();
    }
    else
    {
      if (!this.currentItem)
      {
        organic.util.dojoAlertShow('${m.t("short.phone.select")?js_string}');
        return;
      }

      var title = '${m.t("label.phone.edit")?js_string}';
      phone = organic.util.normalizeStoreItem(this.currentItem);
      phone.config = ''+phone.hasConfig; // TODO bella roba x i checkbox, sistemare
      ui.phone.crudForm.setValues(phone);
      ui.phone.crudDialog.titleNode.innerHTML = title;
      ui.phone.crudDialog.show();
    }
  },


  endOfLib:null
};
