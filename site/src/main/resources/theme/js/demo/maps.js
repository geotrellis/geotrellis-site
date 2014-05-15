define([], function(){
  var maps = [
    L.mapbox.map('map-1', 'azavea.i8f80o61'),
    L.mapbox.map('map-2', 'azavea.i8f80o61'),
    L.mapbox.map('map-3', 'azavea.i8f80o61')
  ];

  maps[0].setView([39.99, -75.21], 12);
  maps[1].setView([46.30, -121.50], 11);

  _.forEach(maps, function(map) {
    map.scrollWheelZoom.disable();
  });

  return maps;
 });