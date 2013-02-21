var map;
PluginDetect.getVersion(".");
var version = PluginDetect.getVersion('Java');
var rootURL = "https://java-trialaj2n.java.us1.oraclecloudapps.com/javacountdown/rest/version";
var gdpData;

// initialized by onLoad
function initialize() {
    console.log("version" + version);
    if (navigator.geolocation)
    {
        console.log("geolocation");
        navigator.geolocation.getCurrentPosition(logPosition, showError);
        $("#geoMessage").text("Thanks for contributing!");
    } else {
        console.log("no geolocation");
    }

// callback for geolocation - logs java version incl lat long  
    function logPosition(position)
    {
        console.log("position.coords.latitude" + position.coords.latitude);
        var coord = position.coords.latitude + "," + position.coords.longitude;
        console.log("coords" + coord);
        log = new log(version, position.coords.latitude, position.coords.longitude);
        addLog(JSON.stringify(log));
    }
    ;

    // Error callback - displays errors.
    function showError(error)
    {
        switch (error.code)
        {
            case error.PERMISSION_DENIED:
                $("#geoMessage").text("User denied the request for Geolocation.");
                break;
            case error.POSITION_UNAVAILABLE:
                $("#geoMessage").text("Location information is unavailable.");
                break;
            case error.TIMEOUT:
                $("#geoMessage").text("The request to get user location timed out.");
                break;
            case error.UNKNOWN_ERROR:
                $("#geoMessage").text("An unknown error occurred.");
                break;
        }
    }
    ;
//http://jvectormap.com/maps/world/world/

    // fill the gdata object with series-values for the map.
    gdpData = getData();

// get data from the rest backend
    function getData() {
        var result;
        $.ajax({
            url: rootURL,
            type: 'get',
            async: false,
            dataType: 'json',
            success: function(dataWeGotViaJsonp) {
                result = dataWeGotViaJsonp;
            },
            error: function(jqXHR, textStatus, errorThrown) {
                console.log(textStatus, errorThrown);
            }
        });
        return result;
    }


// render the map
    $('#map_canvas').vectorMap({
        map: 'world_mill_en',
        backgroundColor: "#FFFFFF",
        regionStyle: {
            initial: {
                fill: '#d80000',
                "fill-opacity": 1,
                stroke: 'none',
                "stroke-width": 0,
                "stroke-opacity": 1
            },
            hover: {
                "fill-opacity": 0.7
            }
        },
        series: {
            regions: [{
                    values: gdpData,
                    scale: ['#C8EEFF', '#0071A4'],
                    normalizeFunction: 'polynomial'
                }]
        },
        onRegionLabelShow: function(e, el, code) {
            el.html(el.html() + ' Java 7 Adoption - (' + gdpData[code] + ' %)');
        }
    });


    // write the log via POST to the rest backend

    function addLog(log) {

        $.ajax({
            type: 'POST',
            contentType: 'application/json',
            url: rootURL,
            dataType: "json",
            data: log,
            success: function(data, textStatus, jqXHR) {
                console.log('Log created successfully');
            },
            error: function(jqXHR, textStatus, errorThrown) {
                console.log('Error adding: ' + textStatus);
            }
        });
    }


// the log object
    function log(version, lat, lng)
    {
        this.version = version;
        this.lat = lat;
        this.lng = lng;
    }


}