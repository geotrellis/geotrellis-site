define(['app/ui'], function(ui){
  var WOLayer = null;
  var map = ui.maps[0];

  var update = function(weight1, weight2){
    var layers = ['pop-density', 'st-density'];
    var weights = [1, weight1];
    var numBreaks = 10;

    $.ajax({
      url: 'gt/breaks',
      data: {
        'layers' : layers.join(),
        'weights' : weights.join(),
        'numBreaks': numBreaks
      },
      dataType: "json",
      success: function(r) {
        var breaks = r.classBreaks;

        WOLayer = new L.TileLayer.WMS("gt/weighted-overlay", {
          breaks: breaks,
          layers: layers,
          weights: weights,
          colorRamp: 'yellow-to-red-heatmap',
          attribution: 'Azavea'
        });

        if (WOLayer) map.removeLayer(WOLayer);
        WOLayer.setOpacity(0.7);
        WOLayer.addTo(map);
      }
    });
  };

  return {
    'update': update
  }
});