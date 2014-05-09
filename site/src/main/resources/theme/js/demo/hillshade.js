define(['app/maps'], function(maps){
  var HSLayer;
  var map = maps[1];

  var update = function(layer, numBreaks, azimuth, altitude, zFactor){
    console.log("HS update", layer, numBreaks)

    $.ajax({
      url: 'gt/hillshade/breaks',
      data: {
        'layers' : layer,
        'numBreaks': numBreaks,
        'azimuth': azimuth, 
        'altitude': altitude, 
        'zFactor': zFactor
      },
      dataType: "json",
      success: function(r) {
        var breaks = r.classBreaks;
        console.log("HS breaks:", layer, breaks);

        if (HSLayer !== undefined) {
          map.removeLayer(HSLayer);
        }

        HSLayer = new L.TileLayer.WMS("gt/hillshade/wms", {
          breaks: breaks,
          layers: layer,
          'azimuth': azimuth, 
          'altitude': altitude, 
          'zFactor': zFactor,
          format: 'image/png',
          attribution: 'Azavea'
        });

        HSLayer.setOpacity(0.6);
        HSLayer.addTo(map);
      }
    });
  };

  return {
    'update': update
  }
});