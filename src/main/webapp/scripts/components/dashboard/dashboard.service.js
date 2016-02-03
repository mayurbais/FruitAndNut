'use strict';

angular.module('try1App')
    .factory('Dashboard', function ($resource, DateUtils, $http) {
    	 var factory = {}; 	
    	 factory.getMyChildren = function(successfn, errorfn){
    		$http.get('api/dashboard/getMyChildren').success(function(data) {
    			successfn(data);
			}).error(function(data) {
				errorfn(data);
			});
    	}
    	 return factory;
    });
