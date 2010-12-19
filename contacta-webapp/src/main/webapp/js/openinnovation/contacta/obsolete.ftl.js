// -*- js-var:ui,console,dojo,dijit,dojox; -*-
// $Revision: 627 $
// :encoding=UTF-8:

/*
 *
 */
function notImplemented()
{
  organic.util.dojoAlertShow('${m.t("short.unimplemented")?js_string}');
}


/*
 * The Fastest Trim in the World of Javascript
 */
function trim12 (str)
{
  var str = str.replace(/^\s\s*/, '');  // URGENT come funzia questa cosa degli slash in js???
  var ws = /\s/;
  var i = str.length;
  while (ws.test(str.charAt(--i)));
  return str.slice(0, i + 1);
}


/*
 *
 */
function validateEmailAddress()
{
  return true; //dojox.regexp.emailAddress();
}


/*
 *
 */
function validateEmailAddressList()
{
  return true;//return dojox.regexp.validateEmailAddressList(dojox.regexp.tld());
}


/*
 *
 */
function validateNameEmailAddress(/*dijit.form.ValidationTextBox.__Constraints*/constraints)
{
  var vtbId = constraints["vtb"];
  var vtb = dijit.byId(vtbId);

  if(!vtb)
  {
    return "\.*";
  }
  else
  {
    var vtbValues = vtb.attr('value');
    var words=vtbValues.split(","); //split using comma as delimiter

    var emailsToValidate = '';

    for (var i=0; i<words.length; i++)
    {
      var b1Index = words[i].indexOf('<');
      var b2Index = words[i].indexOf('>');

      var sbValue = (b1Index == -1 || b2Index == -1) ? trim12(words[i]) : trim12(words[i].substring(b1Index+1, b2Index));
      if(emailsToValidate != "")
      {
        emailsToValidate += ",";
      }

      emailsToValidate += sbValue;
    }

    if( dojox.validate.isEmailAddress(emailsToValidate, dojox.regexp.tld) )
    {
      return vtb.attr('value');
    }
    else
    {
      return vtb.attr('value')+'thisIsImpossible';
    }
  }
}


/*
 *
 */
function validateNameEmailAddressList(/*dijit.form.ValidationTextBox.__Constraints*/constraints)
{
  var vtbId = constraints["vtb"];
  var vtb = dijit.byId(vtbId);

  if(!vtb)
  {
    return "\.*";
  }
  else
  {
    var vtbValues = vtb.attr('value');
    var words=vtbValues.split(","); //split using comma as delimiter

    var emailsToValidate = '';

    for (var i=0; i<words.length; i++)
    {
      var b1Index = words[i].indexOf('<');
      var b2Index = words[i].indexOf('>');

      var sbValue = (b1Index == -1 || b2Index == -1) ? trim12(words[i]) : trim12(words[i].substring(b1Index+1, b2Index));
      if(emailsToValidate != "")
      {
        emailsToValidate += ",";
      }

      emailsToValidate += sbValue;
    }

    if( dojox.validate.isEmailAddressList(emailsToValidate, dojox.regexp.tld) )
    {
      return vtb.attr('value');
    }
    else
    {
      return vtb.attr('value')+'thisIsImpossible';
    }
  }

}


/*
 *
 */
function clone(origObj)
{
  var newObj = (origObj instanceof Array) ? [] : {};

  for (var i in origObj)
  {
    if (origObj[i] && typeof origObj[i] == "object")
    {
      newObj[i] = clone(origObj[i]);
    }
    else
    {
      newObj[i] = origObj[i];
    }
  }

  return newObj;
}

