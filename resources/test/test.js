var page = require('webpage').create();

////////////////////////////////////////////////////////////////////////////////
// phantom.args[0] is deprecated as of phantomJS 2.0
// http://phantomjs.org/api/phantom/property/args.html
var system = require('system');
// var argsdeprecated = system.args;
// argsdeprecated.shift();// travis says: RangeError: Range error
// phantom.args = argsdeprecated;
////////////////////////////////////////////////////////////////////////////////

//var url = phantom.args[0];
var url = system.args[1];

page.onConsoleMessage = function (message) {
    console.log(message);
};

page.open(url, function (status) {
    page.evaluate(function(){
        paredit_cm.test.run();
    });
    phantom.exit(0);
});
