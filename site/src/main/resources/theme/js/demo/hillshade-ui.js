define([
  'app/hillshade'
], function(model) {

  var time = ['07:00 AM', '08:00 AM', '09:00 AM', '10:00 AM', '11:00 AM', '12:00 PM','01:00 PM','02:00 PM', '03:00 PM', '04:00 PM', '05:00 PM', '06:00 PM', '07:00 PM','08:00 PM']
  var altitude = [9.5, 21, 32.6, 44.1, 54.9, 63.6, 67.7, 64.7, 56.5, 45.9, 34.5, 22.9, 11.4, 0.6 ]
  var azimuth = [76.2, 85.2, 94.6, 105.7, 120.5, 143, 177, 212.4, 236.7, 252.4, 263.9, 273.5, 282.5, 291.6]

  var layer = "hills_cropped"
  var breaks = 14
  defaultState = 4;

  var updateHS = function(event, ui) {
    var idx = ui.value
    $("#controller-2-amount-1").val(time[idx]);
    model.update(layer, breaks, azimuth[idx], altitude[idx], 1.0);
  };

  // Hillshade controllers
  $("#controller-2-slider-1").slider({
    min: 0, max: time.length - 1, value: defaultState,
    stop: updateHS
  });
  $("#controller-2-amount-1").val(time[defaultState]);

  return {
    'init': function() { model.update(layer, breaks, azimuth[defaultState], altitude[defaultState], 1.0);}
  }
});