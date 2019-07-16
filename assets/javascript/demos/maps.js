---
# Front matter comment to ensure Jekyll properly reads file.
---

define([], function(){
  var maps = [
    L.mapbox.map('map-1', 'azavea.i8f80o61'),
    L.mapbox.map('map-2', 'azavea.i8f80o61'),
  ];

  maps[0].setView([39.99, -75.21], 12);

  maps[1].setView([46.90, -121.98], 11);

  function fn_resize(map) { 
    map.invalidateSize();
  }

  _.forEach(maps, function(map) {
    map.scrollWheelZoom.disable();
    $(window).resize(_.partial(fn_resize, map))
  });

  return maps;
 });