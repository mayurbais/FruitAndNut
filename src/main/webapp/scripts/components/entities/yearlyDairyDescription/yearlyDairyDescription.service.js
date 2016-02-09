'use strict';

angular.module('try1App')
    .factory('YearlyDairyDescription', function ($resource, DateUtils) {
        return $resource('api/yearlyDairyDescriptions/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.year = DateUtils.convertDateTimeFromServer(data.year);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
