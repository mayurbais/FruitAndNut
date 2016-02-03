'use strict';

angular.module('try1App')
    .factory('GradingLevel', function ($resource, DateUtils) {
        return $resource('api/gradingLevels/:id', {}, {
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
