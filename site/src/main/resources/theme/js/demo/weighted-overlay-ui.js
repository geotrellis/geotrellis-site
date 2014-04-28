define([
  'app/ui',
  'app/weighted-overlay'
], function(ui, wo) {
  var layers = ['short_philly_bars', 'short_philly_grocery_stores', 'short_philly_rail_stops'];
  var weights = [2,1,-2];
  var numBreaks = 20;

  var updateWO = function(index, event, ui) {
    weights[index] = ui.value;
    $("#controller-1-amount-1").val(weights[0]);
    $("#controller-1-amount-2").val(weights[1]);
    $("#controller-1-amount-3").val(weights[2]);
    wo.update(layers, weights, numBreaks);
  };

  $("#controller-1-slider-1").slider({
    min: -3, max: 3, value: weights[0],
    slide: _.partial(updateWO, 0)
  });

  $("#controller-1-slider-2").slider({
    min: -3, max: 3, value: weights[1],
    slide: _.partial(updateWO, 1)
  });

  $("#controller-1-slider-3").slider({
    min: -3, max: 3, value: weights[2],
    slide: _.partial(updateWO, 2)
  });

  //initial load
  $("#controller-1-amount-1").val(weights[0]);
  $("#controller-1-amount-2").val(weights[1]);
  $("#controller-1-amount-3").val(weights[2]);
  wo.update(layers, weights, numBreaks);
});