---
# Front matter comment to ensure Jekyll properly reads file.
---

define(['app/maps'], function(maps){
  var HSLayer;
  var map = maps[1];
  var zFactor = 1.0;
  var state = {
    x: -0.48, y: 0.56, z: 0.4, //suns location if it was projected onto unit-sphere
    azimuth: 314, altitude: 31  //these words have meanings
  }

  function setXY(x, y) {
    state.x = x; state.y = y;
    var b = Math.sqrt(x*x + y*y);
    state.z = Math.sqrt(1-b*b);
    
    state.altitude = Math.atan(state.z/b) *180/Math.PI;
    
    var a = Math.atan2(x,y) *180/Math.PI;
    if (a<0) { a = (180+a) + 180; }      
    state.azimuth = a;    
  }

  function setZ(z){
    state.z = z;
    var b = Math.sqrt(state.x*state.x + state.y*state.y);
    state.z = Math.sqrt(1-b*b);
    state.altitude = Math.atan(z/b) *180/Math.PI;
  }

  var update = function(layer){
    if (HSLayer !== undefined) {
      map.removeLayer(HSLayer);
    }

    HSLayer = new L.TileLayer.WMS("https://tiles.geotrellis.io/gt/hillshade/wms", {
      'layers': layer,
      'azimuth': state.azimuth,
      'altitude': state.altitude,
      'breaks': '0',
      'zFactor': zFactor,
      format: 'image/png',
      attribution: 'Azavea'
    });

    HSLayer.setOpacity(0.9);
    HSLayer.addTo(map);
  };

  return {
    update: update,
    state: state,
    setXY: setXY,
    setZ: setZ
  }
});