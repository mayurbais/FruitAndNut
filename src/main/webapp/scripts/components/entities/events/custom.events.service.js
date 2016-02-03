'use strict';

angular.module('try1App')
    .factory('CustomEvents', function ($resource, DateUtils, $http) {
    	 var factory = {}; 	
    	
    	 factory.getSectionEvents = function(obj,successfn, errorfn){
    		 
    		 $http.post('api/eventss/getChildEvents', $.param(obj), {
                 headers: {
                     "content-type": "application/x-www-form-urlencoded"
                 }
             }).success(function(data) {
    			successfn(data);
			}).error(function(data) {
				errorfn(data);
			});
    	 }
    	 return factory;
    });
