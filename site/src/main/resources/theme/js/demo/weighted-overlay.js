define(['app/ui'], function(ui){
  var WOLayer = null;
  var map = ui.maps[0];

  var update = function(layers, weights, numBreaks){
    console.log("WO update:", layers, weights);
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
        console.log("WO breaks:", layers, weights, breaks);

        WOLayer = new L.TileLayer.WMS("gt/weighted-overlay", {
          breaks: breaks,
          layers: layers,
          weights: weights,
          colorRamp: 'light-to-dark-green',
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