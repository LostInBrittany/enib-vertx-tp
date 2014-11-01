'use strict';

/* Services */

angular.module('BeerServices', ['ngResource'])
  .factory('Beer', ['$resource',
    function($resource){
      return $resource('api/beers/:beerId', {}, {
        query: {method:'GET', params:{beerId:'beers'}, isArray:true}
      });
    }]);
