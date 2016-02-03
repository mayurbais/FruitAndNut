'use strict';

angular.module('try1App')
    .factory('TimeSlot', function ($resource, DateUtils) {
        return $resource('api/timeSlots/:id', {}, {
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
