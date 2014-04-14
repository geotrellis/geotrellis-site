define([], function(){
   var maps = [];

   maps[0] = L.mapbox.map('map-1', 'lknarf.map-rugx6hs5')
     .setView([40.185262587404466,-75.34060850650938], 11);
   //maps[0].scrollWheelZoom.disable();

   maps[1] = L.mapbox.map('map-2', 'lknarf.map-rugx6hs5')
     .setView([39.948365, -79.221654], 13);
   maps[1].scrollWheelZoom.disable();

   maps[2] = L.mapbox.map('map-3', 'lknarf.map-rugx6hs5')
     .setView([43.948365, -79.221654], 6);
   maps[2].scrollWheelZoom.disable();



   $('#carousel-map').carousel({
     interval: false
   });


   $('#carousel-controller').carousel({
     interval: 0
   }).on('slide.bs.carousel', function (event) {
     if (event.namespace==="bs.carousel") {
       var next = $(event.relatedTarget);
       var to = next.index();
       $('#carousel-map').carousel(to);
       maps[to].invalidateSize();
       alert("NEXT!")
     }
   });

 return {
   'maps': maps
 }
 });