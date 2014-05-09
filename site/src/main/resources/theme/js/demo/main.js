requirejs.config({
  baseUrl: 'js',
  paths: {
    app: 'demo'
  }
});

requirejs(
[
  'app/weighted-overlay-ui',
  'app/hillshade-ui',
  'app/transit'
],
function(wo_ui, hs_ui){

});
