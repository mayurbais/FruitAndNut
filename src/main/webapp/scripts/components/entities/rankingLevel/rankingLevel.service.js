'use strict';

angular.module('try1App')
    .factory('RankingLevel', function ($resource, DateUtils) {
        return $resource('api/rankingLevels/:id', {}, {
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
