define([
  'app/hillshade'
], function(model) {

  var altitude = [1,13,23,31,37,41,43,43,41,37,31,23,13,1]
  var azimuth = [90, 77, 64, 51, 38, 25, 12, 360, 347, 334, 321, 308, 295, 282]
  var breaks = "0,10,20,30,40,50,60,70,80,90,100,110,120,127";
  var layer = "hills";
  var N = altitude.length -1;
  var defaultState = N-7;
  var maxAltitude = _.max(altitude);

  var updateHS = function(event, ui) {
    var idx = N - ui.value;
    model.update(layer, breaks, azimuth[idx], altitude[idx], 10.0);
  };

  var drawSun = function(i) {
    var percentLeft = altitude[i] / maxAltitude * 100;
    $('.sun-slider-content').children('.ui-slider-handle').css('background-position', '0 ' + (100 - percentLeft) +'%'); //set css sprite
    console.log(altitude[i],percentLeft);
  }

  // Hillshade controllers
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

  $("#controller-2-slider-1").slider({
    min: -3, max: 3, value: 2, orientation: "vertical"
  });

  return {
    'init': function() {
      model.update(layer, breaks, azimuth[N-defaultState], altitude[N-defaultState], 10.0);
      drawSun(defaultState);
    }
  }
});