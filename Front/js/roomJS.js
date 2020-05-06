function toggleFAB(fab) {
    if (document.querySelector(fab).classList.contains('show')) {
        document.querySelector(fab).classList.remove('show');
    } else {
        document.querySelector(fab).classList.add('show');
    }
}

document.querySelector('.fab .main').addEventListener('click', function () {
    toggleFAB('.fab');
});

document.querySelectorAll('.fab ul li button').forEach((item) => {
    item.addEventListener('click', function () {
        toggleFAB('.fab');
    });
});

/*Embed features */
/* URL */
function refreshURL(source){
    var iframe = document.getElementById('embed-url');
    iframe.src = source;
    document.getElementById('embed-url').style.display = 'block';
    document.getElementById('object-pdf').style.display = 'none';
    document.getElementById('embed-pdf').style.display = 'none';
}
/* PDF */
function refreshPDF(source){
    var object = document.getElementById('object-pdf');
    var embed = document.getElementById('embed-pdf');
    object.data = source;
    embed.src = source;
    document.getElementById("embed-pdf").style.display = 'block';
    document.getElementById('object-pdf').style.display = 'block';
    document.getElementById("embed-url").style.display = 'none';
}

/*MAPA*/
let map;
function GetMap(lat,long) {
    map = new Microsoft.Maps.Map('#myMap', {
        center: new Microsoft.Maps.Location(lat,long), //Location center position
        mapTypeId: Microsoft.Maps.MapTypeId.load, //aerial,canvasDark,canvasLight,birdseye,grayscale,streetside
        zoom: 15  //Zoom:1=zoomOut ~ 20=zoomUp
    });
}

function refreshMap(country,zipcode){
    var requestURL = "http://dev.virtualearth.net/REST/v1/Locations?countryRegion="+country+"&postalCode="+zipcode+"&key=AnzqHklo_lvZR_czqcvXPo-hrYNQ6qElpbIAOWL0U6fgDrJxdvdKazPtBPlFklpu";
    console.log(requestURL);
    var request = new XMLHttpRequest();
    request.open('GET', requestURL);
    request.responseType = 'json';
    request.send();
    request.onload = function latitude(){
        GetMap(request.resourcesSets.resources.point.coordinates);
    }
    
    document.getElementById("reload").style.display = 'none';
}