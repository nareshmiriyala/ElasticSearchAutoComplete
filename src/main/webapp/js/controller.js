/**
 * Created by nareshm on 6/11/2015.
 */

var blogController = function (/* $scope, $location, $http */) {
    console.log("Blog Controller reporting for duty.");
};


var pageController = function (/* $scope, $location, $http */) {
    console.log("Page Controller reporting for duty.");

    // Activates the Carousel
    $('.carousel').carousel({
        interval: 5000
    });

    // Activates Tooltips for Social Links
    $('.tooltip-social').tooltip({
        selector: "a[data-toggle=tooltip]"
    })
};

var searchFunction = function ($scope, searchFactory, dbInsert, elasticIndex) {
    $scope.data = [];

    $scope.search = function (name) {
        console.log("Search :" + name)
      return  searchFactory.getSearchResults(name)
            .then(function (response) {
              var json=[];
              var resp=response.data;
              for(var i=0; i < resp.length; i++){
                  json.push(resp[i].name+","+resp[i].count+","+resp[i].year+","+resp[i].gender);
              }
              console.log("Response from server:"+json)
              return json;
            });
    };


};