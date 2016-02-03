'use strict';

angular.module('try1App')
    .factory('CustomAdmissionDetails', function ($resource, DateUtils) {
        return $resource('api/customAdmissionDetailss/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
