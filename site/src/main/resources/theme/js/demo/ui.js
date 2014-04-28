define([], function(){
  var maps = [
    L.mapbox.map('map-1', 'lknarf.map-rugx6hs5'),
    L.mapbox.map('map-2', 'lknarf.map-rugx6hs5'),
    L.mapbox.map('map-3', 'lknarf.map-rugx6hs5')
  ];


  _.forEach(maps, function(map) {
    map.scrollWheelZoom.disable();
    map.setView([39.99, -75.21], 12);
  });


  $('#carousel-controller').carousel({ interval: false });
  $('#carousel-map').carousel({ interval: false });

  $('#carousel-controller').on('slid.bs.carousel',
    function (event) {
      if (event.namespace==="bs.carousel") {
        var next = $(event.relatedTarget);
        var to = next.index();
        $('#carousel-map').carousel(to);
        maps[to].invalidateSize();
      }
    }
  );

  return {
   'maps': maps
   }
 });