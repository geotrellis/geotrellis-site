define([
  'app/hillshade'
], function(model) {

  var time = ['07:00 AM', '08:00 AM', '09:00 AM', '10:00 AM', '11:00 AM', '12:00 PM','01:00 PM','02:00 PM', '03:00 PM', '04:00 PM', '05:00 PM', '06:00 PM', '07:00 PM','08:00 PM']
  var altitude = [1,13,23,31,37,41,43,43,41,37,31,23,13,1]
  var azimuth = [76.2, 85.2, 94.6, 105.7, 120.5, 143, 177, 212.4, 236.7, 252.4, 263.9, 273.5, 282.5, 291.6]
  var breaks = "0,10,20,30,40,50,60,70,80,90,100,110,120,127";
  var layer = "hills";
  var N = time.length -1;
  var defaultState = N-2;
  var maxAltitude = _.max(altitude);

  var updateHS = function(event, ui) {
    var idx = N - ui.value;
    $("#sun-time").val(time[idx]);
    model.update(layer, breaks, azimuth[idx], altitude[idx], 10.0);
  };

  var drawSun = function(i) {
    var percentLeft = altitude[i] / maxAltitude * 100;        
    $('.sun-slider-content').children('.ui-slider-handle').css('background-position', '0 ' + (100 - percentLeft) +'%'); //set css sprite      
    console.log(altitude[i],percentLeft);
  }

  // Hillshade controllers
  $("#sun-time").val(time[N-defaultState]);

  $('.sun-slider-content').slider({
    'animate': false,
    'min': 0, 'max': N,
    'value' : defaultState,
    'orientation' : 'horizontal',
    'stop': updateHS,
    'slide': function(e, ui){
      drawSun(N - ui.value)
    } 
  });


  return {
    'init': function() { 
      model.update(layer, breaks, azimuth[N-defaultState], altitude[N-defaultState], 10.0);
      drawSun(defaultState);
    }
  }
});