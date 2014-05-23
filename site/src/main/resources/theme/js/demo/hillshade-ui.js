define([
  'app/hillshade'
], function(model) {
  var $sun = $("#circle-sun")
  var $sun_slider = $("#controller-2-slider-1")
  var altitude = 31;
  var azimuth = 314;
  var layer = "hills";

  //-- Vertical Sun slider
  var drawSun = function(i) {
    var percentLeft = (90 - altitude)/90*100;
    $('.sun-slider-content').children('.ui-slider-handle').css('background-position', '0 ' + (100 - percentLeft) +'%'); //set css sprite
    console.log(altitude[i],percentLeft);
  }

  // Hillshade controllers

  $sun_slider.slider({
    min: 0, max: 90, value: 39, orientation: "vertical", disabled: true    
  });

  //-- Sun Globe
  jQuery.fn.extend({
      controlpad: function (options) {
          $sun.draggable({
              drag: options.ondrag,
          });
      }
  });
  function limit(x, y, cenx, ceny, r) {
      var dist = distance([x, y], [cenx, ceny]);
      if (dist <= r) {
          return { x: x, y: y };
      }
      else {
          x = x - cenx;
          y = y - ceny;
          var radians = Math.atan2(y, x);
          return {
              x: Math.cos(radians) * r + cenx,
              y: Math.sin(radians) * r + ceny
          };
      }
  }
  function distance(dot1, dot2) {
      var x1 = dot1[0],
          y1 = dot1[1],
          x2 = dot2[0],
          y2 = dot2[1];
      return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
  }
  function update_angle(x, y){
    var theta = Math.atan2(x,y)*180/Math.PI;
    if (theta<0) { var theta = (180+theta) + 180; }
    var b = Math.sqrt(x*x + y*y);
    var z = Math.sqrt(1-b*b);
    var alpha = Math.atan(z/b)*180/Math.PI;
    $sun_slider.slider( "value", alpha);
    altitude = alpha;
    azimuth = theta;
  }
  $(document).ready(function(){
      var circle_cenx = 62;
      var circle_ceny = 62;
      var circle_radius = 62;
      $sun.controlpad({
          ondrag: function(event, ui){
              var result = limit(ui.position.left, ui.position.top, circle_cenx, circle_ceny, circle_radius);
              update_angle((result.x - circle_cenx)/circle_radius,(circle_ceny - result.y)/circle_radius)              
              ui.position = { 'top': result.y, 'left': result.x };
          }
      });
      $sun.on("dragstop", 
        function(event, ui) {  
            console.log("DRAG END")  
            model.update(layer, azimuth, altitude, 10.0);
        }
      );
  });





  return {
    'init': function() {
      model.update(layer, azimuth, altitude, 10.0);
    }
  }
});