define(['app/maps'], function(maps){
  var WOLayer;
  var map = maps[0];

  var update = function(layers, weights, numBreaks){
    console.log("WO update:", layers, weights);
    $.ajax({
      url: 'gt/weighted-overlay/breaks',
      data: {
        'layers' : layers.join(),
        'weights' : weights.join(),
        'numBreaks': numBreaks
      },
      dataType: "json",
      success: function(r) {
        var breaks = r.classBreaks;
        console.log("WO breaks:", layers, weights, breaks);

        if (WOLayer !== undefined) {
          console.log("Removing Layer: ", WOLayer);
          map.removeLayer(WOLayer);
        }

        WOLayer = new L.TileLayer.WMS("gt/weighted-overlay/wms", {
          breaks: breaks,
          layers: layers,
          format: 'image/png',
          weights: weights,
          transparent: true,
          attribution: 'Azavea'
        });

        WOLayer.setOpacity(0.6);
        WOLayer.addTo(map);
      }
    });
  };

  return {
    'update': update
  }
});