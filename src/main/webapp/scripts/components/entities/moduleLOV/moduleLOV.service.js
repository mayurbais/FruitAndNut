'use strict';

angular.module('try1App')
    .factory('ModuleLOV', function ($resource, DateUtils) {
        return $resource('api/moduleLOVs/:id', {}, {
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
