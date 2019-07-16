// Demo code
requirejs.config({
    baseUrl: 'assets/javascript',
    paths: {
        app: 'demos'
    }
});

requirejs(
[
    'app/weighted-overlay-ui',
    'app/hillshade-ui',
    'app/maps'
],

function(wo_ui, hs_ui, maps){
    $('#carousel-controller').carousel({ interval: false });
    $('#carousel-map').carousel({ interval: 0 });

    $('#carousel-controller').on('slide.bs.carousel',
        function (event) {
            if (event.namespace==="bs.carousel") {
                var next = $(event.relatedTarget);
                var to = next.index();
                
                //do initial load only when we switch to that view.
                var cache = [];
                if (cache[to] == undefined){
                    cache[to] = true;
                    switch (to) {
                        case 1: hs_ui.init();
                            break;
                    }
                }
                
                $('#carousel-map').carousel(to);
                maps[to].invalidateSize();
            }
        }
    );
});