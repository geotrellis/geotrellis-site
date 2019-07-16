---
# Front matter comment to ensure Jekyll properly reads file.
---

define([
  'app/hillshade'
], function(model) {
  var $sun = $("#circle-sun");
  var $sun_slider = $("#controller-2-slider-1")
  var layer = "hills";

  var circle_cenx = 62;
  var circle_ceny = 62;
  var circle_radius = 62;

  function getGridXY(x, y) { //unused, might be nice in the future
    return [(x*62)+62, -(y*62-62)];
  }

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

  function ui2cords(ui){ 
    var result = limit(ui.position.left, ui.position.top, circle_cenx, circle_ceny, circle_radius);
    var x = (result.x - circle_cenx)/circle_radius;
    var y = (circle_ceny - result.y)/circle_radius;
    return {
      ui: result, //Used for draggable UI
      x:  x,      //Used for model state
      y:  y       //Used for model state
    }
  }

  $sun.controlpad({
    ondrag: function(event, ui){
      var ret = ui2cords(ui);
      model.setXY(ret.x,ret.y);
      $sun_slider.slider( {value: model.state.z * 100} );
      ui.position = { 'top': ret.ui.y, 'left': ret.ui.x };
    }
  });
  $sun.on("dragstop", function(event, ui) {  
    model.update(layer);
  });

  $sun_slider.slider({
    min: 0, max: 100, value: 39, 
    orientation: "vertical", 
    disabled: false,
    stop: function(e, ui) {
      model.setZ(ui.value/100);
      model.update(layer);
    }    
  });

  return {
    'init': function() {
      model.update(layer);
    }
  }
});
