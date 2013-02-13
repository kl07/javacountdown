 var map;
      var sanFrancisco = new google.maps.LatLng(37.774546, -122.433523);

      function initialize() {

	  var heatmapData = [
  new google.maps.LatLng(37.782, -122.447),
  new google.maps.LatLng(37.782, -122.445),
  new google.maps.LatLng(37.782, -122.443),
  new google.maps.LatLng(37.782, -122.441),
  new google.maps.LatLng(37.782, -122.439),
  new google.maps.LatLng(37.782, -122.437),
  new google.maps.LatLng(37.782, -122.435),
  new google.maps.LatLng(37.785, -122.447),
  new google.maps.LatLng(37.785, -122.445),
  new google.maps.LatLng(37.785, -122.443),
  new google.maps.LatLng(37.785, -122.441),
  new google.maps.LatLng(37.785, -122.439),
  new google.maps.LatLng(37.785, -122.437),
  new google.maps.LatLng(37.785, -122.435)
];
	  
	  
        var javaMapStyles = [
            {
            featureType: 'landscape',
            elementType: 'all ',
            stylers: [
              { visibility : 'off' }              
              
            ]
          },{
            featureType: 'transit',
            elementType: 'all ',
            stylers: [
              { visibility : 'off' }
            ]
          },{
            featureType: 'poi',
            elementType: 'all ',
            stylers: [
              { visibility : 'off' }
            ]
          },{
            featureType: 'road',
            elementType: 'all ',
            stylers: [
              { visibility : 'off' }
            ]
          },{
            featureType: 'water',
            elementType: 'geometry',
            stylers: [
              { saturation: 40 },
              { lightness: 40 }
            ]
          },{
            featureType: 'administrative.country',
            elementType: 'labels',
            stylers: [
              { hue: '#0022ff' },
              { saturation: 50 },
              { lightness: -10 },
              { gamma: 0.90 }
            ]
          }
        ];

        var mapOptions = {
          zoom: 1,
          center: sanFrancisco,
		  mapTypeControl: false,
          mapTypeControlOptions: {
		  
            mapTypeIds: [google.maps.MapTypeId.SATELLITE , 'java7map']
          },
		   streetViewControl: false,
    zoomControl: true,
    zoomControlOptions: {
      style: google.maps.ZoomControlStyle.SMALL
    }
        };

        map = new google.maps.Map(document.getElementById('map_canvas'),
            mapOptions);

        var styledMapOptions = {
          name: 'Java 7 Adoption'
        };

        var usJava7MapType = new google.maps.StyledMapType(
            javaMapStyles, styledMapOptions);

        map.mapTypes.set('java7map', usJava7MapType);
        map.setMapTypeId('java7map');
		
		var heatmap = new google.maps.visualization.HeatmapLayer({
         data: heatmapData
       });
       heatmap.setMap(map);
		
      }