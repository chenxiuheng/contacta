define(
[
  'dojo',
  'dojo/_base/declare',
  'openinnovation/contacta/Calls',
  'openinnovation/contacta/Cdr',
  'openinnovation/contacta/Coc',
  'openinnovation/contacta/Contacta',
  'openinnovation/contacta/Coverage',
  'openinnovation/contacta/PbxContext',
  'openinnovation/contacta/PbxProfile',
  'openinnovation/contacta/Phonebook',
  'openinnovation/contacta/Phone',
  'openinnovation/contacta/Product',
  'openinnovation/contacta/Sip'
],
function(dojo, declare, Calls, Cdr, Coc, Contacta, Coverage, PbxContext, PbxProfile, Phonebook, Phone, Product, Sip)
{
  var Singleton = declare("openinnovation.contacta.Contacta", null,
  {
    contactaService:null,
    develMode:false,
    //addressbookService:null,
    sip:null,
    coverage:null,
    phone:null,
    session:
    {
      admin:false,
      user:false,
      guest:true
    },
    _i18n:null,


    constructor: function(config)
    {
      // summary:
      //   bla bla bla...
      //
      //  args: object
      //         bla bla bla...
      //
      if (config)
      {
        if (config.session !== undefined)
        {
          this.session = config.session;
        }
        this.develMode = config.develMode ? config.develMode : false;
      }
      //console.log('openinnovation.contacta.Contacta: constructor');
    },


    init:function()
    {
      this._i18n = organic.i18n;

      this.contactaService = new dojo.rpc.JsonService(organic.baseUrl+"/smd/contacta.action");

      if (this.session.admin === true)
      {
        this.pbxcontext = new PbxContext();
        this.pbxprofile = new PbxProfile();
        this.product = new Product();
        this.cdr = new Cdr();
        this.pbxcontext.refresh();
        this.pbxprofile.refresh();
        this.product.refresh();

        this.phone = new Phone();
        this.sip = new Sip();
        this.coc = new Coc();
        this.phone.refresh();
        this.sip.refresh();
        this.coc.refresh();

        this.coverage = new Coverage();
        this.coverage.grid = ui.coverageGrid;
        dojo.connect(ui.coverageGrid, "onRowClick", this.coverage, this.coverage.showDetail);

        if (ui.sip.quick)
        {
          dojo.connect(ui.sip.quick.domNode, 'onkeypress', function(e)
          {
            if (e.keyCode === dojo.keys.ENTER)
            {
              this.sip.quickSearch(ui.sip.quick.getValue());
            }
          });

          dojo.connect(ui.phone.quickFilter.domNode, 'onkeypress', function(e)
          {
            if (e.keyCode === dojo.keys.ENTER)
            {
              this.phone.quickSearch(ui.phone.quickFilter.getValue());
            }
          });
        }

        this.checkAsterisk();
      }

      this.phonebook = new Phonebook();
      this.phonebook.refresh();
      this.calls = new Calls();
      this.calls.refresh();

      //this.addressbookService = new dojo.rpc.JsonService("${base}/smd/addressbook.action");

      if (this.develMode)
      {
        var now = new Date();
        //var d = dojo.date.locale.format(now, {selector:'date',datePattern:'yyyy-MM-dd'});
        //var b = ''+now.getHours()+':00';
        //var e = ''+(now.getHours()+1)+':00';
        //console.log('XXXXXX', d);
        ui.confDayInput.set('value', now);
        //ui.confBeginInput.set('value', b);
        //ui.confEndInput.set('value', e);
      }
      ui.progressBar.update({ progress:100 });
    },


    checkAsterisk:function()
    {
      //console.log('contacta.Contacta: checkAsterisk', this);
      this.contactaService.checkAsterisk().addCallbacks(function(result)
      {
        //console.log('checkAsterisk', result);
        //var td = dojo.byId("ui.asteriskStatus");
        var msg = result;
        if(result === 'Cannot reach asterisk')
        {
          msg = '${m.t("short.asterisk.unavailable")?js_string}';
        }
        else if(result === 'Asterisk is down. Plese restart')
        {
          msg = '${m.t("short.asterisk.notrunning")?js_string}';
        }
        else if(result === 'Asterisk is up and running')
        {
          msg = '${m.t("short.asterisk.ok")?js_string}';
        }
        ui.asteriskStatus.setContent(msg);
      }, organic.util.errback);
    },


    restartAsterisk:function()
    {
      this.contactaService.restartAsterisk().addCallbacks(function(result)
      {
        this.checkAsterisk();
      }, organic.util.errback);
    },


    updateExtensionProfile:function()
    {
      this.contactaService.updateExtensionProfile().addCallbacks(function(result)
      {
      }, organic.util.errback);
    },


    downloadConfiguration:function(/*String*/fileType)
    {
      window.location.replace('/d/download-configuration.action?fileType='+fileType);
    },


    missedCalls:function()
    {
      ui.contactaTabContainer.selectChild(ui.contacta.callsTab);
    },


    dial:function(nr)
    {
      if (!nr)
      {
        nr = ui.digitInput.getValue();
      }
      this.contactaService.dial(nr).addCallbacks(function(result)
      {
        console.log('this.contactaService.dial', result);
      },
      function(result)
      {
        organic.util.errback(result);
      });
    },


    transfer:function()
    {
      var nr = ui.digitInput.getValue();
      this.contactaService.transfer(nr, 'nazionali').addCallbacks(function(result)
      {
        console.log('this.contactaService.transfer', result);
      },
      function(result)
      {
        organic.util.errback(result);
      });
    },


    hold:function()
    {
      this.contactaService.hold().addCallbacks(function(result)
      {
        console.log('this.contactaService.hold', result);
      },
      function(result)
      {
        organic.util.errback(result);
      });
    },


    unhold:function()
    {
      this.contactaService.unhold().addCallbacks(function(result)
      {
        console.log('this.contactaService.unhold', result);
      },
      function(result)
      {
        organic.util.errback(result);
      });
    },


    answer:function()
    {
      this.contactaService.answer().addCallbacks(function(result)
      {
        console.log('this.contactaService.answer', result);
      },
      function(result)
      {
        organic.util.errback(result);
      });
    },


    booking:
    {
      remail:function(/*int*/appointmentId)
      {
        this.contactaService.remail(appointmentId).addCallbacks(
          function(result)
          {
            if (result === 'KO')
            {
              console.log('RESULT', result);
              organic.util.dojoAlertShow('${m.t("short.contactAdmin")?js_string}');
            }
            this.booking.my();
          }, organic.util.errback);
      },


      cancel:function(/*int*/appointmentId)
      {
        this.contactaService.cancel(appointmentId).addCallbacks(
          function(result)
          {
            if (result === 'KO')
            {
              console.log('RESULT', result);
              organic.util.dojoAlertShow('${m.t("short.contactAdmin")?js_string}');
            }
            this.booking.my();
            this.booking.calendar();
          }, organic.util.errback);
      },


      my:function()
      {
        ui.bookingSchedule.set('href', '${base}/s/booking-my.action');
      },


      calendar:function(/*int*/dir)
      {
        ui.calendarPane.set('href', '${base}/s/booking-calendar.action?weekN='+dir);
      },


      submit:function()
      {
        var bk = ui.confBookForm.getValues();
        if(!ui.confBookForm.validate() || bk.begin > bk.end)
        {
          organic.util.dojoAlertShow('${m.t("short.data.invalid")?js_string}');
          return;
        }
        //account.id = parseInt(account.id, 10);
        console.log('booking', bk);

        var begin = new Date(bk.begin);
        begin.setDate(bk.day.getDate());
        begin.setMonth(bk.day.getMonth());
        begin.setFullYear(bk.day.getFullYear());
        var end = new Date(bk.end);
        end.setDate(bk.day.getDate());
        end.setMonth(bk.day.getMonth());
        end.setFullYear(bk.day.getFullYear());

        console.log('XXXXXXXXX', begin, end, bk.day.getDate());
        var attendees = ui.attendeeListInput ? ui.attendeeListInput.get('value') : '';

        this.contactaService.book(begin.getTime(), end.getTime(), attendees, '${m.t("label.conf.detail")?js_string}', 'description').addCallbacks(
          function(result)
          {
            if (result === 'KO')
            {
              console.log('RESULT', result);
              organic.util.dojoAlertShow('${m.t("short.linesBusy")?js_string}');
              //organic.util.dojoAlertShow('${m.t("short.contactAdmin")?js_string}');
            }
            this.booking.my();
            this.booking.calendar();
          }, organic.util.errback);
      },


      reset:function()
      {
        ui.confBookForm.reset();
      },


      addAttendee:function()
      {
        var a = ui.attendeeInput.get('value');
        if(!a || a === '')
        {
          organic.util.dojoAlertShow('${m.t("short.field.empty")?js_string}');
          return;
        }
        ui.attendeeInput.set('value', '');

        var alist = ui.attendeeListInput.get('value');
        if (alist.indexOf(a) === -1)
        {
          if (alist.length !== 0)
          {
            alist += ',';
          }
          alist += a;
          ui.attendeeListInput.set('value', alist);
        }
      }
    },


    eventHandler:function(e)
    {
      // use this.domNode.getAttribute('widgetId') to show "this" is the widget
      // mouseleave/enter map to mouseout/over in all browsers except IE
      console.log(this.domNode.getAttribute('widgetId') + ' ' + arguments[0].type);
    },


    endOfLib:null
  });

  var instance = null;
  return function getSingleton(args) { return (instance = (instance || new Singleton(args))); };
});
