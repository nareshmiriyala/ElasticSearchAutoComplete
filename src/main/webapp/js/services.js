var searchService = function ($http) {
    return {
        getSearchResults: function (query) {
            return $http.post('/search', query)
        }
    };
};

var dbInsert = function ($http, $log) {
    return {
        insertDbRecords: function () {
            $log.info("Inserting records started");
            return $http.post('/insertRecords')
        }
    };
};
var elasticIndex = function ($http) {
    return {
        indexSearchResults: function () {
            return $http.post('/indexRecords')
        }
    };
};