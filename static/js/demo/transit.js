define([
  'app/maps'
], function(maps) {

  var MAX_DURATION = 45 * 60;
  var INITIAL_TIME = 32400;
  var duration = MAX_DURATION;
  var time = INITIAL_TIME;
  var direction = "departing";
  var schedule = "weekday";
  var modes = "train,bus";
  var mapLayer
  var $walking = $("#option1");
  var $transit = $("#option2");

  var map = maps[2];
  var marker = L.marker([39.9795, -75.159], { draggable: true }).addTo(map);

  map.setView([39.9887950160466,-75.2019775390625], 12);

  var breaks =
     _.reduce(_.map([1,10,15,20,30,40,50,60,75,90,120], function(minute) { return minute*60; }),
              function(s,i) { return s + "," + i.toString(); })

  var colors = "0x000000,0xF68481,0xFDB383,0xFEE085," +
               "0xDCF288,0xB6F2AE,0x98FEE6,0x83D9FD,0x81A8FC,0x8083F7,0x7F81BD"

  var update = function() {
    var modes = $('input[name=options]:checked').val();

    if (mapLayer) {
        map.removeLayer(mapLayer);
        mapLayer = null;
    }

    mapLayer = new L.TileLayer.WMS("http://transit.geotrellis.com/api/travelshed/wms", {
                    latitude: marker.getLatLng().lat,
                    longitude: marker.getLatLng().lng,
                    time: time,
                    duration: duration,
                    modes: modes,
                    schedule: schedule,
                    direction: direction,
                    breaks: breaks,
                    palette: colors,
                    attribution: 'Azavea'
    });

    mapLayer.setOpacity(0.7);
    mapLayer.addTo(map);
  };

  marker.on('dragend', update);
  $transit.on('change', update);
  $walking.on('change', update);

  update();

  return {
    'init': update
  }
});
