define(['app/maps'], function(maps){
  var WOLayer;
  var map = maps[0];

  var update = function(layers, weights, numBreaks){
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

        if (WOLayer !== undefined) {
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